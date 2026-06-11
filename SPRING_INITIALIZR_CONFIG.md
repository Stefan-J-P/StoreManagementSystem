# Spring Initializr Configuration – Store Management System

**Date**: 2026-06-10  
**Target**: Java 21 · Spring Boot 3.5.x · Maven · PostgreSQL  
**Phase**: 1 – Reference Data (Country, Category, Trade, Payment)  
**Status**: Ready for project generation  

---

## 1) Spring Initializr – Dependency Selection

### URL (pre-filled)

```
https://start.spring.io/#!type=maven-project
  &language=java
  &bootVersion=3.5.0
  &baseDir=store-management-system
  &groupId=io.github.stefanjp
  &artifactId=store-management-system
  &name=store-management-system
  &description=Store%20Management%20System%20REST%20API
  &packageName=io.github.stefanjp.storemanagement
  &packaging=jar
  &javaVersion=21
  &dependencies=web,data-jpa,validation,actuator,postgresql,flyway,lombok,testcontainers
```

### Manual Selection (start.spring.io checkboxes)

| Category | Dependency | Initializr Name |
|----------|-----------|-----------------|
| Web | Spring Web | `web` |
| SQL | Spring Data JPA | `data-jpa` |
| SQL | Flyway Migration | `flyway` |
| SQL | PostgreSQL Driver | `postgresql` |
| I/O | Validation | `validation` |
| Ops | Spring Boot Actuator | `actuator` |
| Developer Tools | Lombok | `lombok` |
| Testing | Testcontainers | `testcontainers` |

> **Not in Spring Initializr**: `springdoc-openapi-starter-webmvc-ui` (OpenAPI/Swagger).  
> Add manually to `pom.xml` after generation. See Section 8.

---

## 2) Maven Coordinates

| Field | Value |
|-------|-------|
| **groupId** | `io.github.stefanjp` |
| **artifactId** | `store-management-system` |
| **version** | `0.0.1-SNAPSHOT` |
| **packaging** | `jar` |
| **name** | `store-management-system` |
| **description** | `Store Management System REST API` |
| **Package name** | `io.github.stefanjp.storemanagement` |
| **Java version** | `21` |
| **Spring Boot parent** | `3.5.0` (use latest `3.5.x` patch) |

**Rationale for `io.github.stefanjp`**:
- Follows reverse-domain naming convention
- Identifies the GitHub owner (`stefanjp`)
- Clearly separates this project from the legacy `jan.stefan.hibernate` namespace
- Compatible with GitHub Packages Maven registry if publishing

---

## 3) Initial Package Structure (Phase 1)

Reflects the vertical slice architecture from `SPRING_BOOT_3_ARCHITECTURE.md`.  
Only packages required for Phase 1 reference entities are listed.

```
src/main/java/io/github/stefanjp/storemanagement/
│
├── StoreManagementApplication.java              # @SpringBootApplication entry point
│
├── config/                                      # Cross-framework configuration beans
│   ├── OpenApiConfig.java                       # Springdoc grouping, API metadata
│   └── WebConfig.java                           # CORS, message converters (if needed)
│
├── core/                                        # Shared, non-domain infrastructure
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java          # @RestControllerAdvice
│   │   ├── EntityNotFoundException.java         # 404
│   │   ├── ConflictException.java               # 409 – duplicate name/key
│   │   ├── BusinessException.java               # 422 – generic domain rule violation
│   │   └── ErrorResponse.java                   # Standard error JSON contract (record)
│   └── dto/
│       └── PageResponse.java                    # Generic paginated response wrapper
│
└── domain/
    └── reference/                               # Phase 1 bounded context
        ├── country/
        │   ├── entity/
        │   │   └── Country.java
        │   ├── repository/
        │   │   └── CountryRepository.java
        │   ├── service/
        │   │   ├── CountryService.java          # interface
        │   │   └── CountryServiceImpl.java
        │   ├── controller/
        │   │   └── CountryController.java
        │   └── dto/
        │       ├── CountryCreateDto.java
        │       ├── CountryUpdateDto.java
        │       └── CountryResponseDto.java
        ├── category/                            # same internal structure as country/
        ├── trade/                               # same internal structure as country/
        └── payment/                             # same internal structure as country/
```

```
src/test/java/io/github/stefanjp/storemanagement/
│
├── integration/
│   ├── config/
│   │   └── IntegrationTestBase.java             # shared @SpringBootTest base
│   └── domain/
│       └── reference/
│           ├── country/
│           │   └── CountryControllerIT.java
│           ├── category/
│           │   └── CategoryControllerIT.java
│           ├── trade/
│           │   └── TradeControllerIT.java
│           └── payment/
│               └── PaymentControllerIT.java
│
└── unit/
    └── domain/
        └── reference/
            ├── country/
            │   └── CountryServiceTest.java
            └── ...
```

---

## 4) `application.yml` Design

Location: `src/main/resources/application.yml`

Key decisions:
- `ddl-auto: validate` — schema owned by Flyway, never by Hibernate
- `open-in-view: false` — disables OSIV; prevents lazy loading outside service boundary
- All connection parameters resolved from environment variables with safe defaults
- Actuator health endpoint restricted; no stack traces in error responses
- Swagger UI disabled by default (enabled only in `dev` profile)

```yaml
spring:
  application:
    name: store-management-system

  datasource:
    url: jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:store_management}
    username: ${DB_USER:postgres}
    password: ${DB_PASSWORD:postgres}
    driver-class-name: org.postgresql.Driver
    hikari:
      pool-name: StoreManagementPool
      maximum-pool-size: ${DB_POOL_MAX:10}
      minimum-idle: ${DB_POOL_MIN:2}
      connection-timeout: 20000
      idle-timeout: 300000
      max-lifetime: 1200000

  jpa:
    open-in-view: false
    hibernate:
      ddl-auto: validate
    show-sql: false
    properties:
      hibernate:
        format_sql: false
        default_schema: public
        jdbc:
          batch_size: 20
        order_inserts: true
        order_updates: true

  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: false
    validate-on-migrate: true
    out-of-order: false

  jackson:
    default-property-inclusion: non_null
    serialization:
      write-dates-as-timestamps: false
    deserialization:
      fail-on-unknown-properties: false

  mvc:
    throw-exception-if-no-handler-found: true

  web:
    resources:
      add-mappings: false

server:
  port: ${SERVER_PORT:8080}
  error:
    include-message: never
    include-binding-errors: never
    include-stacktrace: never
    include-exception: false

management:
  endpoints:
    web:
      base-path: /actuator
      exposure:
        include: health, info, metrics
  endpoint:
    health:
      show-details: when-authorized
      show-components: when-authorized
  info:
    env:
      enabled: true

springdoc:
  swagger-ui:
    enabled: false
  api-docs:
    enabled: false

logging:
  level:
    root: INFO
    io.github.stefanjp.storemanagement: INFO
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
```

---

## 5) `application-dev.yml` Design

Location: `src/main/resources/application-dev.yml`

Activation: `--spring.profiles.active=dev` (via IDE run config, `SPRING_PROFILES_ACTIVE=dev` env var, or Maven property)

Key decisions:
- SQL logging with formatted output (developer productivity)
- Swagger UI enabled (only for dev)
- Flyway validate relaxed (schema may drift during active development)
- Hibernate TRACE for SQL bind parameters (debug query issues)

```yaml
spring:
  jpa:
    show-sql: true
    properties:
      hibernate:
        format_sql: true

  flyway:
    validate-on-migrate: false

logging:
  level:
    io.github.stefanjp.storemanagement: DEBUG
    org.hibernate.SQL: DEBUG
    org.hibernate.orm.jdbc.bind: TRACE

springdoc:
  swagger-ui:
    enabled: true
    path: /swagger-ui.html
    operations-sorter: alpha
    tags-sorter: alpha
    try-it-out-enabled: true
  api-docs:
    enabled: true
    path: /v3/api-docs
```

---

## 6) `application-test.yml` Design

Location: `src/test/resources/application-test.yml`

Activation: `@ActiveProfiles("test")` on integration test classes.

Key decisions:
- Uses **Testcontainers JDBC URL** approach — spins up a real PostgreSQL container automatically
- No username/password required (Testcontainers provides internal defaults)
- Flyway runs normally against the container database (ensures migration scripts are tested)
- SQL disabled to keep test output clean
- Swagger disabled (not relevant during testing)

```yaml
spring:
  datasource:
    url: jdbc:tc:postgresql:15:///store_management_test
    driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver

  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
    properties:
      hibernate:
        format_sql: false

  flyway:
    enabled: true
    validate-on-migrate: true

springdoc:
  swagger-ui:
    enabled: false
  api-docs:
    enabled: false

logging:
  level:
    root: WARN
    io.github.stefanjp.storemanagement: INFO
    org.flywaydb: INFO
```

> **Note on Testcontainers JDBC URL**:  
> The URL `jdbc:tc:postgresql:15:///store_management_test` tells the Testcontainers JDBC driver to
> start a PostgreSQL 15 container automatically before any test that uses the datasource.  
> Requires: `testcontainers` dependency in `test` scope with `driver-class-name` set explicitly.  
> No separate `@Container` annotation or manual lifecycle management is needed.

---

## 7) Flyway Directory Structure

```
src/main/resources/
└── db/
    └── migration/
        ├── V1__create_reference_tables.sql       ← Phase 1 (Country, Category, Trade, Payment)
        ├── V2__create_master_entities.sql         ← Phase 2 (Customer, Producer, Shop)
        ├── V3__create_product_catalog.sql         ← Phase 3 (Product, guarantees)
        ├── V4__create_inventory_stock.sql         ← Phase 4 (Stock)
        ├── V5__create_order_transactions.sql      ← Phase 5 (Order)
        ├── V6__create_reporting_views.sql         ← Phase 6 (views / read models)
        └── V7__add_indexes_and_constraints.sql    ← Optimization (post-schema)
```

### Naming Convention

```
V{VERSION}__{DESCRIPTION}.sql
│              │
│              └─ Snake_case, describes content (not action)
└─ Monotonically increasing integer; no gaps
```

**Rules**:
- Never edit an already-applied migration
- Descriptive names are mandatory (Flyway validates uniqueness by checksum)
- `baseline-on-migrate: false` (fresh database starts at V1, no baselining)
- Only `V1__create_reference_tables.sql` is needed for Phase 1 bootstrap

### Phase 1 migration file (V1) — what it must contain

`V1__create_reference_tables.sql` must define:
- `countries` table (id, name, unique constraint on name)
- `categories` table (id, name, unique constraint on name)
- `trades` table (id, trade_name, unique constraint on trade_name)
- `payments` table (id, payment_type, unique constraint on payment_type)

> See `DOMAIN_DEPENDENCY_ANALYSIS.md` Section 7 (ER diagram) for column details.

---

## 8) OpenAPI Setup Recommendation

### Why `springdoc-openapi-starter-webmvc-ui`?

- Not managed by Spring Boot BOM → version is explicit in `pom.xml`
- Must be added manually after Spring Initializr generation
- Spring Boot 3.5.x is compatible with springdoc-openapi `2.8.x` and higher
- Check latest version at: https://github.com/springdoc/springdoc-openapi/releases

### Dependency to Add Manually

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>${springdoc.version}</version>
</dependency>
```

### Version Property (in `<properties>` block)

```xml
<springdoc.version>2.8.8</springdoc.version>
```

> Always verify compatibility at https://springdoc.org/#springboot-support before updating.

### Recommended `OpenApiConfig.java` setup

Add this class to `com.storemanagement.config`:

```java
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI storeManagementOpenApi() {
        return new OpenAPI()
            .info(new Info()
                .title("Store Management System API")
                .description("REST API for managing store inventory, orders, and reference data.")
                .version("1.0.0")
                .contact(new Contact().name("Stefan JP").url("https://github.com/stefanjp"))
                .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")));
    }

    @Bean
    public GroupedOpenApi referenceDataApi() {
        return GroupedOpenApi.builder()
            .group("reference-data")
            .displayName("Reference Data")
            .pathsToMatch("/api/v1/countries/**", "/api/v1/categories/**",
                          "/api/v1/trades/**", "/api/v1/payments/**")
            .build();
    }
}
```

### Access URLs (when dev profile is active)

| Resource | URL |
|----------|-----|
| Swagger UI | `http://localhost:8080/swagger-ui.html` |
| OpenAPI JSON | `http://localhost:8080/v3/api-docs` |
| OpenAPI YAML | `http://localhost:8080/v3/api-docs.yaml` |

---

## 9) Rationale for Every Dependency

### Included Dependencies

#### `spring-boot-starter-web`
- Provides embedded Tomcat, Spring MVC, REST support
- Required for all `@RestController` endpoints
- Includes Jackson for JSON serialization/deserialization
- Cannot use REST API without it

#### `spring-boot-starter-data-jpa`
- Provides Spring Data JPA (repositories, transactions, auditing)
- Replaces the entire manual Hibernate session/transaction management in the legacy code
- Enables derived query methods, pagination, `@Transactional`, and `JpaRepository<E, ID>`
- Brings Hibernate as JPA provider (already used in legacy, now properly managed)

#### `spring-boot-starter-validation`
- Jakarta Bean Validation (JSR-380) for DTO-level constraints
- Enables `@NotNull`, `@NotBlank`, `@Size`, `@Email`, `@Valid` on controller parameters
- Spring Boot does not auto-enable validation without this starter
- Required for input validation in Phase 1 Create/Update DTOs

#### `spring-boot-starter-actuator`
- Provides operational endpoints: `/actuator/health`, `/actuator/info`, `/actuator/metrics`
- Essential for production health checks (load balancer liveness/readiness probes)
- Works with Spring Boot's management layer; minimal overhead when restricted to health only
- Configured to expose only `health`, `info`, `metrics` (safe for production)

#### `postgresql` (runtime)
- PostgreSQL JDBC driver
- `runtime` scope: not needed at compile time, only at runtime
- Version managed by Spring Boot BOM (no explicit version needed)
- Replaces `mysql-connector-java` from the legacy project

#### `flyway-core`
- Database migration tool; manages schema versioning via SQL scripts
- Replaces `hibernate.hbm2ddl.auto=update` (unsafe in production)
- Runs automatically at application startup, applies pending migrations in order
- Maintains `flyway_schema_history` table for checksums and applied version tracking

#### `flyway-database-postgresql`
- Flyway 10+ split database-specific drivers into separate modules
- **Required** for PostgreSQL support in Flyway 10+ (bundled with Spring Boot 3.3+)
- Without this, Flyway will fail to connect to PostgreSQL
- Version managed by Spring Boot BOM alongside `flyway-core`

#### `lombok`
- Compile-time annotation processor: eliminates boilerplate for getters, setters, constructors, builders
- `optional: true` → not included in final artifact, not transitive
- Excluded from `spring-boot-maven-plugin` repackaging
- Key annotations: `@Getter`, `@Setter`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`, `@RequiredArgsConstructor`

#### `springdoc-openapi-starter-webmvc-ui` (manual addition)
- Auto-generates OpenAPI 3.0 documentation from Spring MVC annotations
- Provides interactive Swagger UI with no extra configuration
- Inspects `@RestController`, `@RequestMapping`, DTOs, and validation annotations
- NOT managed by Spring Boot BOM → explicit version required
- Disabled by default in `application.yml`; enabled only in `dev` profile

#### `spring-boot-starter-test` (test scope)
- Includes JUnit 5, Mockito, AssertJ, MockMvc, Spring Test
- Replaces the manually configured JUnit 4/5 + Mockito combination in the legacy `pom.xml`
- Sufficient for unit tests (no Spring context) and slice tests (`@WebMvcTest`, `@DataJpaTest`)

#### `testcontainers` + `testcontainers:postgresql` (test scope)
- Spins up a real PostgreSQL container during integration tests
- Tests run against the same database type as production (no H2/HSQLDB inconsistencies)
- `org.testcontainers:junit-jupiter` provides `@Testcontainers` and `@Container` annotations
- Simplest usage via TC JDBC URL in `application-test.yml` (see Section 6)

---

## 10) Optional Dependencies (NOT Included Initially)

The following dependencies are architecturally valuable but would add complexity that is premature for Phase 1. Revisit when the corresponding need arises.

| Dependency | When to Add | Reason for Deferral |
|-----------|-------------|---------------------|
| `spring-boot-starter-security` | When authentication/authorization is needed | No security requirements in Phase 1; adds complexity and requires filter chain configuration |
| `spring-boot-starter-cache` + `spring-boot-starter-data-redis` | When response caching is needed | No performance issue demonstrated; premature optimization |
| `mapstruct` | When DTO mapping becomes repetitive | Phase 1 has 4 simple entities; manual mapping is clear; MapStruct adds annotation processor configuration |
| `spring-boot-starter-aop` | When cross-cutting logging/auditing is needed | AOP adds conceptual overhead; SLF4J annotations sufficient for Phase 1 |
| `micrometer-registry-prometheus` | When Prometheus/Grafana monitoring is used | Actuator already exposes `/metrics`; Prometheus format is deployment-specific |
| `spring-retry` | When external service calls with retry logic are needed | No external calls in Phase 1 |
| `spring-boot-starter-mail` | When email notifications are needed | Not in scope for any current phase |
| `bucket4j-core` | When API rate limiting is needed | No rate-limiting requirement defined yet |
| `spring-boot-docker-compose` | When Docker Compose-based local dev is desired | Useful, but adds dependency on Docker and a `compose.yml` file |
| `hibernate-jpamodelgen` | When Criteria API with type-safe metamodel is needed | Specifications can be built without metamodel in Phase 1 |
| `datasource-proxy` | When SQL query logging with parameters is needed in detail | `org.hibernate.orm.jdbc.bind: TRACE` in `dev` profile is sufficient |
| `liquibase-core` | Alternative to Flyway | Flyway already chosen; mixing migration tools is an anti-pattern |
| `spring-data-envers` | When JPA entity audit history (Hibernate Envers) is needed | No audit history requirement in Phase 1 |
| `jacoco-maven-plugin` | When code coverage enforcement is needed | Add in CI pipeline phase; not needed for initial development |

---

## Project Generation Steps

1. **Generate via Spring Initializr**

   ```bash
   curl https://start.spring.io/starter.zip \
     -d type=maven-project \
     -d language=java \
     -d bootVersion=3.5.0 \
     -d groupId=io.github.stefanjp \
     -d artifactId=store-management-system \
     -d packageName=io.github.stefanjp.storemanagement \
     -d javaVersion=21 \
     -d dependencies=web,data-jpa,validation,actuator,postgresql,flyway,lombok,testcontainers \
     -o store-management-system.zip
   ```

   Or use the web UI at: **https://start.spring.io**

2. **Add `springdoc-openapi-starter-webmvc-ui` to `pom.xml` manually** (not in Initializr)

3. **Replace `application.properties`** with `application.yml`, `application-dev.yml`

4. **Create `src/test/resources/application-test.yml`**

5. **Create `src/main/resources/db/migration/` directory**

6. **Write `V1__create_reference_tables.sql`** following schema from `DOMAIN_DEPENDENCY_ANALYSIS.md`

7. **Implement Phase 1 vertical slices** following `SPRING_BOOT_3_FOLDER_TREE.md`

---

**Document Version**: 1.0  
**Created**: 2026-06-10  
**Reference files**: `pom.xml`, `application.yml`, `application-dev.yml`, `application-test.yml`

