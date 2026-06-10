# Spring Boot 3 Project Structure Design

**Date**: 2026-06-10  
**Target Stack**: Java 21, Spring Boot 3.5, PostgreSQL, Flyway, Spring Data JPA, Validation, Lombok, OpenAPI (Springdoc)  
**Package Root**: `com.storemanagement`  
**Project Name**: store-management-system  
**Group ID**: `com.storemanagement`  
**Artifact ID**: `store-management-system`  

---

## 1) Overview: Architectural Principles

The new structure follows these principles:

1. **Vertical Slice Organization**: Core business domains organized as bounded contexts
2. **Layered Architecture within Slices**: Entity → Repository → Service → Controller
3. **Spring Boot 3 Conventions**: Respects starter dependencies and auto-configuration
4. **Separation of Concerns**: Clear boundaries between configuration, domain logic, API contracts, and infrastructure
5. **Testability**: Organized to support unit, integration, and contract tests
6. **Maintainability**: Package names reflect business domains, not technical layers
7. **Scalability**: Ready for microservices decomposition or module organization

---

## 2) Complete Folder Tree Structure

```
StoreManagementSystem/
├── pom.xml                                    # Maven configuration
├── .gitignore                                 # Updated for Java 21, Spring Boot 3
├── README.md
├── DOMAIN_DEPENDENCY_ANALYSIS.md             # Existing dependency analysis
├── MODERNIZATION_REPORT.md                   # Existing modernization report
├── SPRING_BOOT_3_ARCHITECTURE.md            # This file
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── storemanagement/
│   │   │           ├── StoreManagementApplication.java     # Spring Boot entry point
│   │   │           │
│   │   │           ├── config/                             # Configuration & beans
│   │   │           │   ├── JpaConfig.java
│   │   │           │   ├── ValidationConfig.java
│   │   │           │   ├── OpenApiConfig.java
│   │   │           │   ├── WebConfig.java
│   │   │           │   ├── SecurityConfig.java             # Future: if adding Spring Security
│   │   │           │   └── CachingConfig.java              # Optional: for Spring Cache
│   │   │           │
│   │   │           ├── core/                               # Cross-cutting / shared concerns
│   │   │           │   ├── exception/
│   │   │           │   │   ├── GlobalExceptionHandler.java
│   │   │           │   │   ├── ApiException.java
│   │   │           │   │   ├── EntityNotFoundException.java
│   │   │           │   │   ├── BusinessRuleException.java
│   │   │           │   │   ├── ValidationException.java
│   │   │           │   │   ├── ConflictException.java
│   │   │           │   │   └── ErrorResponse.java
│   │   │           │   ├── logging/
│   │   │           │   │   └── LoggingAspect.java          # AOP logging for services
│   │   │           │   ├── validation/
│   │   │           │   │   ├── validators/
│   │   │           │   │   │   ├── EmailValidator.java
│   │   │           │   │   │   ├── UniqueEmailValidator.java
│   │   │           │   │   │   └── UniqueNameValidator.java
│   │   │           │   │   └── ValidationMessagesConfig.java
│   │   │           │   ├── utils/
│   │   │           │   │   ├── JsonUtil.java
│   │   │           │   │   ├── DateTimeUtil.java
│   │   │           │   │   └── NumberFormatUtil.java
│   │   │           │   └── dto/                            # Shared DTO contracts
│   │   │           │       ├── ErrorResponseDto.java
│   │   │           │       ├── ApiResponseDto.java
│   │   │           │       └── PaginationDto.java
│   │   │           │
│   │   │           ├── domain/                             # Bounded contexts / vertical slices
│   │   │           │   ├── reference/                      # PHASE 1: Reference data
│   │   │           │   │   ├── country/
│   │   │           │   │   │   ├── entity/
│   │   │           │   │   │   │   └── Country.java
│   │   │           │   │   │   ├── repository/
│   │   │           │   │   │   │   └── CountryRepository.java
│   │   │           │   │   │   ├── service/
│   │   │           │   │   │   │   ├── CountryService.java
│   │   │           │   │   │   │   └── CountryServiceImpl.java
│   │   │           │   │   │   ├── controller/
│   │   │           │   │   │   │   └── CountryController.java
│   │   │           │   │   │   └── dto/
│   │   │           │   │   │       ├── CountryCreateDto.java
│   │   │           │   │   │       ├── CountryUpdateDto.java
│   │   │           │   │   │       └── CountryResponseDto.java
│   │   │           │   │   ├── category/
│   │   │           │   │   │   ├── entity/
│   │   │           │   │   │   │   └── Category.java
│   │   │           │   │   │   ├── repository/
│   │   │           │   │   │   │   └── CategoryRepository.java
│   │   │           │   │   │   ├── service/
│   │   │           │   │   │   │   ├── CategoryService.java
│   │   │           │   │   │   │   └── CategoryServiceImpl.java
│   │   │           │   │   │   ├── controller/
│   │   │           │   │   │   │   └── CategoryController.java
│   │   │           │   │   │   └── dto/
│   │   │           │   │   │       ├── CategoryCreateDto.java
│   │   │           │   │   │       ├── CategoryUpdateDto.java
│   │   │           │   │   │       └── CategoryResponseDto.java
│   │   │           │   │   ├── trade/
│   │   │           │   │   │   ├── entity/
│   │   │           │   │   │   │   └── Trade.java
│   │   │           │   │   │   ├── repository/
│   │   │           │   │   │   │   └── TradeRepository.java
│   │   │           │   │   │   ├── service/
│   │   │           │   │   │   │   ├── TradeService.java
│   │   │           │   │   │   │   └── TradeServiceImpl.java
│   │   │           │   │   │   ├── controller/
│   │   │           │   │   │   │   └── TradeController.java
│   │   │           │   │   │   └── dto/
│   │   │           │   │   │       ├── TradeCreateDto.java
│   │   │           │   │   │       ├── TradeUpdateDto.java
│   │   │           │   │   │       └── TradeResponseDto.java
│   │   │           │   │   └── payment/
│   │   │           │   │       ├── entity/
│   │   │           │   │       │   ├── Payment.java
│   │   │           │   │       │   └── PaymentType.java (enum)
│   │   │           │   │       ├── repository/
│   │   │           │   │       │   └── PaymentRepository.java
│   │   │           │   │       ├── service/
│   │   │           │   │       │   ├── PaymentService.java
│   │   │           │   │       │   └── PaymentServiceImpl.java
│   │   │           │   │       ├── controller/
│   │   │           │   │       │   └── PaymentController.java
│   │   │           │   │       └── dto/
│   │   │           │   │           ├── PaymentCreateDto.java
│   │   │           │   │           ├── PaymentUpdateDto.java
│   │   │           │   │           └── PaymentResponseDto.java
│   │   │           │   │
│   │   │           │   ├── master/                         # PHASE 2: Master data
│   │   │           │   │   ├── customer/
│   │   │           │   │   │   ├── entity/
│   │   │           │   │   │   │   └── Customer.java
│   │   │           │   │   │   ├── repository/
│   │   │           │   │   │   │   ├── CustomerRepository.java
│   │   │           │   │   │   │   └── CustomerQueryRepository.java (custom queries)
│   │   │           │   │   │   ├── service/
│   │   │           │   │   │   │   ├── CustomerService.java
│   │   │           │   │   │   │   ├── CustomerServiceImpl.java
│   │   │           │   │   │   │   └── CustomerValidator.java
│   │   │           │   │   │   ├── controller/
│   │   │           │   │   │   │   ├── CustomerController.java
│   │   │           │   │   │   │   └── CustomerQueryController.java (read-only queries)
│   │   │           │   │   │   └── dto/
│   │   │           │   │   │       ├── CustomerCreateDto.java
│   │   │           │   │   │       ├── CustomerUpdateDto.java
│   │   │           │   │   │       ├── CustomerResponseDto.java
│   │   │           │   │   │       └── CustomerSearchDto.java
│   │   │           │   │   ├── producer/
│   │   │           │   │   │   ├── entity/
│   │   │           │   │   │   │   └── Producer.java
│   │   │           │   │   │   ├── repository/
│   │   │           │   │   │   │   └── ProducerRepository.java
│   │   │           │   │   │   ├── service/
│   │   │           │   │   │   │   ├── ProducerService.java
│   │   │           │   │   │   │   └── ProducerServiceImpl.java
│   │   │           │   │   │   ├── controller/
│   │   │           │   │   │   │   └── ProducerController.java
│   │   │           │   │   │   └── dto/
│   │   │           │   │   │       ├── ProducerCreateDto.java
│   │   │           │   │   │       ├── ProducerUpdateDto.java
│   │   │           │   │   │       └── ProducerResponseDto.java
│   │   │           │   │   └── shop/
│   │   │           │   │       ├── entity/
│   │   │           │   │       │   └── Shop.java
│   │   │           │   │       ├── repository/
│   │   │           │   │       │   └── ShopRepository.java
│   │   │           │   │       ├── service/
│   │   │           │   │       │   ├── ShopService.java
│   │   │           │   │       │   └── ShopServiceImpl.java
│   │   │           │   │       ├── controller/
│   │   │           │   │       │   └── ShopController.java
│   │   │           │   │       └── dto/
│   │   │           │   │           ├── ShopCreateDto.java
│   │   │           │   │           ├── ShopUpdateDto.java
│   │   │           │   │           └── ShopResponseDto.java
│   │   │           │   │
│   │   │           │   ├── catalog/                        # PHASE 3: Catalog (Product aggregate)
│   │   │           │   │   ├── product/
│   │   │           │   │   │   ├── entity/
│   │   │           │   │   │   │   ├── Product.java
│   │   │           │   │   │   │   └── GuaranteeType.java (enum)
│   │   │           │   │   │   ├── repository/
│   │   │           │   │   │   │   ├── ProductRepository.java
│   │   │           │   │   │   │   └── ProductQueryRepository.java
│   │   │           │   │   │   ├── service/
│   │   │           │   │   │   │   ├── ProductService.java
│   │   │           │   │   │   │   ├── ProductServiceImpl.java
│   │   │           │   │   │   │   └── ProductValidator.java
│   │   │           │   │   │   ├── controller/
│   │   │           │   │   │   │   ├── ProductController.java
│   │   │           │   │   │   │   └── ProductQueryController.java
│   │   │           │   │   │   └── dto/
│   │   │           │   │   │       ├── ProductCreateDto.java
│   │   │           │   │   │       ├── ProductUpdateDto.java
│   │   │           │   │   │       ├── ProductResponseDto.java
│   │   │           │   │   │       ├── ProductFilterDto.java
│   │   │           │   │   │       └── ProductDetailDto.java
│   │   │           │   │   │
│   │   │           │   │   └── inventory/               # PHASE 4: Inventory (Stock)
│   │   │           │   │       ├── entity/
│   │   │           │   │       │   └── Stock.java
│   │   │           │   │       ├── repository/
│   │   │           │   │       │   ├── StockRepository.java
│   │   │           │   │       │   └── StockQueryRepository.java
│   │   │           │   │       ├── service/
│   │   │           │   │       │   ├── StockService.java
│   │   │           │   │       │   ├── StockServiceImpl.java
│   │   │           │   │       │   └── InventoryRuleValidator.java
│   │   │           │   │       ├── controller/
│   │   │           │   │       │   ├── StockController.java
│   │   │           │   │       │   └── InventoryController.java
│   │   │           │   │       └── dto/
│   │   │           │   │           ├── StockCreateDto.java
│   │   │           │   │           ├── StockUpdateDto.java
│   │   │           │   │           ├── StockResponseDto.java
│   │   │           │   │           └── InventoryReportDto.java
│   │   │           │   │
│   │   │           │   ├── order/                         # PHASE 5: Orders (transactions)
│   │   │           │   │   ├── entity/
│   │   │           │   │   │   └── Order.java             # Renamed from MyOrder
│   │   │           │   │   ├── repository/
│   │   │           │   │   │   ├── OrderRepository.java
│   │   │           │   │   │   └── OrderQueryRepository.java
│   │   │           │   │   ├── service/
│   │   │           │   │   │   ├── OrderService.java
│   │   │           │   │   │   ├── OrderServiceImpl.java
│   │   │           │   │   │   ├── OrderValidator.java
│   │   │           │   │   │   ├── OrderNumberGenerator.java
│   │   │           │   │   │   └── OrderWorkflow.java    # Orchestration
│   │   │           │   │   ├── event/                    # Domain events (optional, future)
│   │   │           │   │   │   ├── OrderCreatedEvent.java
│   │   │           │   │   │   ├── OrderConfirmedEvent.java
│   │   │           │   │   │   └── OrderEventPublisher.java
│   │   │           │   │   ├── controller/
│   │   │           │   │   │   ├── OrderController.java
│   │   │           │   │   │   └── OrderQueryController.java
│   │   │           │   │   └── dto/
│   │   │           │   │       ├── OrderCreateDto.java
│   │   │           │   │       ├── OrderUpdateDto.java
│   │   │           │   │       ├── OrderResponseDto.java
│   │   │           │   │       ├── OrderDetailDto.java
│   │   │           │   │       └── OrderSearchDto.java
│   │   │           │   │
│   │   │           │   └── reporting/                      # PHASE 6: Reporting & Analytics
│   │   │           │       ├── entity/                     # Read models / projections
│   │   │           │       │   └── ProductByCountryView.java
│   │   │           │       ├── repository/
│   │   │           │       │   ├── ReportingRepository.java (custom queries for read models)
│   │   │           │       │   └── AnalyticsRepository.java
│   │   │           │       ├── service/
│   │   │           │       │   ├── ReportingService.java
│   │   │           │       │   ├── ReportingServiceImpl.java
│   │   │           │       │   ├── AnalyticsService.java
│   │   │           │       │   └── AnalyticsServiceImpl.java
│   │   │           │       ├── controller/
│   │   │           │       │   ├── ReportController.java
│   │   │           │       │   └── AnalyticsController.java
│   │   │           │       └── dto/
│   │   │           │           ├── ProductsByCountryReportDto.java
│   │   │           │           ├── SalesAnalyticsDto.java
│   │   │           │           ├── InventoryReportDto.java
│   │   │           │           └── CustomerSegmentationDto.java
│   │   │           │
│   │   │           └── infra/                              # Infrastructure & persistence
│   │   │               ├── persistence/
│   │   │               │   ├── converter/                 # JPA attribute converters
│   │   │               │   │   └── GuaranteeTypeConverter.java
│   │   │               │   ├── listener/                  # JPA entity listeners
│   │   │               │   │   └── AuditingEntityListener.java
│   │   │               │   └── specification/             # Spring Data Specifications
│   │   │               │       ├── EntitySpecification.java
│   │   │               │       ├── CustomerSpecification.java
│   │   │               │       ├── ProductSpecification.java
│   │   │               │       └── OrderSpecification.java
│   │   │               └── audit/
│   │   │                   └── AuditableEntity.java        # Base entity with audit fields
│   │   │
│   │   ├── resources/
│   │   │   ├── application.yml                           # Main application config
│   │   │   ├── application-dev.yml                       # Development profile
│   │   │   ├── application-prod.yml                      # Production profile
│   │   │   ├── application-test.yml                      # Test profile
│   │   │   │
│   │   │   ├── META-INF/
│   │   │   │   ├── persistence.xml                       # Optional: JPA config overrides
│   │   │   │   └── spring.properties                     # Optional: Spring-specific properties
│   │   │   │
│   │   │   ├── db/
│   │   │   │   └── migration/                            # Flyway migrations
│   │   │   │       ├── V1__Initial_Schema_Reference.sql  # Phase 1: Country, Category, Trade, Payment
│   │   │   │       ├── V2__Create_Master_Entities.sql    # Phase 2: Customer, Producer, Shop
│   │   │   │       ├── V3__Create_Product_Catalog.sql    # Phase 3: Product, guarantees
│   │   │   │       ├── V4__Create_Inventory_Stock.sql    # Phase 4: Stock
│   │   │   │       ├── V5__Create_Order_Transactions.sql # Phase 5: Order
│   │   │   │       ├── V6__Create_Reporting_Views.sql    # Phase 6: Read models
│   │   │   │       └── V7__Add_Indexes_And_Constraints.sql # Phase 7: Optimization
│   │   │   │
│   │   │   └── i18n/                                      # Internationalization
│   │   │       ├── messages.properties
│   │   │       ├── messages_en.properties
│   │   │       └── messages_de.properties
│   │   │
│   │   └── asciidocs/                                    # AsciiDoc for OpenAPI/Swagger
│   │       └── index.adoc
│   │
│   ├── test/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── storemanagement/
│   │   │           ├── unit/                             # Unit tests (isolated)
│   │   │           │   ├── domain/
│   │   │           │   │   ├── reference/
│   │   │           │   │   │   ├── service/
│   │   │           │   │   │   │   └── CountryServiceTest.java
│   │   │           │   │   │   │   └── CategoryServiceTest.java
│   │   │           │   │   │   └── ...
│   │   │           │   │   ├── master/
│   │   │           │   │   │   ├── service/
│   │   │           │   │   │   │   └── CustomerServiceTest.java
│   │   │           │   │   │   │   └── ProducerServiceTest.java
│   │   │           │   │   │   └── ...
│   │   │           │   │   ├── catalog/
│   │   │           │   │   │   ├── service/
│   │   │           │   │   │   │   └── ProductServiceTest.java
│   │   │           │   │   │   │   └── StockServiceTest.java
│   │   │           │   │   │   └── ...
│   │   │           │   │   ├── order/
│   │   │           │   │   │   ├── service/
│   │   │           │   │   │   │   └── OrderServiceTest.java
│   │   │           │   │   │   │   └── OrderValidatorTest.java
│   │   │           │   │   │   └── ...
│   │   │           │   │   └── reporting/
│   │   │           │   │       ├── service/
│   │   │           │   │       │   └── ReportingServiceTest.java
│   │   │           │   │       └── ...
│   │   │           │   │
│   │   │           │   ├── core/
│   │   │           │   │   ├── exception/
│   │   │           │   │   │   └── GlobalExceptionHandlerTest.java
│   │   │           │   │   ├── validation/
│   │   │           │   │   │   ├── EmailValidatorTest.java
│   │   │           │   │   │   └── UniqueEmailValidatorTest.java
│   │   │           │   │   └── utils/
│   │   │           │   │       ├── DateTimeUtilTest.java
│   │   │           │   │       └── NumberFormatUtilTest.java
│   │   │           │   │
│   │   │           │   └── infra/
│   │   │           │       ├── persistence/
│   │   │           │       │   └── SpecificationTest.java
│   │   │           │       └── audit/
│   │   │           │           └── AuditableEntityTest.java
│   │   │           │
│   │   │           ├── integration/                       # Integration tests (with DB, context loading)
│   │   │           │   ├── config/
│   │   │           │   │   ├── TestDatabaseConfig.java
│   │   │           │   │   ├── TestContainersConfig.java  # Testcontainers for PostgreSQL
│   │   │           │   │   └── IntegrationTestBase.java   # Base class with context loading
│   │   │           │   │
│   │   │           │   ├── domain/
│   │   │           │   │   ├── reference/
│   │   │           │   │   │   ├── repository/
│   │   │           │   │   │   │   └── CountryRepositoryIT.java
│   │   │           │   │   │   └── service/
│   │   │           │   │   │       └── CountryServiceIT.java
│   │   │           │   │   ├── master/
│   │   │           │   │   │   ├── repository/
│   │   │           │   │   │   │   └── CustomerRepositoryIT.java
│   │   │           │   │   │   └── service/
│   │   │           │   │   │       └── CustomerServiceIT.java
│   │   │           │   │   ├── catalog/
│   │   │           │   │   │   ├── repository/
│   │   │           │   │   │   │   └── ProductRepositoryIT.java
│   │   │           │   │   │   └── service/
│   │   │           │   │   │       └── ProductServiceIT.java
│   │   │           │   │   ├── order/
│   │   │           │   │   │   ├── repository/
│   │   │           │   │   │   │   └── OrderRepositoryIT.java
│   │   │           │   │   │   └── service/
│   │   │           │   │   │       └── OrderServiceIT.java
│   │   │           │   │   └── reporting/
│   │   │           │   │       └── service/
│   │   │           │   │           └── ReportingServiceIT.java
│   │   │           │   │
│   │   │           │   └── infra/
│   │   │           │       └── persistence/
│   │   │           │           └── SpecificationIT.java
│   │   │           │
│   │   │           ├── contract/                         # Contract tests (REST API contracts)
│   │   │           │   ├── reference/
│   │   │           │   │   ├── CountryControllerContractTest.java
│   │   │           │   │   └── CategoryControllerContractTest.java
│   │   │           │   ├── master/
│   │   │           │   │   ├── CustomerControllerContractTest.java
│   │   │           │   │   └── ProducerControllerContractTest.java
│   │   │           │   ├── catalog/
│   │   │           │   │   ├── ProductControllerContractTest.java
│   │   │           │   │   └── StockControllerContractTest.java
│   │   │           │   ├── order/
│   │   │           │   │   └── OrderControllerContractTest.java
│   │   │           │   └── reporting/
│   │   │           │       └── ReportControllerContractTest.java
│   │   │           │
│   │   │           ├── e2e/                              # End-to-end tests (full workflow)
│   │   │           │   ├── config/
│   │   │           │   │   ├── E2ETestConfig.java
│   │   │           │   │   └── E2ETestBase.java
│   │   │           │   ├── scenarios/
│   │   │           │   │   ├── ReferenceDataSetupScenarioTest.java
│   │   │           │   │   ├── CustomerCreationAndValidationE2ETest.java
│   │   │           │   │   ├── ProductCatalogSetupE2ETest.java
│   │   │           │   │   ├── StockManagementE2ETest.java
│   │   │           │   │   ├── OrderFlowE2ETest.java
│   │   │           │   │   └── CompleteOrderFulfillmentE2ETest.java
│   │   │           │
│   │   │           └── fixtures/                         # Test data / fixtures
│   │   │               ├── EntityFixture.java
│   │   │               ├── CountryFixture.java
│   │   │               ├── CustomerFixture.java
│   │   │               ├── ProductFixture.java
│   │   │               ├── OrderFixture.java
│   │   │               └── DtoFixture.java
│   │   │
│   │   └── resources/
│   │       ├── application-test.yml
│   │       ├── test-data.sql                            # Initial test data
│   │       │
│   │       └── testcontainers/
│   │           ├── docker-compose.yml                   # Optional: for complex test infra
│   │           └── README.md
│   │
│   └── it/                                              # Optional: separate integration test module
│       ├── java/
│       │   └── com/storemanagement/it/
│       │       ├── OrderFullCycleIT.java
│       │       ├── InventoryManagementIT.java
│       │       └── ReportGenerationIT.java
│       │
│       ├── resources/
│       │   └── application-it.yml
│       │
│       └── pom.xml                                      # Optional: separate IT pom

```

---

## 3) Root Package: `com.storemanagement`

**Rationale**:
- Aligns with modern Java package conventions (reverse domain)
- Removes legacy `jan.stefan.hibernate` naming
- `-` in artifact ID automatically becomes `_` or clean separation in packages
- Groups all application code under a consistent business domain
- Facilitates future multi-module structure (e.g., `com.storemanagement.api`, `com.storemanagement.admin`)

---

## 4) Package Hierarchy Overview

### Top-Level Packages

| Package | Purpose | Modules |
|---------|---------|---------|
| `com.storemanagement` | **Root** | — |
| `com.storemanagement.config` | Application & bean configuration | JPA, validation, security, caching, OpenAPI |
| `com.storemanagement.core` | Cross-cutting concerns | Exceptions, logging, validation, utils, DTOs |
| `com.storemanagement.domain` | Business logic (vertical slices / bounded contexts) | reference, master, catalog, order, reporting |
| `com.storemanagement.infra` | Technical infrastructure | Persistence, converters, listeners, audit |

---

## 5) Detailed Package Layouts

### 5.1 Config Package: `com.storemanagement.config`

**Purpose**: Centralized Spring configuration, Bean definitions, and cross-cutting configuration.

**Contents**:

```
com.storemanagement.config
├── JpaConfig.java                  # Spring Data JPA, Hibernate properties, auditing
├── ValidationConfig.java           # Bean Validation (JSR-303/Jakarta), message source
├── OpenApiConfig.java              # Springdoc OpenAPI (Swagger 3.0) configuration
├── WebConfig.java                  # Web MVC configuration (CORS, formatters, converters)
├── SecurityConfig.java             # Spring Security (if/when needed)
├── CachingConfig.java              # Spring Cache abstraction
└── AsyncConfig.java                # Async execution pool
```

**Rationale**:
- Separates framework configuration from domain logic
- Facilitates environment-specific overrides (profiles)
- Centralizes bean definitions for visibility
- Supports lazy loading and conditional configuration

### 5.2 Core Package: `com.storemanagement.core`

**Purpose**: Shared, cross-domain concerns (not specific to any business domain).

**Sub-packages**:

#### 5.2.1 Exception Handling: `core.exception`

```
com.storemanagement.core.exception
├── GlobalExceptionHandler.java       # @RestControllerAdvice for all REST exceptions
├── ApiException.java                 # Base exception for API errors
├── EntityNotFoundException.java
├── BusinessRuleException.java
├── ValidationException.java
├── ConflictException.java            # 409 Conflict (e.g., duplicate email)
├── InvalidStateException.java
├── ErrorResponse.java                # DTO for error responses
└── ErrorCode.java                    # Error code enumeration
```

**Design**:
- Extends unchecked exceptions (`RuntimeException`)
- Includes HTTP status code mapping
- Supports i18n error messages
- Global handler translates to standard error response format

#### 5.2.2 Logging: `core.logging`

```
com.storemanagement.core.logging
├── LoggingAspect.java               # AOP aspect for service method logging
├── LoggingFilter.java               # Optional: Servlet filter for HTTP logging
└── PerformanceMonitor.java          # Execution time tracking
```

#### 5.2.3 Validation: `core.validation`

```
com.storemanagement.core.validation
├── validators/
│   ├── EmailValidator.java           # Custom @Email validator
│   ├── UniqueEmailValidator.java     # Cross-DB @UniqueEmail constraint
│   ├── UniqueNameValidator.java      # Generic uniqueness validator
│   └── CurrencyValidator.java        # For price fields
└── ValidationMessagesConfig.java     # i18n message configuration
```

**Design**:
- Uses Jakarta Bean Validation (JSR-380)
- Custom validators for business rules (e.g., unique email)
- Centralized message templates (i18n-ready)

#### 5.2.4 Utils: `core.utils`

```
com.storemanagement.core.utils
├── JsonUtil.java                    # JSON serialization/deserialization
├── DateTimeUtil.java                # LocalDateTime, formatting utilities
├── NumberFormatUtil.java            # Price, BigDecimal formatting
└── SlugUtil.java                    # URL-friendly identifiers
```

#### 5.2.5 Shared DTOs: `core.dto`

```
com.storemanagement.core.dto
├── ErrorResponseDto.java
├── ApiResponseDto.java              # Generic wrapper: { status, data, errors }
├── PaginationDto.java               # { page, size, totalElements, totalPages }
└── AuditableDto.java                # Shared audit fields (createdAt, updatedAt, etc.)
```

### 5.3 Domain Package: `com.storemanagement.domain`

**Purpose**: Business logic, organized as **vertical slices / bounded contexts**.

**High-Level Structure**:

```
com.storemanagement.domain
├── reference/                       # Phase 1: Reference entities (independent, low-coupling)
│   ├── country/
│   ├── category/
│   ├── trade/
│   └── payment/
├── master/                          # Phase 2: Master data (depend on reference)
│   ├── customer/
│   ├── producer/
│   └── shop/
├── catalog/                          # Phase 3+: Product & Inventory
│   ├── product/
│   └── inventory/
├── order/                            # Phase 5: Transactional orders
└── reporting/                        # Phase 6: Analytics & read models
```

**Within Each Vertical Slice** (e.g., `domain.reference.country`):

```
com.storemanagement.domain.reference.country
├── entity/
│   └── Country.java                 # @Entity with JPA annotations
├── repository/
│   └── CountryRepository.java       # Spring Data JPA interface
├── service/
│   ├── CountryService.java          # Service interface
│   └── CountryServiceImpl.java       # @Service implementation
├── controller/
│   └── CountryController.java       # @RestController with REST endpoints
│       # GET/POST/PUT/DELETE /api/v1/countries
├── dto/
│   ├── CountryCreateDto.java        # @Valid request DTO
│   ├── CountryUpdateDto.java        # @Valid request DTO
│   ├── CountryResponseDto.java      # Response DTO
│   └── CountrySearchDto.java        # Search/filter criteria
└── validator/                        # Optional: domain-specific validators
    └── CountryValidator.java
```

**Rationale for Vertical Slicing**:
1. **Minimize cognitive load**: Each slice is independent, with clear boundaries
2. **Enable parallel development**: Teams can work on `customer` while others work on `product`
3. **Facilitate phased rollout**: Phase 1 (`reference`) can be deployed independently
4. **Support testing**: Each slice can be unit-tested in isolation and integration-tested within phase
5. **Reduce merge conflicts**: Clear package ownership

### 5.4 Infrastructure Package: `com.storemanagement.infra`

**Purpose**: Technical persistence, database mapping, query specifications.

**Sub-packages**:

#### 5.4.1 Persistence: `infra.persistence`

```
com.storemanagement.infra.persistence
├── converter/
│   ├── GuaranteeTypeConverter.java   # @Converter for Product.guaranteeComponents
│   ├── PaymentTypeConverter.java
│   └── ...
├── listener/
│   ├── AuditingEntityListener.java   # @EntityListeners for audit timestamps
│   └── ...
└── specification/
    ├── EntitySpecification.java       # Abstract Criteria builder
    ├── CustomerSpecification.java     # Spring Data Specification for Customer filters
    ├── ProductSpecification.java
    └── OrderSpecification.java
```

**Rationale**:
- JPA attribute converters (enums, custom types)
- Entity listeners for automatic audit timestamps
- Reusable query specifications for complex filters

#### 5.4.2 Audit: `infra.audit`

```
com.storemanagement.infra.audit
├── AuditableEntity.java             # Abstract base with createdAt, updatedAt, createdBy
├── AuditableEntityListener.java      # Auto-populate audit fields
└── AuditingConfig.java              # Spring Data Auditing configuration
```

---

## 6) Entity Package Layout: `domain.{domain}.entity`

**Structure** (per vertical slice):

```
entity/
├── Country.java
├── Category.java
├── Product.java
├── GuaranteeType.java              # Enum mapped as @ElementCollection
└── ...
```

**Design Principles**:

1. **@Entity** with table naming strategy (snake_case)
2. **@Id @GeneratedValue(strategy = GenerationType.IDENTITY)** for auto-increment
3. **@ManyToOne(fetch = FetchType.LAZY)** with `@JoinColumn(nullable = false)`
4. **@OneToMany(mappedBy = ...)** reverse with `cascade = CascadeType.PERSIST, orphanRemoval = true`
5. **@ElementCollection** for enums (e.g., `Product.guaranteeComponents`)
6. **Lombok**: `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@Builder`
7. **Validation**: `@NotNull`, `@NotBlank`, `@UniqueEmail` at entity level for database constraints
8. **Inheritance**: Consider `@MappedSuperclass` for shared audit fields

**Example Structure** (not code):
- PK: `id` (Long, auto-increment)
- FK columns: `country_id`, `category_id`, etc. (non-null for required refs)
- Business key: unique constraint on `(name, country_id)` for multi-tenant uniqueness
- Audit fields: `created_at`, `updated_at`, `created_by`, `updated_by` (from AuditableEntity base class)
- Enum fields: `@Enumerated(EnumType.STRING)` or `@Converter` for custom enums
- Temporal: `@CreationTimestamp`, `@UpdateTimestamp` via Hibernate

---

## 7) DTO Package Layout: `domain.{domain}.dto`

**Naming Convention**:

| DTO Type | Naming | Purpose |
|----------|--------|---------|
| Create Request | `{Entity}CreateDto` | POST body (`@Valid @RequestBody`) |
| Update Request | `{Entity}UpdateDto` | PUT body (`@Valid @RequestBody`) |
| Response | `{Entity}ResponseDto` | GET response, wraps entity |
| Search Criteria | `{Entity}SearchDto`, `{Entity}FilterDto` | Query parameters (e.g., `/products?category=X&price=Y`) |
| Detail View | `{Entity}DetailDto` | Rich response with nested aggregates |
| Report/Projection | `{Report}Dto` | Reporting domain (flattened, denormalized) |

**Validation Annotations**:
- Request DTOs: `@NotNull`, `@NotBlank`, `@Email`, `@UniqueEmail`, `@Min`, `@Max`, `@Pattern`
- Nested: `@Valid` on composed objects
- Custom: `@UniqueEmail(message = "Email already exists")`

**Principle**:
- DTOs are **input/output contracts** for REST API
- Separate from entities to allow independent evolution
- Request DTOs validate user input; responses shape the JSON
- Nested DTOs represent related data without full entity graphs

---

## 8) Repository Package Layout: `domain.{domain}.repository`

**Structure**:

```
repository/
├── CountryRepository.java           # Spring Data JPA interface
├── CountryQueryRepository.java      # Optional: custom @Repository for complex queries
└── ...
```

**Design**:

```
interface CountryRepository extends JpaRepository<Country, Long>, JpaSpecificationExecutor<Country> {
    // Derived query methods
    Optional<Country> findByName(String name);
    
    // Custom query methods
    List<Country> findByNameContainingIgnoreCase(String name);
}

@Repository
class CountryQueryRepository {
    // Complex queries using Criteria API or JPQL
    // Larger result sets, aggregations, reporting queries
}
```

**Rationale**:
- `JpaRepository` for CRUD + pagination
- `JpaSpecificationExecutor` for dynamic filtering (via Specification classes)
- Separate custom repository for complex queries (keeps interface clean)
- Query methods use derived naming (#findByNameContaining)

---

## 9) Service Package Layout: `domain.{domain}.service`

**Structure**:

```
service/
├── CountryService.java              # Service interface (abstraction)
├── CountryServiceImpl.java           # @Service implementation
├── CountryValidator.java            # Business rule validation
└── CountryNumberGenerator.java      # Utility for ID generation (if needed)
```

**Design Pattern**:

1. **Service Interface**: Defines business operations
   ```
   interface CountryService {
       CountryResponseDto create(CountryCreateDto dto);
       CountryResponseDto findById(Long id);
       ...
   }
   ```

2. **Service Implementation**: `@Service` stereotype
   ```
   @Service
   @RequiredArgsConstructor
   class CountryServiceImpl implements CountryService {
       private final CountryRepository repo;
       private final ModelMapper mapper;
       
       public CountryResponseDto create(CountryCreateDto dto) { ... }
   }
   ```

3. **Business Logic**:
   - Validation (call validator)
   - Transaction boundaries (@Transactional)
   - Cross-domain calls (e.g., if Service A needs Service B, inject via constructor)
   - Exception translation (catch DB exceptions, throw domain exceptions)

4. **Validators**: Separate classes for testability
   ```
   @Component
   class CountryValidator {
       boolean isValid(CountryCreateDto dto) { ... }
   }
   ```

---

## 10) REST Controller Package Layout: `domain.{domain}.controller`

**Structure**:

```
controller/
├── CountryController.java           # CRUD operations (@RestController)
└── CountryQueryController.java      # Read-only, complex queries (optional)
```

**Design**:

```
@RestController
@RequestMapping("/api/v1/countries")
@RequiredArgsConstructor
class CountryController {
    private final CountryService service;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CountryResponseDto create(@Valid @RequestBody CountryCreateDto dto) { ... }
    
    @GetMapping("/{id}")
    CountryResponseDto findById(@PathVariable Long id) { ... }
    
    @PutMapping("/{id}")
    CountryResponseDto update(@PathVariable Long id, @Valid @RequestBody CountryUpdateDto dto) { ... }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) { ... }
}
```

**Conventions**:
- **Base path**: `/api/v1/{resource}` (versioned)
- **Resource naming**: plural, kebab-case (e.g., `/api/v1/products`)
- **HTTP Methods**: 
  - `POST /resource` → create (201 Created)
  - `GET /resource/{id}` → retrieve
  - `PUT /resource/{id}` → update (200 OK)
  - `DELETE /resource/{id}` → delete (204 No Content)
  - `GET /resource` → list with pagination/filtering
- **Status Codes**: 200, 201, 204, 400, 404, 409, 500
- **Collection parameters**: `?page=0&size=20&sort=name,asc`

**OpenAPI Annotations**:
```
@Operation(summary = "Create a country")
@ApiResponse(responseCode = "201", description = "Country created")
@ApiResponse(responseCode = "400", description = "Invalid input")
```

---

## 11) Exception Handling Structure

**Hierarchy**:

```
Throwable
└── Exception
    └── RuntimeException
        ├── ApiException (base for all API errors)
        │   ├── EntityNotFoundException (404)
        │   ├── ValidationException (400)
        │   ├── ConflictException (409)
        │   ├── BusinessRuleException (422)
        │   └── InvalidStateException (400/422)
        └── [Other technical exceptions]
```

**Handler Strategy**:

```
@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handle(EntityNotFoundException e) { ... }
    
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handle(ValidationException e) { ... }
    
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorResponse handle(ConflictException e) { ... }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handle(MethodArgumentNotValidException e) { ... }
}
```

**Error Response Format**:

```json
{
  "timestamp": "2026-06-10T12:34:56Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/v1/countries",
  "errors": [
    {
      "field": "name",
      "message": "must not be blank"
    }
  ]
}
```

---

## 12) Configuration Package Structure

### 12.1 JPA Configuration: `config.JpaConfig`

**Purpose**: Spring Data JPA, Hibernate, auditing.

**Key Beans**:
- `LocalContainerEntityManagerFactoryBean` (if not using Spring Boot defaults)
- `PlatformTransactionManager`
- `TransactionTemplate`
- `@EnableJpaAuditing` with `AuditorAware` for `createdBy`, `updatedBy`
- `@EnableMavenAuditingProperties` or manual mapping

### 12.2 Validation Configuration: `config.ValidationConfig`

**Purpose**: Bean Validation, custom validators, message sources.

**Key Beans**:
- `LocalValidatorFactoryBean`
- `MethodValidationPostProcessor` (for method-level validation with `@Validated` + `@Valid`)
- `MessageSource` for i18n error messages

### 12.3 OpenAPI Configuration: `config.OpenApiConfig`

**Purpose**: Springdoc OpenAPI (Swagger 3.0), API documentation.

**Key Beans**:
- `OpenAPI` with title, description, version, contact
- `GroupedOpenApi` for API grouping (e.g., "Reference Data", "Orders")
- `SpringDocTokenRefreshSecurityScheme` (if JWT needed)

**Example**:
```
@Bean
OpenAPI customOpenApi() {
    return new OpenAPI()
        .info(new Info()
            .title("Store Management System API")
            .version("1.0.0")
            ...);
}
```

---

## 13) Flyway Migration Structure

**Location**: `src/main/resources/db/migration/`

**Naming Convention**: `V{VERSION}__{DESCRIPTION}.sql`

**Phase-Based Versioning**:

| File | Phase | Content |
|------|-------|---------|
| `V1__Initial_Schema_Reference.sql` | 1 | Country, Category, Trade, Payment tables + constraints |
| `V2__Create_Master_Entities.sql` | 2 | Customer, Producer, Shop tables + FKs to phase 1 |
| `V3__Create_Product_Catalog.sql` | 3 | Product table + element collection table for guarantees |
| `V4__Create_Inventory_Stock.sql` | 4 | Stock table + FK to Product, Shop |
| `V5__Create_Order_Transactions.sql` | 5 | Order table + FK to Customer, Product, Payment |
| `V6__Create_Reporting_Views.sql` | 6 | Read model views/materialized views for reports |
| `V7__Add_Indexes_And_Constraints.sql` | 7 | Optimize with indexes, business logic constraints |

**Key Design Decisions**:

1. **Idempotency**: Use `IF NOT EXISTS` to make migration reversible
2. **Foreign Keys**: Added post-table creation (better control + avoid circular deps)
3. **Null Constraints**: `NOT NULL` where entities require dependencies
4. **Unique Constraints**: Natural keys (e.g., `UNIQUE(name, country_id)`)
5. **Indexes**: On FK columns and frequently filtered fields
6. **Audit Fields**: `created_at`, `updated_at`, `created_by`, `updated_by` (timestamps with defaults)

**Example SQL Structure** (not actual code):
```
V1: 
  - CREATE TABLE country
  - CREATE TABLE category
  - CREATE TABLE trade
  - CREATE TABLE payment

V2:
  - CREATE TABLE customer (country_id FK)
  - ALTER TABLE customer ADD CONSTRAINT fk_country
  
V3:
  - CREATE TABLE product (category_id, producer_id, trade_id FKs)
  - CREATE TABLE product_guarantee (product_id, guarantee_type)

V5:
  - CREATE TABLE order (customer_id, product_id, payment_id FKs)
```

**Configuration** (application.yml):
```yaml
spring:
  flyway:
    enabled: true
    locations: classpath:db/migration
    baselineVersion: 0
    validateOnMigrate: true
```

---

## 14) Testing Structure

### 14.1 Unit Tests: `test.unit.{domain}.{layer}`

**Scope**: Single class, mocked dependencies.

**Example**: `test/java/com/storemanagement/unit/domain/reference/service/CountryServiceTest.java`

```
class CountryServiceTest {
    @Mock CountryRepository repo;
    @InjectMocks CountryServiceImpl service;
    
    @Test
    void createCountry_ShouldReturnResponse() { ... }
}
```

**Framework**: JUnit 5, Mockito

### 14.2 Integration Tests: `test.integration.{domain}.{layer}`

**Scope**: Service + Repository with real database (H2 or Testcontainers).

**Example**: `test/java/com/storemanagement/integration/domain/reference/service/CountryServiceIT.java`

```
@SpringBootTest
@Transactional
class CountryServiceIT extends IntegrationTestBase {
    @Autowired CountryService service;
    @Autowired CountryRepository repo;
    
    @Test
    void createCountry_ShouldPersistAndRetrieve() { ... }
}
```

**Infrastructure**:
- `TestContainersConfig.java`: PostgreSQL container via Testcontainers
- `IntegrationTestBase.java`: Common context, transaction handling, DB cleanup
- `application-test.yml`: Test database URL, Flyway, Hibernate properties

### 14.3 Contract Tests: `test.contract.{domain}`

**Scope**: REST endpoint contracts, request/response shapes.

**Example**: `test/java/com/storemanagement/contract/reference/CountryControllerContractTest.java`

```
class CountryControllerContractTest {
    @WebMvcTest(CountryController.class)
    void createCountry_ShouldReturnCreatedResponseWithValidBody() { ... }
}
```

**Framework**: MockMvc, AssertJ

### 14.4 End-to-End Tests: `test.e2e.scenarios`

**Scope**: Multi-step user workflows across domains.

**Example**: `test/java/com/storemanagement/e2e/scenarios/OrderFlowE2ETest.java`

```
@SpringBootTest
@Transactional
class OrderFlowE2ETest extends E2ETestBase {
    // Setup countries, customers, products in before()
    // Create order, verify stock deduction, confirm order
    
    @Test
    void completeOrderFlow_ShouldUpdateInventoryAndCreateAuditTrail() { ... }
}
```

### 14.5 Test Fixtures: `test.fixtures`

**Purpose**: Reusable test data builders.

```
class CountryFixture {
    static Country country() {
        return Country.builder().name("USA").build();
    }
}

class OrderFixture {
    static OrderCreateDto validOrderCreateDto() {
        return OrderCreateDto.builder().customerId(...).productId(...).build();
    }
}
```

### 14.6 Test Database Configuration

**`TestContainersConfig.java`**:
```
@TestConfiguration
class TestDatabaseConfig {
    @Bean(destroyMethod = "stop")
    PostgreSQLContainer<?> postgres() {
        return new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("store_management_test")
            .withUsername("test")
            .withPassword("test");
    }
}
```

**`application-test.yml`**:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/store_management_test
    username: test
    password: test
  jpa:
    hibernate:
      ddl-auto: validate  # Or create for test
    show-sql: false
    properties:
      hibernate:
        format_sql: true
  flyway:
    enabled: true
```

---

## 15) Application Configuration Files

**Location**: `src/main/resources/`

### 15.1 `application.yml` (Main)

```yaml
spring:
  application:
    name: store-management-system
  
  datasource:
    url: jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:store_management}
    username: ${DB_USER:postgres}
    password: ${DB_PASSWORD:postgres}
    hikari:
      maximum-pool-size: 10
  
  jpa:
    hibernate:
      ddl-auto: validate  # Flyway manages schema
    show-sql: false
    properties:
      hibernate:
        format_sql: false
        dialect: org.hibernate.dialect.PostgreSQL10Dialect
        jdbc:
          batch_size: 20
        order_inserts: true
        order_updates: true
  
  flyway:
    enabled: true
    locations: classpath:db/migration
    baselineVersion: 0
    validateOnMigrate: false
  
  mvc:
    throw-exception-if-no-handler-found: true
  
  web:
    resources:
      add-mappings: false
  
  jackson:
    default-property-inclusion: non_null
    serialization:
      write-dates-as-timestamps: false
  
  messages:
    basename: i18n/messages
    encoding: UTF-8

server:
  port: 8080
  servlet:
    context-path: /

logging:
  level:
    root: INFO
    com.storemanagement: DEBUG
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"

management:
  endpoints:
    web:
      exposure:
        include: health, info, metrics
  endpoint:
    health:
      show-details: when-authorized
```

### 15.2 `application-dev.yml` (Development)

```yaml
spring:
  jpa:
    show-sql: true
    properties:
      hibernate:
        format_sql: true

logging:
  level:
    com.storemanagement: DEBUG
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql: TRACE
```

### 15.3 `application-prod.yml` (Production)

```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
  
  flyway:
    validateOnMigrate: true
    outOfOrder: false

logging:
  level:
    root: WARN
    com.storemanagement: INFO
```

---

## 16) Architectural Decisions & Rationale

### 16.1 Why Vertical Slicing (Bounded Contexts)?

**Benefits**:
- **Parallel Development**: Teams own vertical slices independently
- **Clear Ownership**: No ambiguity about who maintains what
- **Phased Rollout**: Phase 1 (reference) can deploy before Phase 5 (orders)
- **Reduced Cognitive Load**: Developers focus on one bounded context at a time
- **Easier Testing**: Each slice is testable in isolation
- **Microservices Ready**: Slices can become separate services later

**Downside**: Potential code duplication (mitigated by `core` package for shared concerns)

### 16.2 Why Separate Service Interfaces?

**Rationale**:
- Enables dependency inversion and loose coupling
- Facilitates mocking in unit tests
- Allows multiple implementations (e.g., `CountryServiceImpl`, `CountryServiceCacheImpl`)
- Aligns with SOLID (Dependency Inversion Principle)

### 16.3 Why Spring Data JPA over Manual Queries?

**Benefits**:
- **Derived queries**: Minimal boilerplate for common patterns
- **Pagination**: Built-in support for large result sets
- **Specifications**: Dynamic filtering without manual SQL
- **Auditing**: Spring Data integrates with entity listeners
- **Transaction Management**: Auto-handling via `@Transactional`

### 16.4 Why Flyway over Liquibase?

**Rationale**:
- Simpler (SQL-first vs XML/JSON)
- Better Git history (plain SQL files)
- Faster adoption (less learning curve)
- Excellent PostgreSQL support
- Lightweight and reliable

### 16.5 Why Separate DTOs from Entities?

**Rationale**:
- **API Contracts**: DTOs define REST payload schema independent of entity structure
- **Validation**: Request DTOs validate user input; entities validate DB constraints
- **Security**: Response DTOs can omit sensitive fields without entity changes
- **Evolution**: Entity and API can evolve separately without breaking clients
- **Circular References**: DTOs can flatten hierarchies (entity graphs often cyclic)

### 16.6 Why Multiple Repository Classes (Repository + QueryRepository)?

**Rationale**:
- **Spring Data Repositories**: Keep interface clean with derived methods
- **Custom Repositories**: Complex queries (aggregations, joins) keep domain service readable
- **Testability**: Easier to mock separate concerns
- **Performance**: Native queries isolated from ORM abstractions

### 16.7 Why Phase-Based Flyway Versioning?

**Rationale**:
- **Pause-and-Resume**: If Phase 2 encounters issues, system can run on Phase 1 alone
- **Clear History**: Each migration states its phase + purpose
- **Collaboration**: Teams know exactly when their entity gets created
- **Rollback Strategy**: Can roll back to start of phase if needed

### 16.8 Why Lombok?

**Benefits**:
- **Less Boilerplate**: No more `@Override getter`, `equals`, `hashCode`, `toString`
- **Readable Entities**: Focus on business logic, not Java ceremony
- **Field Injection Avoidance**: `@RequiredArgsConstructor` makes constructor injection declarative
- **Supported in IDEs**: Full IntelliJ/Eclipse integration available

### 16.9 Why OpenAPI (Springdoc)?

**Rationale**:
- **Auto-Documentation**: Generates OpenAPI 3.0 spec from Spring annotations
- **Swagger UI**: Built-in UI at `/swagger-ui.html`
- **Type Safety**: Inspects Spring annotations for endpoint contracts
- **Client Generation**: Auto-generate client SDKs from spec
- **No Separate Docs**: Spec lives in code, not separate Markdown

### 16.10 Why PostgreSQL over MySQL?

**Rationale**:
- **Advanced Types**: JSONB, arrays, enums (better for Product.guaranteeComponents)
- **Standards Compliance**: Better SQL standard adherence
- **Performance**: Superior indexing, query planner
- **JSON Support**: Native JSON columns (for flexible reporting)
- **Open Source**: No licensing concerns

---

## 17) Key Architectural Patterns Used

| Pattern | Where | Purpose |
|---------|-------|---------|
| **Repository** | `domain.{domain}.repository` | Abstract persistence, enable testing |
| **Service** | `domain.{domain}.service` | Encapsulate business logic, transaction boundaries |
| **DTO** | `domain.{domain}.dto` | Define API contracts, separate from entities |
| **Validator** | `domain.{domain}.validator` + `core.validation` | Isolate validation rules, reusability |
| **Specification** | `infra.persistence.specification` | Dynamic query building (Spring Data Criteria) |
| **Global Exception Handler** | `core.exception` | Centralized error response mapping |
| **REST Controller** | `domain.{domain}.controller` | HTTP endpoint mapping, request/response |
| **Configuration** | `config.*` | Bean definitions, environment setup |
| **Dependency Injection** | Throughout | Loose coupling via constructor injection |
| **Transactional Boundary** | Service layer | ACID guarantees per operation |
| **Logging Aspect** | `core.logging` | Cross-cutting logging without code noise |

---

## 18) Migration from Old Structure to New Structure

**Quick Mapping**:

| Old Package | New Package |
|-------------|-------------|
| `jan.stefan.hibernate.model.Country` | `com.storemanagement.domain.reference.country.entity.Country` |
| `jan.stefan.hibernate.repository.repositoryInterfaces.CountryRepository` | `com.storemanagement.domain.reference.country.repository.CountryRepository` |
| `jan.stefan.hibernate.service.CountryService` | `com.storemanagement.domain.reference.country.service.CountryService` (interface + impl) |
| `jan.stefan.hibernate.dto.CountryDto` | `com.storemanagement.domain.reference.country.dto.CountryResponseDto` |
| `jan.stefan.hibernate.exceptions.ApiException` | `com.storemanagement.core.exception.ApiException` |
| `jan.stefan.hibernate.menu.*` | *(CLI layer removed; replaced with REST controllers)* |
| `jan.stefan.hibernate.connection.*` | *(Spring Boot manages DB connections; Flyway+ JPA)* |

---

## 19) Next Steps for Implementation

1. **Create Maven POM Structure**
   - Update root `pom.xml` with Spring Boot 3.5 BOM
   - Add dependencies: Spring Data JPA, Flyway, Postgres driver, Lombok, Springdoc OpenAPI, Validation
   - Configure build plugins: compiler (Java 21), surefire (testing), jar

2. **Set Up Configuration Classes**
   - Implement `config/JpaConfig.java`, `config/ValidationConfig.java`, `config/OpenApiConfig.java`
   - Add `@EnableJpaAuditing`, `@EnableMavenAuditingProperties`

3. **Create Base Infra Classes**
   - `infra.audit.AuditableEntity` (base for all entities with audit fields)
   - `core.exception.GlobalExceptionHandler` (centralized error handling)
   - `core.dto.ApiResponseDto` (standard response wrapper)

4. **Implement Phase 1 (Reference Data)**
   - Entities: `Country`, `Category`, `Trade`, `Payment`
   - Repositories: Spring Data interfaces
   - Services: CRUD logic
   - Controllers: REST endpoints
   - DTOs: Create/Update/Response per entity
   - Flyway: V1 migration
   - Tests: Unit, integration, contract for each entity

5. **Build Out Remaining Phases**
   - Repeat structure for Phase 2 (`Customer`, `Shop`, `Producer`)
   - Then Phase 3+ (`Product`, `Stock`, `Order`, reporting)

6. **Documentation**
   - API docs auto-generated by Springdoc (OpenAPI 3.0)
   - Architecture decision record (ADR) per major choice
   - Deployment guide (profiles, env vars, DB setup)

---

## 20) Summary

This Spring Boot 3 architecture provides:

✅ **Clear Structure**: Organized by vertical slices with layered packages within each  
✅ **Separate Concerns**: Config, domain, infrastructure, testing isolated  
✅ **Scalability**: Ready for microservices decomposition  
✅ **Testability**: Unit, integration, contract, and e2e test support  
✅ **Modern Stack**: Java 21, Spring Boot 3.5, PostgreSQL, Flyway, Lombok, OpenAPI  
✅ **Maintainability**: Self-documenting via consistent naming and structure  
✅ **Security**: Exception handling, validation, audit trails  
✅ **Developer Experience**: Reduced boilerplate, clear ownership, parallel development  

The structure aligns with the **6-phase migration roadmap** in `DOMAIN_DEPENDENCY_ANALYSIS.md` and enables safe, incremental delivery starting with reference data (Phase 1).

---

**Document Version**: 1.0  
**Last Updated**: 2026-06-10  
**Next Review**: After Phase 1 implementation

