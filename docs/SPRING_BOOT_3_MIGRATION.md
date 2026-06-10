# Spring Boot 3 Migration: From Current to Target Structure

**Date**: 2026-06-10  
**Purpose**: Show mapping between current (`jan.stefan.hibernate`) and target (`com.storemanagement`) structures

---

## 1) High-Level Transformation

| Aspect | Current | Target | Rationale |
|--------|---------|--------|-----------|
| **Root Package** | `jan.stefan.hibernate` | `com.storemanagement` | Modern naming convention, domain-focused |
| **Framework** | Standalone Hibernate + manual | Spring Boot 3 with auto-config | Reduced boilerplate, managed beans |
| **UI Layer** | Swing/CLI (`menu/*`) | REST API (`controller/*`) | Modern web paradigm, stateless |
| **Database** | MySQL with manual setup | PostgreSQL with Flyway | Better standards compliance, auto-versioning |
| **Dependency Injection** | Manual wiring via factory | Spring DI + annotations | Loose coupling, testability |
| **Transaction Management** | Manual `Session`/`Transaction` | `@Transactional` + Spring TX Manager | Declarative, cleaner code |
| **Configuration** | `persistence.xml` + properties | `application.yml` + `@Configuration` | Single source of truth per profile |
| **Testing** | Limited; manual fixtures | Unit + Integration + Contract + E2E | Comprehensive coverage framework |

---

## 2) Package Structure Mapping

### Current Structure (Old)

```
jan/stefan/hibernate/
├── App.java                         # Entry point
├── connection/                      # Manual DB connection
│   └── (removed in new version)
├── dataInDbValidation/              # Data validation logic
│   └── (moves to core.validation/ + domain validators)
├── dto/                             # Flat DTO organization
│   ├── modelDto/
│   ├── customerDto/
│   └── statModelDto/
├── exceptions/                      # Exception classes
│   └── (consolidates to core.exception/)
├── menu/                            # CLI UI (554-851 lines)
│   ├── MenuPanel.java
│   ├── MenuService.java
│   └── MenuStatistics.java
│   └── (REMOVED - replaced with REST controllers)
├── model/                           # JPA entities
│   ├── Category.java
│   ├── Country.java
│   ├── Customer.java
│   ├── MyOrder.java                 # (renamed to Order)
│   ├── MyError.java                 # (moved to optional audit)
│   ├── Payment.java
│   ├── Producer.java
│   ├── Product.java
│   ├── Shop.java
│   ├── Stock.java
│   ├── Trade.java
│   └── enums/
│       ├── EGuarantee.java
│       └── EPayment.java
├── repository/                      # Repository (manual JPA)
│   ├── generic/
│   │   ├── AbstractGenericRepository.java
│   │   └── GenericRepository.java
│   │       └── (replaces with Spring Data JpaRepository)
│   ├── implementation/
│   │   ├── (11 manual repo implementations)
│   │   └── (replaces with Spring Data derived queries)
│   └── repositoryInterfaces/
│       └── (11 repo interfaces - keep but enhance)
└── service/                         # Services (13 classes)
    ├── (manual service implementations)
    └── (consolidates into vertical slice services)
```

### Target Structure (New)

```
com/storemanagement/
├── StoreManagementApplication.java  # Spring Boot entry point
├── config/                          # Bean definitions & framework setup
│   ├── JpaConfig.java
│   ├── ValidationConfig.java
│   ├── OpenApiConfig.java
│   └── ...
├── core/                            # Cross-cutting concerns
│   ├── exception/                   # Consolidated exceptions
│   ├── validation/                  # Reusable validators
│   ├── logging/                     # AOP logging
│   ├── utils/                       # Shared utilities
│   └── dto/                         # Shared response DTOs
├── domain/                          # Business logic (vertical slices)
│   ├── reference/                   # Phase 1
│   │   ├── country/
│   │   ├── category/
│   │   ├── trade/
│   │   └── payment/
│   ├── master/                      # Phase 2
│   │   ├── customer/
│   │   ├── producer/
│   │   └── shop/
│   ├── catalog/                     # Phases 3-4
│   │   ├── product/
│   │   └── inventory/
│   ├── order/                       # Phase 5
│   └── reporting/                   # Phase 6
└── infra/                           # Infrastructure
    ├── persistence/
    └── audit/
```

---

## 3) Detailed File Mapping by Entity

### Entity: Country

| Aspect | Current Location | Target Location | Changes |
|--------|------------------|-----------------|---------|
| **Entity** | `model/Country.java` | `domain/reference/country/entity/Country.java` | Add Spring Data/JPA annotations if missing |
| **Repository Interface** | `repository/repositoryInterfaces/CountryRepository.java` | `domain/reference/country/repository/CountryRepository.java` | Convert to Spring Data `JpaRepository<Country, Long>` |
| **Repository Implementation** | `repository/implementation/CountryRepositoryImpl.java` | *Deleted* | Spring Data removes need for manual impl |
| **Service Interface** | *None (implied)* | `domain/reference/country/service/CountryService.java` | NEW: Define CRUD + domain ops |
| **Service Implementation** | `service/CountryService.java` | `domain/reference/country/service/CountryServiceImpl.java` | Move; add `@Service`, `@Transactional` |
| **DTOs** | (scattered in `dto/`) | `domain/reference/country/dto/` | NEW: Separate `CountryCreateDto`, `CountryUpdateDto`, `CountryResponseDto` |
| **Controller** | *None* | `domain/reference/country/controller/CountryController.java` | NEW: REST `@RestController` with CRUD endpoints |
| **Validator** | `dataInDbValidation/` | `core/validation/validators/` + optional `domain/reference/country/validator/` | Consolidate; reusable beans |

### Entity: Customer

| Aspect | Current Location | Target Location | Changes |
|--------|------------------|-----------------|---------|
| **Entity** | `model/Customer.java` | `domain/master/customer/entity/Customer.java` | Add audit fields, JPA constraints |
| **Repository** | `repository/implementation/CustomerRepositoryImpl.java` → interface | `domain/master/customer/repository/` | Extend `JpaRepository`; add `CustomerQueryRepository` for complex queries |
| **Service** | `service/CustomerService.java` | `domain/master/customer/service/` | Add `@Transactional`, `@Service` |
| **DTOs** | `dto/customerDto/` (scattered) | `domain/master/customer/dto/` | Consolidate; separate create/update/response |
| **Controller** | *None* | `domain/master/customer/controller/CustomerController.java` | NEW: REST endpoints; separate query controller for read-only ops |
| **Validation** | `dataInDbValidation/` + service logic | `domain/master/customer/validator/CustomerValidator.java` | Dedicated validator for business rules |

### Entity: Product (Complex Aggregate)

| Aspect | Current Location | Target Location | Changes |
|--------|------------------|-----------------|---------|
| **Entity** | `model/Product.java` | `domain/catalog/product/entity/Product.java` | Add guarantee collection, JPA mapping, fetch strategies |
| **Enum** | `model/enums/EGuarantee.java` | `domain/catalog/product/entity/GuaranteeType.java` | Consolidate to same package |
| **Repository** | `repository/implementation/ProductRepositoryImpl.java` | `domain/catalog/product/repository/ProductRepository.java` + `ProductQueryRepository.java` | Spring Data + custom queries for filtering |
| **Service** | `service/ProductService.java` | `domain/catalog/product/service/ProductServiceImpl.java` | Add dependency injection for `CategoryService`, `ProducerService`, `TradeService` |
| **DTOs** | `dto/` (multiple product dtos) | `domain/catalog/product/dto/` | Consolidate; add `ProductDetailDto` for nested relations |
| **Controller** | *None* | `domain/catalog/product/controller/ProductController.java` + `ProductQueryController.java` | REST endpoints + advanced filtering/search |
| **Inventory (Stock)** | `model/Stock.java` + service | `domain/catalog/inventory/` (separate bounded context) | New vertical slice under `catalog` domain |

### Entity: Order (Transactional - High Complexity)

| Aspect | Current Location | Target Location | Changes |
|--------|------------------|-----------------|---------|
| **Entity** | `model/MyOrder.java` | `domain/order/entity/Order.java` | Rename; add order number generation, audit, FK constraints |
| **Repository** | `repository/implementation/OrderRepositoryImpl.java` | `domain/order/repository/OrderRepository.java` + `OrderQueryRepository.java` | Spring Data + complex query patterns |
| **Service** | `service/OrderService.java` | `domain/order/service/OrderServiceImpl.java` | Add transactional workflow orchestration |
| **Validators** | `dataInDbValidation/` | `domain/order/service/OrderValidator.java` | NEW: Order-specific business rule validation |
| **Generators** | *Implicit in service* | `domain/order/service/OrderNumberGenerator.java` | NEW: Dedicated class for order number generation |
| **Workflow** | *Implicit* | `domain/order/service/OrderWorkflow.java` | NEW: Orchestrate multi-step order operations |
| **Domain Events** | *None* | `domain/order/event/OrderCreatedEvent.java`, etc. | NEW: Event publishing for order lifecycle (optional, for future) |
| **DTOs** | `dto/` (scattered) | `domain/order/dto/` | Consolidate; separate create/update/response + search |
| **Controller** | *None* | `domain/order/controller/OrderController.java` + `OrderQueryController.java` | NEW: REST API for order lifecycle |

### New: MyError Entity

| Current | Target | Changes |
|---------|--------|---------|
| `model/MyError.java` service | `domain/reporting/entity/` or optional audit service | Either keep as read-only audit log or consolidate to infrastructure logging |

---

## 4) Cross-Cutting Concerns: From Scattered to Centralized

### Exception Handling

**Current**:
```
exceptions/
├── (various exception classes, scattered usage)
```

**Target**:
```
core/exception/
├── GlobalExceptionHandler.java       # @RestControllerAdvice (centralized)
├── ApiException.java                 # Base exception
├── EntityNotFoundException.java       # 404
├── BusinessRuleException.java        # 422
├── ValidationException.java          # 400
├── ConflictException.java            # 409
└── ErrorResponse.java                # Standard error DTO
```

**Impact**: Consistent error responses, easier debugging, centralized status code mapping

---

### Validation

**Current**:
```
dataInDbValidation/
├── (manual validation logic scattered in services)
```

**Target**:
```
core/validation/
├── validators/
│   ├── EmailValidator.java
│   ├── UniqueEmailValidator.java     # Cross-DB check
│   └── CurrencyValidator.java
├── ValidationMessagesConfig.java
└── (domain-specific validators in each service/)

domain/{domain}/{entity}/validator/
├── {Entity}Validator.java            # Business rule validation
```

**Impact**: 
- Reusable, composable validators
- Jakarta Bean Validation integration
- i18n error messages
- Separated from service logic

---

### Dependency Injection & Wiring

**Current**:
```
service/
├── {Entity}Service.java
└── (manual wiring, possibly service locator pattern)
```

**Target**:
```
service/
├── {Entity}Service.java              # Interface
├── {Entity}ServiceImpl.java           # @Service with @RequiredArgsConstructor
└── (constructor injection; Spring manages DI)

config/
├── (Optional: @Bean definitions for complex beans)
```

**Impact**: 
- Constructor injection (immutability)
- Spring manages bean lifecycle
- Automatic wiring of dependencies
- Easier testing (mock injection)

---

### Configuration Management

**Current**:
```
persistence.xml
(static configuration, single environment)
```

**Target**:
```
application.yml              # Default (dev)
application-dev.yml          # Development profile
application-prod.yml         # Production profile
application-test.yml         # Test profile
config/
├── JpaConfig.java
├── ValidationConfig.java
└── ...
```

**Impact**: 
- Profile-based configuration
- Environment variables support (`${DB_HOST:localhost}`)
- Single source of truth
- Easy environment switching

---

### Data AccessLayer

**Current**:
```
repository/
├── generic/AbstractGenericRepository.java    # Manual base
├── implementation/
│   ├── CountryRepositoryImpl.java
│   └── (11 manual implementations)
└── repositoryInterfaces/
    └── (interfaces)

(Manual Hibernate session management, manual transaction handling)
```

**Target**:
```
domain/{domain}/{entity}/repository/
├── {Entity}Repository.java            # extends JpaRepository
└── {Entity}QueryRepository.java        # @Repository, custom @Query methods

infra/persistence/
├── specification/
│   └── {Entity}Specification.java     # Criteria builder
└── converter/
    └── {Type}Converter.java           # JPA @Converter

(Spring Data JPA manages sessions, @Transactional handles TX)
```

**Impact**: 
- Massive code reduction (eliminate 11+ manual repository impls)
- Derived query methods (no SQL for CRUD)
- Dynamic filtering via Specifications
- Spring manages Hibernate session lifecycle

---

### UI Layer Removal

**Current**:
```
menu/
├── MenuPanel.java         (Swing/console, 554 lines)
├── MenuService.java       (851 lines)
└── MenuStatistics.java    (142 lines)
```

**Target**:
```
domain/{domain}/{entity}/controller/
├── {Entity}Controller.java            # @RestController
└── (REST endpoints replace CLI)

(Plus OpenAPI auto-documentation)
```

**Impact**: 
- Stateless REST API (scalable)
- Automatic API documentation (Swagger)
- Consumable by web/mobile clients
- Parallel requests instead of sequential menu loops

---

## 5) Database Migration Strategy

### Current State

- Manual table creation (likely SQL+ scripts or DDL scattered)
- Single MySQL database
- Manual schema versioning (if any)

### Target State

**Flyway-Managed Migrations**:

```
db/migration/
├── V1__Initial_Schema_Reference.sql    # Country, Category, Trade, Payment
├── V2__Create_Master_Entities.sql      # Customer, Shop, Producer
├── V3__Create_Product_Catalog.sql      # Product + guarantee collection
├── V4__Create_Inventory_Stock.sql      # Stock
├── V5__Create_Order_Transactions.sql   # Order
├── V6__Create_Reporting_Views.sql      # Read models
└── V7__Add_Indexes_And_Constraints.sql # Optimization + FK constraints
```

**Migration Execution**:
1. Application startup → Flyway scans `db/migration/`
2. Compares checksums with `flyway_schema_history` table
3. Executes new migrations in sequence
4. Records success; prevents re-execution

**Benefits**:
- Version control for schema (Git history)
- Automatic on deployment
- Reproducible environments (dev/test/prod same schema)
- Phased rollout support (phase 1 schema separate from phase 5)
- Easy rollback (previous migration restored if needed)

---

## 6) Testing Strategy Evolution

### Current

- Basic JUnit 4 tests (if any)
- Limited mocking
- No CI/CD integration setup

### Target

| Test Type | Current | Target | Count |
|-----------|---------|--------|-------|
| **Unit Tests** | *Minimal* | Service + validator tests (mocked repos) | ~50-70 |
| **Integration Tests** | *None* | Service + repo with real DB (Testcontainers) | ~60-80 |
| **Contract Tests** | *None* | REST endpoint structure + status codes | ~30-40 |
| **E2E Tests** | *None* | Multi-step workflows (order from start→finish) | ~10-20 |
| **Test Data** | Manual setup | Fixtures/builders for reusable test data | Supporting |

**Testing Pyramid**:
```
        /\
       /E2E\          (10%, slow, comprehensive)
      /----\
     /Integration\    (25%, medium, database)
    /-----\
   /Unit  \          (65%, fast, isolated)
  /---------\
```

**Tools**:
- **Framework**: JUnit 5
- **Mocking**: Mockito
- **Assertions**: AssertJ
- **Database**: Testcontainers (PostgreSQL)
- **REST Testing**: MockMvc, RestAssured
- **Coverage**: JaCoCo (optional)

---

## 7) Key Technological Shifts

| Technology | Old | New | Why |
|-----------|-----|-----|-----|
| **Java** | 11 | 21 | Virtual threads, records, pattern matching |
| **Framework** | Manual Hibernate | Spring Boot 3 | Auto-config, reduced boilerplate, community support |
| **Database** | MySQL 8.0 | PostgreSQL 15 | Better JSON, arrays, enums, standards compliance |
| **Dependency Mgmt** | Manual POM | Maven with BOM | Aligned versions, managed transitive deps |
| **HTTP** | Swing/Console | REST API | Scalable, stateless, client-agnostic |
| **Schema Versioning** | Manual/ad-hoc | Flyway | Reproducible, versioned, auto-executed |
| **Configuration** | XML + properties | YAML + profiles | Environment-specific, cleaner syntax |
| **API Documentation** | Manual/none | OpenAPI 3.0 (Springdoc) | Auto-generated, interactive Swagger UI |
| **Logging** | System.out | SLF4J + Logback | Structured, configurable, performance optimized |
| **Boilerplate Reduction** | Manual code | Lombok | Less getter/setter/constructor code |

---

## 8) Development Workflow Changes

### Old Workflow

1. Start Swing/Console application
2. Navigate CLI menus
3. Perform operation
4. See console output or file updates
5. Manual verification against database
6. Repeat for each workflow scenario

### New Workflow

1. Start Spring Boot application (`mvn spring-boot:run`)
2. POST/GET to REST API (`curl`, Postman, client SDK)
3. Receive JSON response with status code
4. Auto-generated Swagger UI at `http://localhost:8080/swagger-ui.html`
5. Browse API docs, test endpoints interactively
6. Integration tests verify workflows automatically
7. CI/CD pipeline runs on Git push

---

## 9) Developer Experience Improvements

| Aspect | Before | After |
|--------|--------|-------|
| **Code Boilerplate** | High (manual repos, service wiring, getters/setters) | Low (Spring Data, Lombok, DI) |
| **Finding Code** | Search by functionality (scattered) | Navigate by domain (vertical slices) |
| **Adding Feature** | Modify entity → update DTO → update service → update menu | Create DTO folder, service, controller in vertical slice |
| **Running Tests** | Ad-hoc manual testing | `mvn test` runs all unit tests; `mvn verify` runs integration |
| **API Documentation** | Maintain Markdown manually | Auto-generated from code annotations |
| **Database Changes** | Manual SQL execution | `V{N}__description.sql` in migration folder |
| **Local Development** | Setup MySQL manually | Docker Compose with Testcontainers |
| **Configuration** | Edit persistence.xml or properties files | `application-dev.yml` with environment variables |
| **Debugging** | Console logs + manual tracing | Spring Boot actuators + structured logging |
| **Deployment** | JAR + manual schema setup | Single JAR + Flyway auto-migrates schema |

---

## 10) Migration Execution Plan

### Step 1: Preparation (Week 1)

- [ ] Create new Maven POM with Spring Boot 3.5 BOM
- [ ] Set up Spring Boot entry point (`StoreManagementApplication.java`)
- [ ] Create base configuration classes (`JpaConfig`, `ValidationConfig`, `OpenApiConfig`)
- [ ] Implement core infrastructure (`AuditableEntity`, `GlobalExceptionHandler`, base DTOs)

### Step 2: Phase 1 Reference Data (Week 2-3)

- [ ] Create `domain/reference/` package structure
- [ ] Migrate entities: `Country`, `Category`, `Trade`, `Payment`
- [ ] Create Spring Data repositories (remove manual impls)
- [ ] Implement services + service interfaces
- [ ] Create DTOs (Create/Update/Response per entity)
- [ ] Build REST controllers
- [ ] Write Flyway V1 migration (`V1__Initial_Schema_Reference.sql`)
- [ ] Implement unit + integration tests for Phase 1

### Step 3: Phase 2 Master Data (Week 4)

- [ ] Create `domain/master/` package structure
- [ ] Migrate `Customer`, `Shop`, `Producer`
- [ ] Create services + DTOs + controllers
- [ ] Write Flyway V2 migration
- [ ] Implement tests

### Step 4: Phases 3-5 (Weeks 5-8)

- [ ] Repeat for `Product`, `Stock`, `Order`
- [ ] Write corresponding Flyway migrations
- [ ] Comprehensive testing at each phase

### Step 5: Phase 6 + Optimization (Week 9)

- [ ] Create reporting domain
- [ ] Write Flyway V6 migration (read models)
- [ ] Build reporting services + controllers
- [ ] Write Flyway V7 optimization (indexes, constraints)
- [ ] Final testing + performance tuning

### Step 6: Documentation & Deployment (Week 10)

- [ ] Finalize OpenAPI documentation (auto-generated)
- [ ] Document deployment procedures
- [ ] Set up CI/CD pipeline
- [ ] Production readiness checklist
- [ ] Team training on new structure

---

## 11) Rollback & Contingency

### If Phase Fails

- **Stop**: Do not proceed to next phase
- **Investigation**: Debug with new testing framework
- **Rollback**: 
  - Database: Flyway can migrate to previous version
  - Code: Git revert to previous Spring Boot version
  - Data: Export from old MySQL instance if needed

### Parallel Running

For critical operations (optional):
- Keep old Swing app running alongside new REST API
- Read from shared database
- Gradually redirect API calls to new version
- Disable old UI once stable

---

## 12) Training & Knowledge Transfer

### Key Topics for Developers

1. **Package Structure**: Vertical slices vs. traditional horizontal layers
2. **Spring Data JPA**: Derived queries, Specifications, pagination
3. **REST API Design**: HTTP methods, status codes, resource-based modeling
4. **DTOs**: Why separate from entities, mapping between layers
5. **Testing Strategy**: Unit vs. integration vs. contract vs. e2e
6. **Flyway Migrations**: Version control for schema, execution order
7. **Configuration Management**: Profiles, environment variables, YAML
8. **Lombok**: Annotations to reduce boilerplate

### Recommended Resources

- Spring Boot 3 official docs
- Spring Data JPA guide
- OpenAPI 3.0 specification
- Flyway documentation
- Clean Architecture by Robert C. Martin

---

## 13) Success Criteria

✅ All entities migrated with Spring Data repositories  
✅ All services converted to Spring Boot + DI pattern  
✅ All DTOs organized in vertical slice packages  
✅ All REST endpoints created with OpenAPI docs  
✅ All Flyway migrations (V1-V7) created and tested  
✅ Test coverage: >80% (unit + integration)  
✅ API documentation auto-generated and accessible  
✅ Application deploys as single JAR  
✅ Database schema auto-migrates on startup  
✅ No manual configuration needed (environment variables only)  
✅ Performance at least equal to old system  
✅ Team trained and confident with new structure  

---

## 14) Reference Documents

- **`SPRING_BOOT_3_ARCHITECTURE.md`**: Detailed architecture decisions, rationale, patterns
- **`SPRING_BOOT_3_FOLDER_TREE.md`**: Visual folder structure with navigation guide
- **`DOMAIN_DEPENDENCY_ANALYSIS.md`**: Entity relationships, phased migration roadmap
- **`MODERNIZATION_REPORT.md`**: Per-class migration decisions, risk assessment

---

**Created**: 2026-06-10  
**Status**: Ready for implementation  
**Next**: Create Maven POM with Spring Boot 3.5 BOM

