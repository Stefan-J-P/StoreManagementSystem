# Spring Boot 3 Project Folder Tree (Quick Reference)

```
store-management-system/
│
├── pom.xml                                 (Maven configuration)
├── .gitignore                             (Git ignore rules)
├── README.md                              (Project overview)
│
├── README-ARCHITECTURE.md                 (This document reference)
├── DOMAIN_DEPENDENCY_ANALYSIS.md         (Domain model & dependencies)
├── MODERNIZATION_REPORT.md               (Migration guidance)
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/storemanagement/       (←← ROOT PACKAGE)
│   │   │       │
│   │   │       ├── StoreManagementApplication.java
│   │   │       │
│   │   │       ├── config/                (Bean definitions)
│   │   │       │   ├── JpaConfig.java
│   │   │       │   ├── ValidationConfig.java
│   │   │       │   ├── OpenApiConfig.java
│   │   │       │   ├── WebConfig.java
│   │   │       │   ├── SecurityConfig.java
│   │   │       │   └── CachingConfig.java
│   │   │       │
│   │   │       ├── core/                  (Cross-cutting concerns)
│   │   │       │   ├── exception/
│   │   │       │   │   ├── GlobalExceptionHandler.java
│   │   │       │   │   ├── ApiException.java
│   │   │       │   │   ├── EntityNotFoundException.java
│   │   │       │   │   ├── BusinessRuleException.java
│   │   │       │   │   ├── ValidationException.java
│   │   │       │   │   ├── ConflictException.java
│   │   │       │   │   ├── ErrorResponse.java
│   │   │       │   │   └── ErrorCode.java
│   │   │       │   │
│   │   │       │   ├── logging/
│   │   │       │   │   ├── LoggingAspect.java
│   │   │       │   │   ├── LoggingFilter.java
│   │   │       │   │   └── PerformanceMonitor.java
│   │   │       │   │
│   │   │       │   ├── validation/
│   │   │       │   │   ├── validators/
│   │   │       │   │   │   ├── EmailValidator.java
│   │   │       │   │   │   ├── UniqueEmailValidator.java
│   │   │       │   │   │   ├── UniqueNameValidator.java
│   │   │       │   │   │   └── CurrencyValidator.java
│   │   │       │   │   └── ValidationMessagesConfig.java
│   │   │       │   │
│   │   │       │   ├── utils/
│   │   │       │   │   ├── JsonUtil.java
│   │   │       │   │   ├── DateTimeUtil.java
│   │   │       │   │   ├── NumberFormatUtil.java
│   │   │       │   │   └── SlugUtil.java
│   │   │       │   │
│   │   │       │   └── dto/
│   │   │       │       ├── ErrorResponseDto.java
│   │   │       │       ├── ApiResponseDto.java
│   │   │       │       ├── PaginationDto.java
│   │   │       │       └── AuditableDto.java
│   │   │       │
│   │   │       ├── domain/                (Business logic - vertical slices)
│   │   │       │   │
│   │   │       │   ├── reference/        (←← PHASE 1: Reference Data)
│   │   │       │   │   ├── country/
│   │   │       │   │   │   ├── entity/
│   │   │       │   │   │   │   └── Country.java
│   │   │       │   │   │   ├── repository/
│   │   │       │   │   │   │   └── CountryRepository.java
│   │   │       │   │   │   ├── service/
│   │   │       │   │   │   │   ├── CountryService.java
│   │   │       │   │   │   │   └── CountryServiceImpl.java
│   │   │       │   │   │   ├── controller/
│   │   │       │   │   │   │   └── CountryController.java
│   │   │       │   │   │   └── dto/
│   │   │       │   │   │       ├── CountryCreateDto.java
│   │   │       │   │   │       ├── CountryUpdateDto.java
│   │   │       │   │   │       ├── CountryResponseDto.java
│   │   │       │   │   │       └── CountrySearchDto.java
│   │   │       │   │   │
│   │   │       │   │   ├── category/
│   │   │       │   │   │   ├── entity/
│   │   │       │   │   │   │   └── Category.java
│   │   │       │   │   │   ├── repository/
│   │   │       │   │   │   │   └── CategoryRepository.java
│   │   │       │   │   │   ├── service/
│   │   │       │   │   │   │   ├── CategoryService.java
│   │   │       │   │   │   │   └── CategoryServiceImpl.java
│   │   │       │   │   │   ├── controller/
│   │   │       │   │   │   │   └── CategoryController.java
│   │   │       │   │   │   └── dto/
│   │   │       │   │   │       ├── CategoryCreateDto.java
│   │   │       │   │   │       ├── CategoryUpdateDto.java
│   │   │       │   │   │       └── CategoryResponseDto.java
│   │   │       │   │   │
│   │   │       │   │   ├── trade/
│   │   │       │   │   │   ├── entity/ → Trade.java
│   │   │       │   │   │   ├── repository/ → TradeRepository.java
│   │   │       │   │   │   ├── service/ → TradeService.java, TradeServiceImpl.java
│   │   │       │   │   │   ├── controller/ → TradeController.java
│   │   │       │   │   │   └── dto/ → TradeCreateDto.java, etc.
│   │   │       │   │   │
│   │   │       │   │   └── payment/
│   │   │       │   │       ├── entity/
│   │   │       │   │       │   ├── Payment.java
│   │   │       │   │       │   └── PaymentType.java (enum)
│   │   │       │   │       ├── repository/ → PaymentRepository.java
│   │   │       │   │       ├── service/ → PaymentService.java, PaymentServiceImpl.java
│   │   │       │   │       ├── controller/ → PaymentController.java
│   │   │       │   │       └── dto/ → PaymentCreateDto.java, etc.
│   │   │       │   │
│   │   │       │   ├── master/          (←← PHASE 2: Master Data)
│   │   │       │   │   ├── customer/
│   │   │       │   │   │   ├── entity/ → Customer.java
│   │   │       │   │   │   ├── repository/ → CustomerRepository.java, CustomerQueryRepository.java
│   │   │       │   │   │   ├── service/ → CustomerService.java, CustomerServiceImpl.java, CustomerValidator.java
│   │   │       │   │   │   ├── controller/ → CustomerController.java, CustomerQueryController.java
│   │   │       │   │   │   └── dto/ → CustomerCreateDto.java, etc.
│   │   │       │   │   │
│   │   │       │   │   ├── producer/
│   │   │       │   │   │   ├── entity/ → Producer.java
│   │   │       │   │   │   ├── repository/ → ProducerRepository.java
│   │   │       │   │   │   ├── service/ → ProducerService.java, ProducerServiceImpl.java
│   │   │       │   │   │   ├── controller/ → ProducerController.java
│   │   │       │   │   │   └── dto/ → ProducerCreateDto.java, etc.
│   │   │       │   │   │
│   │   │       │   │   └── shop/
│   │   │       │   │       ├── entity/ → Shop.java
│   │   │       │   │       ├── repository/ → ShopRepository.java
│   │   │       │   │       ├── service/ → ShopService.java, ShopServiceImpl.java
│   │   │       │   │       ├── controller/ → ShopController.java
│   │   │       │   │       └── dto/ → ShopCreateDto.java, etc.
│   │   │       │   │
│   │   │       │   ├── catalog/         (←← PHASE 3-4: Product & Inventory)
│   │   │       │   │   ├── product/
│   │   │       │   │   │   ├── entity/
│   │   │       │   │   │   │   ├── Product.java
│   │   │       │   │   │   │   └── GuaranteeType.java (enum)
│   │   │       │   │   │   ├── repository/
│   │   │       │   │   │   │   ├── ProductRepository.java
│   │   │       │   │   │   │   └── ProductQueryRepository.java
│   │   │       │   │   │   ├── service/
│   │   │       │   │   │   │   ├── ProductService.java
│   │   │       │   │   │   │   ├── ProductServiceImpl.java
│   │   │       │   │   │   │   └── ProductValidator.java
│   │   │       │   │   │   ├── controller/
│   │   │       │   │   │   │   ├── ProductController.java
│   │   │       │   │   │   │   └── ProductQueryController.java
│   │   │       │   │   │   └── dto/ → ProductCreateDto.java, etc.
│   │   │       │   │   │
│   │   │       │   │   └── inventory/
│   │   │       │   │       ├── entity/ → Stock.java
│   │   │       │   │       ├── repository/ → StockRepository.java, StockQueryRepository.java
│   │   │       │   │       ├── service/ → StockService.java, StockServiceImpl.java, InventoryRuleValidator.java
│   │   │       │   │       ├── controller/ → StockController.java, InventoryController.java
│   │   │       │   │       └── dto/ → StockCreateDto.java, etc.
│   │   │       │   │
│   │   │       │   ├── order/           (←← PHASE 5: Order Transactions)
│   │   │       │   │   ├── entity/ → Order.java
│   │   │       │   │   ├── repository/ → OrderRepository.java, OrderQueryRepository.java
│   │   │       │   │   ├── service/
│   │   │       │   │   │   ├── OrderService.java
│   │   │       │   │   │   ├── OrderServiceImpl.java
│   │   │       │   │   │   ├── OrderValidator.java
│   │   │       │   │   │   ├── OrderNumberGenerator.java
│   │   │       │   │   │   └── OrderWorkflow.java
│   │   │       │   │   ├── event/
│   │   │       │   │   │   ├── OrderCreatedEvent.java
│   │   │       │   │   │   ├── OrderConfirmedEvent.java
│   │   │       │   │   │   └── OrderEventPublisher.java
│   │   │       │   │   ├── controller/ → OrderController.java, OrderQueryController.java
│   │   │       │   │   └── dto/ → OrderCreateDto.java, etc.
│   │   │       │   │
│   │   │       │   └── reporting/       (←← PHASE 6: Reporting & Analytics)
│   │   │       │       ├── entity/
│   │   │       │       │   └── ProductByCountryView.java (read model)
│   │   │       │       ├── repository/
│   │   │       │       │   ├── ReportingRepository.java
│   │   │       │       │   └── AnalyticsRepository.java
│   │   │       │       ├── service/
│   │   │       │       │   ├── ReportingService.java
│   │   │       │       │   ├── ReportingServiceImpl.java
│   │   │       │       │   ├── AnalyticsService.java
│   │   │       │       │   └── AnalyticsServiceImpl.java
│   │   │       │       ├── controller/
│   │   │       │       │   ├── ReportController.java
│   │   │       │       │   └── AnalyticsController.java
│   │   │       │       └── dto/ → ProductsByCountryReportDto.java, etc.
│   │   │       │
│   │   │       └── infra/               (Infrastructure & persistence)
│   │   │           ├── persistence/
│   │   │           │   ├── converter/
│   │   │           │   │   ├── GuaranteeTypeConverter.java
│   │   │           │   │   └── PaymentTypeConverter.java
│   │   │           │   ├── listener/
│   │   │           │   │   └── AuditingEntityListener.java
│   │   │           │   └── specification/
│   │   │           │       ├── EntitySpecification.java
│   │   │           │       ├── CustomerSpecification.java
│   │   │           │       ├── ProductSpecification.java
│   │   │           │       └── OrderSpecification.java
│   │   │           │
│   │   │           └── audit/
│   │   │               ├── AuditableEntity.java (base class)
│   │   │               └── AuditableEntityListener.java
│   │   │
│   │   ├── resources/                    (Configuration & data)
│   │   │   ├── application.yml           (Main config)
│   │   │   ├── application-dev.yml       (Development)
│   │   │   ├── application-prod.yml      (Production)
│   │   │   ├── application-test.yml      (Testing)
│   │   │   │
│   │   │   ├── META-INF/
│   │   │   │   ├── persistence.xml
│   │   │   │   └── spring.properties
│   │   │   │
│   │   │   ├── db/
│   │   │   │   └── migration/            (Flyway migrations)
│   │   │   │       ├── V1__Initial_Schema_Reference.sql
│   │   │   │       ├── V2__Create_Master_Entities.sql
│   │   │   │       ├── V3__Create_Product_Catalog.sql
│   │   │   │       ├── V4__Create_Inventory_Stock.sql
│   │   │   │       ├── V5__Create_Order_Transactions.sql
│   │   │   │       ├── V6__Create_Reporting_Views.sql
│   │   │   │       └── V7__Add_Indexes_And_Constraints.sql
│   │   │   │
│   │   │   └── i18n/                    (Internationalization)
│   │   │       ├── messages.properties
│   │   │       ├── messages_en.properties
│   │   │       └── messages_de.properties
│   │   │
│   │   └── asciidocs/                   (OpenAPI documentation)
│   │       └── index.adoc
│   │
│   ├── test/
│   │   ├── java/
│   │   │   └── com/storemanagement/
│   │   │       │
│   │   │       ├── unit/                (Unit tests - isolated)
│   │   │       │   ├── domain/
│   │   │       │   │   ├── reference/
│   │   │       │   │   │   ├── service/
│   │   │       │   │   │   │   ├── CountryServiceTest.java
│   │   │       │   │   │   │   ├── CategoryServiceTest.java
│   │   │       │   │   │   │   ├── TradeServiceTest.java
│   │   │       │   │   │   │   └── PaymentServiceTest.java
│   │   │       │   │   │   └── ...
│   │   │       │   │   │
│   │   │       │   │   ├── master/
│   │   │       │   │   │   ├── service/
│   │   │       │   │   │   │   ├── CustomerServiceTest.java
│   │   │       │   │   │   │   ├── ProducerServiceTest.java
│   │   │       │   │   │   │   └── ShopServiceTest.java
│   │   │       │   │   │   └── ...
│   │   │       │   │   │
│   │   │       │   │   ├── catalog/
│   │   │       │   │   │   ├── service/
│   │   │       │   │   │   │   ├── ProductServiceTest.java
│   │   │       │   │   │   │   └── StockServiceTest.java
│   │   │       │   │   │   └── ...
│   │   │       │   │   │
│   │   │       │   │   ├── order/
│   │   │       │   │   │   ├── service/
│   │   │       │   │   │   │   ├── OrderServiceTest.java
│   │   │       │   │   │   │   └── OrderValidatorTest.java
│   │   │       │   │   │   └── ...
│   │   │       │   │   │
│   │   │       │   │   └── reporting/
│   │   │       │   │       ├── service/
│   │   │       │   │       │   └── ReportingServiceTest.java
│   │   │       │   │       └── ...
│   │   │       │   │
│   │   │       │   ├── core/
│   │   │       │   │   ├── exception/
│   │   │       │   │   │   └── GlobalExceptionHandlerTest.java
│   │   │       │   │   ├── validation/
│   │   │       │   │   │   ├── EmailValidatorTest.java
│   │   │       │   │   │   └── UniqueEmailValidatorTest.java
│   │   │       │   │   └── utils/
│   │   │       │   │       ├── DateTimeUtilTest.java
│   │   │       │   │       └── NumberFormatUtilTest.java
│   │   │       │   │
│   │   │       │   └── infra/
│   │   │       │       ├── persistence/
│   │   │       │       │   └── SpecificationTest.java
│   │   │       │       └── audit/
│   │   │       │           └── AuditableEntityTest.java
│   │   │       │
│   │   │       ├── integration/         (Integration tests - with DB)
│   │   │       │   ├── config/
│   │   │       │   │   ├── TestDatabaseConfig.java
│   │   │       │   │   ├── TestContainersConfig.java
│   │   │       │   │   └── IntegrationTestBase.java
│   │   │       │   │
│   │   │       │   ├── domain/
│   │   │       │   │   ├── reference/
│   │   │       │   │   │   ├── repository/
│   │   │       │   │   │   │   └── CountryRepositoryIT.java
│   │   │       │   │   │   └── service/
│   │   │       │   │   │       └── CountryServiceIT.java
│   │   │       │   │   │
│   │   │       │   │   ├── master/
│   │   │       │   │   │   ├── repository/
│   │   │       │   │   │   │   └── CustomerRepositoryIT.java
│   │   │       │   │   │   └── service/
│   │   │       │   │   │       └── CustomerServiceIT.java
│   │   │       │   │   │
│   │   │       │   │   ├── catalog/
│   │   │       │   │   │   ├── repository/
│   │   │       │   │   │   │   ├── ProductRepositoryIT.java
│   │   │       │   │   │   │   └── StockRepositoryIT.java
│   │   │       │   │   │   └── service/
│   │   │       │   │   │       ├── ProductServiceIT.java
│   │   │       │   │   │       └── StockServiceIT.java
│   │   │       │   │   │
│   │   │       │   │   ├── order/
│   │   │       │   │   │   ├── repository/
│   │   │       │   │   │   │   └── OrderRepositoryIT.java
│   │   │       │   │   │   └── service/
│   │   │       │   │   │       └── OrderServiceIT.java
│   │   │       │   │   │
│   │   │       │   │   └── reporting/
│   │   │       │   │       └── service/
│   │   │       │   │           └── ReportingServiceIT.java
│   │   │       │   │
│   │   │       │   └── infra/
│   │   │       │       └── persistence/
│   │   │       │           └── SpecificationIT.java
│   │   │       │
│   │   │       ├── contract/            (REST API contract tests)
│   │   │       │   ├── reference/
│   │   │       │   │   ├── CountryControllerContractTest.java
│   │   │       │   │   ├── CategoryControllerContractTest.java
│   │   │       │   │   ├── TradeControllerContractTest.java
│   │   │       │   │   └── PaymentControllerContractTest.java
│   │   │       │   │
│   │   │       │   ├── master/
│   │   │       │   │   ├── CustomerControllerContractTest.java
│   │   │       │   │   ├── ProducerControllerContractTest.java
│   │   │       │   │   └── ShopControllerContractTest.java
│   │   │       │   │
│   │   │       │   ├── catalog/
│   │   │       │   │   ├── ProductControllerContractTest.java
│   │   │       │   │   └── StockControllerContractTest.java
│   │   │       │   │
│   │   │       │   ├── order/
│   │   │       │   │   └── OrderControllerContractTest.java
│   │   │       │   │
│   │   │       │   └── reporting/
│   │   │       │       ├── ReportControllerContractTest.java
│   │   │       │       └── AnalyticsControllerContractTest.java
│   │   │       │
│   │   │       ├── e2e/                 (End-to-end workflow tests)
│   │   │       │   ├── config/
│   │   │       │   │   ├── E2ETestConfig.java
│   │   │       │   │   └── E2ETestBase.java
│   │   │       │   │
│   │   │       │   └── scenarios/
│   │   │       │       ├── ReferenceDataSetupScenarioTest.java
│   │   │       │       ├── CustomerCreationAndValidationE2ETest.java
│   │   │       │       ├── ProductCatalogSetupE2ETest.java
│   │   │       │       ├── StockManagementE2ETest.java
│   │   │       │       ├── OrderFlowE2ETest.java
│   │   │       │       └── CompleteOrderFulfillmentE2ETest.java
│   │   │       │
│   │   │       └── fixtures/            (Reusable test data)
│   │   │           ├── EntityFixture.java
│   │   │           ├── CountryFixture.java
│   │   │           ├── CustomerFixture.java
│   │   │           ├── ProductFixture.java
│   │   │           ├── OrderFixture.java
│   │   │           └── DtoFixture.java
│   │   │
│   │   └── resources/
│   │       ├── application-test.yml
│   │       ├── test-data.sql
│   │       └── testcontainers/
│   │           ├── docker-compose.yml
│   │           └── README.md
│   │
│   └── it/ (Optional: separate integration test module)
│       ├── java/com/storemanagement/it/
│       │   ├── OrderFullCycleIT.java
│       │   ├── InventoryManagementIT.java
│       │   └── ReportGenerationIT.java
│       ├── resources/
│       │   └── application-it.yml
│       └── pom.xml

```

---

## Navigation Guide

### By Layer (Horizontal):

- **Configuration**: `src/main/java/.../config/`
- **Cross-cutting**: `src/main/java/.../core/`
- **Domain Logic**: `src/main/java/.../domain/`
- **Infrastructure**: `src/main/java/.../infra/`

### By Feature / Vertical Slice (Vertical):

- **Phase 1**: `src/main/java/.../domain/reference/{country,category,trade,payment}/`
- **Phase 2**: `src/main/java/.../domain/master/{customer,producer,shop}/`
- **Phase 3+**: `src/main/java/.../domain/catalog/{product,inventory}/`
- **Phase 5**: `src/main/java/.../domain/order/`
- **Phase 6**: `src/main/java/.../domain/reporting/`

### By Type:

- **Entities**: `src/main/java/.../domain/*/entity/`
- **Repositories**: `src/main/java/.../domain/*/repository/`
- **Services**: `src/main/java/.../domain/*/service/`
- **Controllers**: `src/main/java/.../domain/*/controller/`
- **DTOs**: `src/main/java/.../domain/*/dto/`

### By Testing Type:

- **Unit Tests**: `src/test/java/.../unit/`
- **Integration Tests**: `src/test/java/.../integration/`
- **Contract/REST Tests**: `src/test/java/.../contract/`
- **End-to-End Tests**: `src/test/java/.../e2e/`
- **Test Fixtures**: `src/test/java/.../fixtures/`

### Database Migrations:

- **Flyway Scripts**: `src/main/resources/db/migration/V{N}__*.sql`
- **Phases**: V1 (ref), V2 (master), V3-4 (catalog), V5 (order), V6 (reporting), V7 (optimize)

### Configuration:

- **Profiles**: `src/main/resources/application*.yml`
- **Internationalization**: `src/main/resources/i18n/messages*.properties`
- **Migrations**: `src/main/resources/db/migration/`

---

## Key Directories Summary

| Directory | Purpose | Owner | Phases |
|-----------|---------|-------|--------|
| `config/` | Bean definitions, framework setup | Infra Team | All |
| `core/` | Shared exceptions, validation, utils | Shared | All |
| `domain/reference/` | Country, Category, Trade, Payment | Data Team | 1 |
| `domain/master/` | Customer, Producer, Shop | Master Data Team | 2 |
| `domain/catalog/` | Product, Stock | Catalog Team | 3-4 |
| `domain/order/` | Order & fulfillment | Order Team | 5 |
| `domain/reporting/` | Analytics, reports, read models | Analytics Team | 6 |
| `infra/` | ORM, converters, listeners, queries | Core Team | All |
| `db/migration/` | Flyway SQL scripts | DBA/Infra | All |
| `test/*/` | All tests (unit, integration, e2e) | Quality Team | All |

---

## Conventions at a Glance

| Element | Convention | Example |
|---------|-----------|---------|
| **Root Package** | `com.storemanagement` | — |
| **Entity** | Singular noun, PascalCase | `com.storemanagement.domain.reference.country.entity.Country` |
| **Repository** | `{Entity}Repository` | `CountryRepository` |
| **Service Interface** | `{Entity}Service` | `CountryService` |
| **Service Impl** | `{Entity}ServiceImpl` | `CountryServiceImpl` |
| **Controller** | `{Entity}Controller` | `CountryController` |
| **Create DTO** | `{Entity}CreateDto` | `CountryCreateDto` |
| **Update DTO** | `{Entity}UpdateDto` | `CountryUpdateDto` |
| **Response DTO** | `{Entity}ResponseDto` | `CountryResponseDto` |
| **Validator** | `{Entity}Validator` | `CustomerValidator` |
| **Spec Class** | `{Entity}Specification` | `ProductSpecification` |
| **Migration** | `V{N}__{Description}.sql` | `V1__Initial_Schema_Reference.sql` |
| **Config Class** | `{Feature}Config` | `ValidationConfig` |
| **Test (Unit)** | `{Class}Test` | `CountryServiceTest` |
| **Test (Integration)** | `{Class}IT` | `CountryServiceIT` |
| **Test (Contract)** | `{Class}ContractTest` | `CountryControllerContractTest` |
| **Endpoint** | `/api/v1/{resource}` | `/api/v1/countries` |

---

## Dependencies Between Layers (Clean Architecture)

```
HTTP Request
    ↓
Controller (REST endpoint)
    ↓
Service (business logic, transaction boundary)
    ↓
Repository (data access)
    ↓
Entity (domain model)
    ↓
Database

Note: Exception Handler at top catches errors
      Validators checked at Service/Entity level
      Util classes accessible from everywhere in core
```

---

## File at a Glance

**Total Expected Files** (fully implemented):

- **Java source files**: ~150-200 (entities, services, repos, controllers, configs, validators)
- **DTOs**: ~40-50 (create/update/response per entity and domain)
- **Tests**: ~200+ (unit, integration, contract, e2e)
- **SQL migrations**: 7
- **Config files**: 4 (yml) + 1 (xml)
- **i18n properties**: 3
- **Documentation**: 4+ (README, ARCHITECTURE, API docs auto-generated)

---

## Ready for Implementation ✅

This structure is ready for:
1. **Maven POM creation** with Spring Boot 3.5 BOM
2. **Phase 1 development** (reference entities)
3. **Incremental rollout** following the 6-phase roadmap
4. **Team parallelization** (different teams work on different phases)
5. **Full testing coverage** (unit → integration → e2e)
6. **Auto-generated API documentation** (OpenAPI/Swagger)

---

**Created**: 2026-06-10  
**Reference**: `SPRING_BOOT_3_ARCHITECTURE.md` for detailed explanations

