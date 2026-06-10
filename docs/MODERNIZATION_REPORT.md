# StoreManagementSystem Modernization Report

Date: 2026-06-10
Scope: full repository (`src/main/java`, `src/test/java`, build/runtime config)

## Executive Assessment

- Current app is a monolithic CLI application with manual dependency wiring in `App` and ad-hoc repository/service layers.
- Persistence uses old JPA (`javax.persistence`) + Hibernate 5 + MySQL-specific setup from `persistence.xml`.
- Significant modernization is required for Spring Boot 3 (Jakarta namespace, dependency injection, REST controllers, Spring Data JPA).
- Business domain is still valuable and can be preserved: customers, products, producers, orders, shops, stock, countries, trades.

## Package-by-Package + Class-by-Class Review

Legend for decision:
- `MIGRATE AS-IS` = move with minimal change
- `REDESIGN` = keep concept, reimplement in Boot 3 style
- `REMOVE` = delete from target architecture

---

## `jan.stefan.hibernate`
Purpose: bootstrap / composition root.
Business domain role: application startup.

- `App`  
  Purpose: manual creation of repositories/services/validators/menus and start CLI loop.  
  Dependencies: almost all services/repos/validators/menu classes.  
  Decision: `REMOVE` (replace with `@SpringBootApplication` bootstrap).

## `jan.stefan.hibernate.connection`
Purpose: database connection factory management.
Business domain role: technical infrastructure.

- `DbConnection`  
  Purpose: singleton `EntityManagerFactory` provider.  
  Dependencies: `javax.persistence.Persistence`, `persistence.xml`.  
  Decision: `REMOVE` (Spring Boot manages `DataSource` + `EntityManagerFactory`).

## `jan.stefan.hibernate.dataInDbValidation`
Purpose: cross-entity existence checks and dedup lookup.
Business domain role: domain consistency helper.

- `DataBaseValidator`  
  Purpose: ensure related entities exist or create/find by business key.  
  Dependencies: repositories, `ModelMapper`, DTOs, `MyException`.  
  Decision: `REDESIGN` (replace with domain services + unique constraints + transactional upsert policies).
- `stringValidator`  
  Purpose: primitive duplicate checks for customer text fields.  
  Dependencies: implemented by `DataBaseValidator`.  
  Decision: `REDESIGN` (replace with dedicated validator/service interface; rename with Java naming conventions).

## `jan.stefan.hibernate.dto.modelDto`
Purpose: transport objects used by services/menu.
Business domain role: application boundary contracts.

- `CategoryDto` - Purpose: category payload | Domain: catalog | Deps: Lombok | Decision: `REDESIGN` (REST request/response DTO or record).
- `CountryDto` - Purpose: country payload | Domain: reference data | Deps: Lombok | Decision: `REDESIGN`.
- `CustomerDto` - Purpose: customer payload | Domain: customer mgmt | Deps: `CountryDto`, unused converter import | Decision: `REDESIGN`.
- `MyErrorDto` - Purpose: error log payload | Domain: operational diagnostics | Deps: `LocalDateTime` | Decision: `REDESIGN`.
- `MyOrderDto` - Purpose: order payload | Domain: sales/order | Deps: nested DTO graph | Decision: `REDESIGN`.
- `PaymentDto` - Purpose: payment payload | Domain: checkout/payment | Deps: `EPayment` | Decision: `REDESIGN`.
- `ProducerDto` - Purpose: producer payload | Domain: supplier mgmt | Deps: `CountryDto`, `TradeDto` | Decision: `REDESIGN`.
- `ProductDto` - Purpose: product payload | Domain: catalog | Deps: `EGuarantee`, nested DTOs | Decision: `REDESIGN`.
- `ShopDto` - Purpose: shop payload | Domain: store network | Deps: `CountryDto` | Decision: `REDESIGN`.
- `StockDto` - Purpose: inventory payload | Domain: stock/inventory | Deps: `ShopDto`, `ProductDto` | Decision: `REDESIGN`.
- `TradeDto` - Purpose: trade/industry payload | Domain: reference taxonomy | Deps: Lombok | Decision: `REDESIGN`.

## `jan.stefan.hibernate.dto.modelDto.statModelDto`
Purpose: reporting DTOs.
Business domain role: analytics projection.

- `ProductByCustomersCountryDto`  
  Purpose: combined projection for country/age/order/product analytics.  
  Dependencies: domain entities directly (`Customer`, `Product`, `Country`, etc.), unused imports.  
  Decision: `REDESIGN` (projection interface/record from JPQL/native query result).

## `jan.stefan.hibernate.exceptions`
Purpose: custom runtime exception.
Business domain role: error signaling.

- `MyException`  
  Purpose: wraps application-level error messages.  
  Dependencies: none.  
  Decision: `REDESIGN` (typed exceptions + `@ControllerAdvice` error model).

## `jan.stefan.hibernate.menu`
Purpose: CLI UI + command orchestration + statistics calls.
Business domain role: user interaction layer (console).

- `MenuPanel`  
  Purpose: huge interactive CLI menu tree and option dispatch.  
  Dependencies: scanner, services, generator, validator, Gson.  
  Decision: `REMOVE` (replaced by REST controllers).
- `MenuService`  
  Purpose: CLI command handlers for CRUD operations.  
  Dependencies: validators + all core services.  
  Decision: `REMOVE` (split into REST controllers + application services).
- `MenuStatistics`  
  Purpose: statistics use cases (one implemented, others TODO).  
  Dependencies: `ProductService`, `MyOrderService`, partial stream logic.  
  Decision: `REDESIGN` (create dedicated reporting service + REST endpoints).

## `jan.stefan.hibernate.model`
Purpose: persistence entities.
Business domain role: core business model.

- `Category` - Purpose: product category | Domain: catalog | Deps: JPA `javax`, `Product` relation | Decision: `REDESIGN` (Jakarta + constraints + equals/hashCode tuning).
- `Country` - Purpose: country reference | Domain: geo reference | Deps: `Shop`,`Producer`,`Customer` relations | Decision: `REDESIGN`.
- `Customer` - Purpose: customer master | Domain: customer mgmt | Deps: `Country`, `MyOrder`; unused imports | Decision: `REDESIGN`.
- `MyError` - Purpose: error entity | Domain: operations logging | Deps: JPA + timestamp | Decision: `REDESIGN` (or `REMOVE` if external logging adopted).
- `MyOrder` - Purpose: order entity | Domain: sales | Deps: `Customer`,`Product`,`Payment` | Decision: `REDESIGN` (table naming + constraints + lifecycle).
- `Payment` - Purpose: payment method aggregate | Domain: checkout | Deps: `EPayment`, `MyOrder` | Decision: `REDESIGN`.
- `Producer` - Purpose: producer/supplier | Domain: supplier mgmt | Deps: `Country`,`Trade`,`Product` | Decision: `REDESIGN`.
- `Product` - Purpose: product catalog item | Domain: catalog | Deps: category/producer/trade/guarantees/stocks/orders | Decision: `REDESIGN`.
- `Shop` - Purpose: shop branch | Domain: store network | Deps: `Country`, `Stock` | Decision: `REDESIGN`.
- `Stock` - Purpose: inventory entry | Domain: inventory | Deps: `Shop`,`Product`; has `name` queried by repo | Decision: `REDESIGN`.
- `Trade` - Purpose: trade branch/type | Domain: taxonomy | Deps: `Producer` | Decision: `REDESIGN`.

## `jan.stefan.hibernate.model.enums`
Purpose: enum dictionaries.
Business domain role: constrained values.

- `EGuarantee`  
  Purpose: guarantee components list.  
  Dependencies: used in `Product`, `ProductDto`, scanner parsing.  
  Decision: `MIGRATE AS-IS` (rename optional; keep semantics).
- `EPayment`  
  Purpose: payment method list.  
  Dependencies: `Payment`, `PaymentDto`, scanner.  
  Decision: `MIGRATE AS-IS`.

## `jan.stefan.hibernate.model.validation`
Purpose: manual validators for DTO/entities.
Business domain role: input quality gate.

- `CategoryValidation` - Purpose: category name validation | Domain: input validation | Deps: `CategoryDto` | Decision: `REDESIGN` (Bean Validation; current `hasErrors` bug).
- `CountryValidation` - Purpose: country name validation | Domain: input validation | Deps: `CountryDto` | Decision: `REDESIGN`.
- `CustomerValidation` - Purpose: customer fields validation | Domain: input validation | Deps: `CustomerDto` | Decision: `REDESIGN`.
- `OrderValidation` - Purpose: order validation skeleton | Domain: input validation | Deps: `MyOrderDto` | Decision: `REMOVE` (current impl incomplete) then reintroduce as Bean Validation annotations.
- `PaymentValidation` - Purpose: payment validator stub | Domain: input validation | Deps: `Payment` | Decision: `REMOVE` (stub).
- `ProducerValidation` - Purpose: producer payload checks | Domain: input validation | Deps: `ProducerDto` | Decision: `REDESIGN`.
- `ProductValidation` - Purpose: product name/price checks | Domain: input validation | Deps: `ProductDto` | Decision: `REDESIGN`.
- `ShopValidation` - Purpose: shop validation | Domain: input validation | Deps: `ShopDto` | Decision: `REDESIGN`.
- `StockValidation` - Purpose: stock validator stub | Domain: input validation | Deps: `StockDto` | Decision: `REMOVE` (stub).
- `TradeValidation` - Purpose: trade name validation | Domain: input validation | Deps: `TradeDto` | Decision: `REDESIGN` (current `hasErrors` bug).

## `jan.stefan.hibernate.model.validation.generic`
Purpose: validator abstraction.
Business domain role: validation framework.

- `Validator`  
  Purpose: validator contract (`validate`, `hasErrors`).  
  Dependencies: `Map`.  
  Decision: `REMOVE` (superseded by Jakarta Bean Validation API).
- `AbstractValidator`  
  Purpose: shared error map and `hasErrors`.  
  Dependencies: `Validator`.  
  Decision: `REMOVE`.

## `jan.stefan.hibernate.repository.generic`
Purpose: hand-written generic CRUD repository layer.
Business domain role: persistence abstraction.

- `GenericRepository`  
  Purpose: base CRUD API.  
  Dependencies: collections/optional.  
  Decision: `REMOVE` (replace by Spring Data `JpaRepository`).
- `AbstractGenericRepository`  
  Purpose: generic JPA implementation with manual tx/entity manager lifecycle.  
  Dependencies: `DbConnection`, JPA transactions, reflection.  
  Decision: `REMOVE`.

## `jan.stefan.hibernate.repository.repositoryInterfaces`
Purpose: domain-specific repository contracts.
Business domain role: data access entry points.

- `CategoryRepository` - Purpose: category access by name | Domain: catalog persistence | Deps: generic repo | Decision: `REDESIGN` as `JpaRepository<Category, Long>`.
- `CountryRepository` - Purpose: country access by name | Domain: reference persistence | Deps: generic repo | Decision: `REDESIGN`.
- `CustomerRepository` - Purpose: customer access by email/name/surname | Domain: customer persistence | Deps: generic repo | Decision: `REDESIGN`.
- `MyErrorRepository` - Purpose: error record access | Domain: diagnostics persistence | Deps: generic repo | Decision: `REDESIGN` or `REMOVE` if using logs only.
- `OrderRepository` - Purpose: order queries (number, last number, by customer country/age) | Domain: order persistence/reporting | Deps: generic repo + `CountryDto` leak | Decision: `REDESIGN`.
- `PaymentRepository` - Purpose: payment CRUD | Domain: payment persistence | Deps: generic repo | Decision: `REDESIGN`.
- `ProducerRepository` - Purpose: producer by name | Domain: supplier persistence | Deps: generic repo | Decision: `REDESIGN`.
- `ProductRepository` - Purpose: product by name | Domain: catalog persistence | Deps: generic repo | Decision: `REDESIGN`.
- `ShopRepository` - Purpose: shop by name | Domain: shop persistence | Deps: generic repo | Decision: `REDESIGN`.
- `StockRepository` - Purpose: stock by name | Domain: inventory persistence | Deps: generic repo | Decision: `REDESIGN`.
- `TradeRepository` - Purpose: trade by name | Domain: taxonomy persistence | Deps: generic repo | Decision: `REDESIGN`.

## `jan.stefan.hibernate.repository.implementation`
Purpose: concrete data access with JPQL/native queries.
Business domain role: persistence implementation.

- `CategoryRepositoryImpl` - Purpose: category query impl | Domain: catalog persistence | Deps: JPA tx, generic abstract repo | Decision: `REMOVE` (replaced by Spring Data proxy).
- `CountryRepositoryImpl` - Purpose: country query impl | Domain: reference persistence | Deps: JPA tx | Decision: `REMOVE`.
- `CustomerRepositoryImpl` - Purpose: customer query impl | Domain: customer persistence | Deps: JPA tx | Decision: `REMOVE`.
- `MyErrorRepositoryImpl` - Purpose: basic error CRUD impl | Domain: diagnostics persistence | Deps: generic abstract repo | Decision: `REMOVE`.
- `OrderRepositoryImpl` - Purpose: order custom query impl | Domain: order persistence/reporting | Deps: JPQL/native-like query strings | Decision: `REDESIGN` (custom `@Query`/Specification + projections).
- `PaymentRepositoryImpl` - Purpose: payment CRUD impl | Domain: payment persistence | Deps: generic abstract repo | Decision: `REMOVE`.
- `ProducerRepositoryImpl` - Purpose: producer query impl | Domain: supplier persistence | Deps: JPA tx | Decision: `REMOVE`.
- `ProductRepositoryImpl` - Purpose: product query impl | Domain: catalog persistence | Deps: JPA tx | Decision: `REMOVE`.
- `ShopRepositoryImpl` - Purpose: shop query impl | Domain: network persistence | Deps: JPA tx | Decision: `REMOVE`.
- `StockRepositoryImpl` - Purpose: stock query impl | Domain: inventory persistence | Deps: JPA tx; queries `Stock.name` | Decision: `REDESIGN` (or `REMOVE` with derived query).
- `TradeRepositoryImpl` - Purpose: trade query impl | Domain: taxonomy persistence | Deps: JPA tx | Decision: `REMOVE`.

## `jan.stefan.hibernate.service`
Purpose: use-case orchestration over repositories.
Business domain role: application services.

- `CategoryService` - Purpose: category CRUD use case | Domain: catalog ops | Deps: category repo + mapper | Decision: `REDESIGN` (`@Service` + tx boundaries).
- `CountryService` - Purpose: country CRUD | Domain: reference ops | Deps: country repo + mapper | Decision: `REDESIGN`.
- `CustomerService` - Purpose: customer CRUD + country attach | Domain: customer ops | Deps: customer repo + `DataBaseValidator` | Decision: `REDESIGN`.
- `DataGenerationService` - Purpose: placeholder service | Domain: none | Deps: none | Decision: `REMOVE`.
- `MyErrorService` - Purpose: persist/read business errors | Domain: diagnostics | Deps: myError repo + mapper | Decision: `REDESIGN` or `REMOVE`.
- `MyOrderService` - Purpose: order CRUD and query by order number/country-age | Domain: order ops | Deps: order repo + validator + mapper | Decision: `REDESIGN`.
- `PaymentService` - Purpose: payment CRUD | Domain: payment ops | Deps: payment repo + mapper | Decision: `REDESIGN`.
- `ProducerService` - Purpose: producer CRUD + country/trade attach | Domain: supplier ops | Deps: producer repo + validator + mapper | Decision: `REDESIGN`.
- `ProductService` - Purpose: product CRUD + category/producer/trade attach | Domain: catalog ops | Deps: product repo + validator + mapper | Decision: `REDESIGN`.
- `ScannerService` - Purpose: CLI input parsing and enum parsing | Domain: UI plumbing | Deps: console scanner | Decision: `REMOVE`.
- `ShopService` - Purpose: shop CRUD + country attach | Domain: network ops | Deps: shop repo + validator + mapper | Decision: `REDESIGN`.
- `StockService` - Purpose: stock CRUD + product/shop attach | Domain: inventory ops | Deps: stock repo + validator + mapper | Decision: `REDESIGN`.
- `TradeService` - Purpose: trade CRUD | Domain: taxonomy ops | Deps: trade repo + mapper | Decision: `REDESIGN`.

## `jan.stefan.hibernate.service.JsonConverter`
Purpose: Gson-based file serialization wrappers.
Business domain role: import/export support.

- `JsonConverter`  
  Purpose: generic JSON read/write helper with runtime generic type extraction.  
  Dependencies: Gson, file IO, custom exception.  
  Decision: `REDESIGN` (Jackson + Spring `ObjectMapper` + explicit import/export service).
- `CategoryJsonConverter` - Purpose: typed wrapper | Domain: import/export | Deps: generic converter | Decision: `REMOVE`.
- `CountryJsonConverter` - Purpose: typed wrapper | Domain: import/export | Deps: generic converter | Decision: `REMOVE`.
- `CustomerJsonConverter` - Purpose: typed wrapper | Domain: import/export | Deps: generic converter | Decision: `REMOVE`.
- `CustomerListJson` - Purpose: list wrapper | Domain: import/export | Deps: generic converter | Decision: `REMOVE`.
- `MyOrderJsonConverter` - Purpose: typed wrapper | Domain: import/export | Deps: generic converter | Decision: `REMOVE`.
- `PaymentJsonConverter` - Purpose: typed wrapper | Domain: import/export | Deps: generic converter | Decision: `REMOVE`.
- `ProducerJsonConverter` - Purpose: typed wrapper | Domain: import/export | Deps: generic converter | Decision: `REMOVE`.
- `ProductJsonConverter` - Purpose: typed wrapper | Domain: import/export | Deps: generic converter | Decision: `REMOVE`.
- `ShopJsonConverter` - Purpose: typed wrapper | Domain: import/export | Deps: generic converter | Decision: `REMOVE`.
- `StockJsonConverter` - Purpose: typed wrapper | Domain: import/export | Deps: generic converter | Decision: `REMOVE`.
- `TradeJsonConverter` - Purpose: typed wrapper | Domain: import/export | Deps: generic converter | Decision: `REMOVE`.

## `jan.stefan.hibernate.service.dataGenerator`
Purpose: test/demo data generators and file readers.
Business domain role: seed/demo support.

- `DataManager` - Purpose: random values + file reads + test hooks | Domain: seed utilities | Deps: validator, customer service | Decision: `REDESIGN` (`SeedDataService`, faker, resource loader).
- `CustomerDataManager` - Purpose: generate synthetic customer sets | Domain: seed data | Deps: `DataManager`, `CustomerService`, validator | Decision: `REDESIGN`.
- `CountriesDataManager` - Purpose: country JSON read stub | Domain: seed import | Deps: `CountryJsonConverter` | Decision: `REMOVE` (stub/incomplete).
- `CategoriesDataManager` - Purpose: empty placeholder | Domain: none | Deps: none | Decision: `REMOVE`.
- `MyOrdersDataManager` - Purpose: empty placeholder | Domain: none | Deps: none | Decision: `REMOVE`.
- `ProducersDataManager` - Purpose: empty placeholder | Domain: none | Deps: none | Decision: `REMOVE`.
- `ProductsDataManager` - Purpose: empty placeholder | Domain: none | Deps: none | Decision: `REMOVE`.
- `ShopsDataManager` - Purpose: empty placeholder | Domain: none | Deps: none | Decision: `REMOVE`.
- `StocksDataManager` - Purpose: empty placeholder | Domain: none | Deps: none | Decision: `REMOVE`.
- `TradesDataManager` - Purpose: empty placeholder | Domain: none | Deps: none | Decision: `REMOVE`.

## `jan.stefan.hibernate.service.mappers`
Purpose: static DTO/entity mapper layer.
Business domain role: boundary conversion.

- `ModelMapper`  
  Purpose: static conversion functions entity <-> DTO.  
  Dependencies: all DTOs + entities; manual mapping and nested recursion risk.  
  Decision: `REDESIGN` (MapStruct/manual mapper components with clear read/write DTO models).

---

## Test Code Assessment (`src/test/java`)

- `AppTest`: legacy smoke test (JUnit 4 style), no meaningful behavior coverage -> `REMOVE/REWRITE`.
- `CustomerServiceTest`: mostly scaffold; not validating behavior due missing assertions -> `REWRITE`.
- `MyOrderTest` / `StatisticsTest`: partial unit tests for statistics grouping; useful concept but tied to old service shapes -> `REDESIGN` and keep scenarios.

---

## Target Spring Boot 3 Architecture (Java 21 + PostgreSQL + Spring Data JPA + REST)

## Proposed module layout

- `com.storemanagement` (new root package)
  - `StoreManagementApplication` (`@SpringBootApplication`)
  - `config`
    - `JpaConfig` (only if custom beans needed)
    - `OpenApiConfig` (optional)
  - `domain`
    - `model` (JPA entities using `jakarta.persistence`)
    - `repository` (Spring Data `JpaRepository` interfaces)
    - `service` (business services with `@Transactional`)
    - `validation` (domain validators if rules exceed Bean Validation)
  - `api`
    - `controller` (REST controllers by aggregate: customers/products/orders/...)
    - `dto`
      - `request`
      - `response`
      - `mapper` (MapStruct mappers)
    - `error`
      - `ApiError`, `GlobalExceptionHandler`
  - `reporting`
    - `service` (statistics/read-model queries)
    - `projection` (DTO projections)
  - `seed`
    - `SeedDataService` + optional `CommandLineRunner` for local/dev bootstrap

## Core technical decisions

- Java 21 features: records for immutable API DTOs, sealed interfaces only where useful.
- Spring Boot 3 starters:
  - `spring-boot-starter-web`
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-validation`
  - `postgresql`
  - `spring-boot-starter-test`
- Persistence migration:
  - `javax.persistence.*` -> `jakarta.persistence.*`
  - explicit table/column names and constraints
  - DB migrations with Flyway (`V1__baseline.sql`, ...)
- API style:
  - `/api/v1/customers`, `/products`, `/orders`, `/stocks`, ...
  - pagination for list endpoints
  - consistent error responses from `@ControllerAdvice`

## Domain aggregate suggestion

- Aggregates: `Customer`, `Product`, `Order`, `Stock`, `Shop`, `Producer`.
- Reference entities: `Country`, `Category`, `Trade`, `PaymentMethod` (enum-backed).
- Reporting endpoints separated from transactional CRUD services.

---

## Migration Order (Easiest -> Hardest)

1. **Build and platform baseline**  
   Upgrade `pom.xml` to Spring Boot 3 parent, Java 21, PostgreSQL driver, test stack.
2. **Namespace and entity modernization**  
   Move entities to `jakarta.persistence`, fix mappings, remove legacy imports/unused fields.
3. **Repository migration**  
   Replace generic/impl repositories with Spring Data interfaces and derived queries.
4. **Service layer port**  
   Recreate CRUD business services with constructor injection + transactions.
5. **Validation modernization**  
   Replace manual validators with Bean Validation annotations and custom validators where needed.
6. **API contract design**  
   Split DTOs into request/response models, add mappers (MapStruct/manual).
7. **REST controllers**  
   Implement endpoints for each aggregate and replace CLI `menu`/`scanner` flow.
8. **Statistics/reporting redesign**  
   Reimplement `MenuStatistics` scenarios via projection queries and dedicated reporting service.
9. **Seed/import modernization**  
   Replace data-generator/json-converter stubs with robust seed/import module.
10. **Testing overhaul**  
   Add unit tests (service/mappers), repository integration tests (`@DataJpaTest`), API tests (`@WebMvcTest`/integration).
11. **Operational hardening**  
   Add structured logging, exception taxonomy, actuator/health checks, CI pipeline.

---

## Biggest Modernization Risks

- Coupled DTO/entity graph can create recursion and over-fetching when exposed directly in REST.
- Existing query `getProductsByCustomersCountry` uses SQL-like syntax in JPQL context and ignores parameters.
- Validation classes contain logical defects (`hasErrors` returning false in multiple validators).
- Manual transaction handling can hide failures currently; behavior may change after proper transaction boundaries.

## Immediate Next Step Recommendation

Start with a **thin vertical slice** (Customer): entity -> repository -> service -> controller -> tests, then replicate pattern to other aggregates.

