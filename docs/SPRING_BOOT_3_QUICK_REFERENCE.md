# Spring Boot 3 Architecture - Quick Reference Card

## 📦 Package Structure at a Glance

```
com.storemanagement
├── config/                (JpaConfig, ValidationConfig, OpenApiConfig, etc.)
├── core/                  (exception, validation, logging, utils, dto)
├── domain/
│   ├── reference/        (country, category, trade, payment)
│   ├── master/           (customer, producer, shop)
│   ├── catalog/          (product, inventory)
│   ├── order/            (order, event, workflow)
│   └── reporting/        (analytics, reports)
└── infra/                (persistence, audit)
```

---

## 📊 Vertical Slice Template (Per Entity)

Each entity lives in its vertical slice with this structure:

```
domain/{domain}/{entity}/
├── entity/
│   └── {Entity}.java
├── repository/
│   ├── {Entity}Repository.java         (extends JpaRepository)
│   └── {Entity}QueryRepository.java    (optional: custom queries)
├── service/
│   ├── {Entity}Service.java            (interface)
│   ├── {Entity}ServiceImpl.java         (@Service)
│   ├── {Entity}Validator.java          (optional: business rules)
│   └── {Entity}WorkFlow.java           (optional: orchestration)
├── controller/
│   ├── {Entity}Controller.java         (POST/PUT/DELETE, modify)
│   └── {Entity}QueryController.java    (optional: GET, read-only)
└── dto/
    ├── {Entity}CreateDto.java
    ├── {Entity}UpdateDto.java
    ├── {Entity}ResponseDto.java
    ├── {Entity}SearchDto.java
    └── {Entity}DetailDto.java
```

---

## 🎯 Common Annotation Patterns

### Entity
```java
@Entity
@Table(name = "countries")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Country extends AuditableEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank @Column(unique = true, nullable = false)
    private String name;
    
    @OneToMany(mappedBy = "country", cascade = CascadeType.PERSIST)
    private Set<Customer> customers;
}
```

### Repository
```java
@Repository
public interface CountryRepository extends 
        JpaRepository<Country, Long>,
        JpaSpecificationExecutor<Country> {
    
    Optional<Country> findByNameIgnoreCase(String name);
    List<Country> findByNameContaining(String name);
}
```

### Service
```java
@Service
@RequiredArgsConstructor
@Transactional
public class CountryServiceImpl implements CountryService {
    private final CountryRepository repository;
    private final ModelMapper mapper;
    
    @Override
    public CountryResponseDto create(@Valid CountryCreateDto dto) {
        Country country = mapper.map(dto, Country.class);
        Country saved = repository.save(country);
        return mapper.map(saved, CountryResponseDto.class);
    }
    
    @Override
    @Transactional(readOnly = true)
    public CountryResponseDto findById(Long id) {
        return repository.findById(id)
            .map(c -> mapper.map(c, CountryResponseDto.class))
            .orElseThrow(() -> new EntityNotFoundException("Country not found"));
    }
}
```

### Controller
```java
@RestController
@RequestMapping("/api/v1/countries")
@RequiredArgsConstructor
@Tag(name = "Countries", description = "Country management")
public class CountryController {
    private final CountryService service;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create country")
    public CountryResponseDto create(@Valid @RequestBody CountryCreateDto dto) {
        return service.create(dto);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get country by ID")
    public CountryResponseDto findById(@PathVariable Long id) {
        return service.findById(id);
    }
    
    @GetMapping
    @Operation(summary = "List countries")
    public Page<CountryResponseDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return service.getAll(PageRequest.of(page, size));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update country")
    public CountryResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody CountryUpdateDto dto) {
        return service.update(id, dto);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete country")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
```

### DTO
```java
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class CountryCreateDto {
    @NotBlank(message = "Country name is required")
    @Size(min = 2, max = 100)
    private String name;
}

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class CountryResponseDto {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

---

## 🗄️ Flyway Naming Convention

```
V{NUMBER}__{DESCRIPTION}.sql

Examples:
V1__Initial_Schema_Reference.sql
V2__Create_Master_Entities.sql
V3__Create_Product_Catalog.sql
V4__Create_Inventory_Stock.sql
V5__Create_Order_Transactions.sql
V6__Create_Reporting_Views.sql
V7__Add_Indexes_And_Constraints.sql
```

---

## 🧪 Testing Structure

### Unit Test (Service)
```java
class CountryServiceTest {
    @Mock CountryRepository repository;
    @InjectMocks CountryServiceImpl service;
    @Mock ModelMapper mapper;
    
    @Test
    void create_WithValidDto_ShouldReturnResponse() {
        CountryCreateDto dto = new CountryCreateDto("USA");
        Country entity = new Country(null, "USA", null);
        CountryResponseDto response = new CountryResponseDto(1L, "USA", null, null);
        
        when(repository.save(any())).thenReturn(entity);
        when(mapper.map(dto, Country.class)).thenReturn(entity);
        when(mapper.map(entity, CountryResponseDto.class)).thenReturn(response);
        
        CountryResponseDto result = service.create(dto);
        assertThat(result).isEqualTo(response);
    }
}
```

### Integration Test (Service + Repo with DB)
```java
@SpringBootTest
@Transactional
class CountryServiceIT extends IntegrationTestBase {
    @Autowired CountryService service;
    @Autowired CountryRepository repository;
    
    @Test
    void create_ShouldPersistAndRetrieve() {
        CountryCreateDto dto = new CountryCreateDto("Canada");
        CountryResponseDto created = service.create(dto);
        
        CountryResponseDto retrieved = service.findById(created.getId());
        assertThat(retrieved.getName()).isEqualTo("Canada");
    }
}
```

---

## 🔧 Configuration Pattern

### application.yml (Default)
```yaml
spring:
  application:
    name: store-management-system
  
  datasource:
    url: jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:store_management}
    username: ${DB_USER:postgres}
    password: ${DB_PASSWORD:postgres}
  
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
  
  flyway:
    enabled: true
    locations: classpath:db/migration
    baselineVersion: 0
```

### application-dev.yml
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
```

---

## 📋 REST Endpoint Conventions

| Method | Endpoint | Status | Purpose |
|--------|----------|--------|---------|
| **POST** | `/api/v1/{resource}` | 201 | Create |
| **GET** | `/api/v1/{resource}` | 200 | List (paginated) |
| **GET** | `/api/v1/{resource}/{id}` | 200 | Retrieve |
| **PUT** | `/api/v1/{resource}/{id}` | 200 | Update |
| **DELETE** | `/api/v1/{resource}/{id}` | 204 | Delete |
| **GET** | `/api/v1/{resource}/search` | 200 | Complex query |

---

## 🚨 Exception Handling Pattern

### Define Exception
```java
public class EntityNotFoundException extends ApiException {
    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "ENTITY_NOT_FOUND");
    }
}
```

### Handle Globally
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle(EntityNotFoundException e) {
        return ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(404)
            .error("Not Found")
            .message(e.getMessage())
            .build();
    }
}
```

### Throw from Service
```java
Country country = repository.findById(id)
    .orElseThrow(() -> new EntityNotFoundException("Country not found"));
```

---

## ✅ Phase Checklist (Per Phase)

- [ ] Create `domain/{domain}/` package structure
- [ ] Create entities (extend `AuditableEntity`)
- [ ] Add JPA annotations, validators
- [ ] Create Spring Data repository
- [ ] Create service interface
- [ ] Create service implementation (`@Service`, `@Transactional`)
- [ ] Create DTOs (Create/Update/Response)
- [ ] Create REST controller (`@RestController`)
- [ ] Add OpenAPI annotations
- [ ] Create unit tests (mocked repos)
- [ ] Create integration tests (real DB)
- [ ] Write Flyway migration (V{N}__*.sql)
- [ ] Test migration (forward + rollback)
- [ ] Write E2E test
- [ ] Update API documentation
- [ ] Code review
- [ ] Merge to main branch

---

## 🎓 Common Use Cases

### Pagination
```java
Page<CountryResponseDto> countries = service.getAll(
    PageRequest.of(0, 20, Sort.by("name").ascending())
);
```

### Custom Query
```java
@Query("SELECT c FROM Country c WHERE c.name LIKE %:name%")
List<Country> findByNameContaining(@Param("name") String name);
```

### Specification (Dynamic Filtering)
```java
Specification<Country> spec = (root, query, cb) -> 
    cb.like(cb.lower(root.get("name")), "%" + searchTerm.toLowerCase() + "%");
    
Page<Country> results = repository.findAll(spec, pageable);
```

### Validation
```java
public CountryResponseDto create(@Valid CountryCreateDto dto) {
    // @Valid triggers validation, @NotBlank on dto fields, etc.
}
```

### Transaction
```java
@Transactional
public void complexOperation() {
    // All DB changes rolled back if exception thrown
}

@Transactional(readOnly = true)
public CountryResponseDto findById(Long id) {
    // Optimized query
}
```

---

## 🚀 Quick Start Commands

```bash
# Build project
mvn clean install

# Run application
mvn spring-boot:run

# Run tests
mvn test                    # Unit tests only
mvn verify                  # Unit + integration tests
mvn verify -P integration   # Integration tests only

# Check test coverage
mvn jacoco:report

# Generate API docs (automatic on startup)
# Visit: http://localhost:8080/swagger-ui.html

# Package for deployment
mvn clean package
java -jar target/*.jar
```

---

## 📦 Maven Dependency Pattern

```xml
<!-- Spring Boot 3 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Data Access -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.1</version>
</dependency>

<!-- Validation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- API Documentation -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.0.4</version>
</dependency>

<!-- Flyway -->
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>

<!-- Testing -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>testcontainers</artifactId>
    <version>1.17.6</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>postgresql</artifactId>
    <version>1.17.6</version>
    <scope>test</scope>
</dependency>
```

---

## 🎯 Decision Matrix

**When to create QueryRepository?** → Yes, if custom `@Query` methods > 3

**Use @Transactional on Controller?** → No, only on Service

**When to extend AuditableEntity?** → Always for domain entities

**Validate at Entity or DTO level?** → Entity for DB constraints, DTO for API input

**Single or separate Repository methods?** → Separate if different business contexts

**Use service in controller directly?** → Yes, never bypass service layer

**Create validator class?** → Yes, if validation logic > 20 lines or reused

---

## 📞 Troubleshooting

| Issue | Solution |
|-------|----------|
| `LazyInitializationException` | Use `@Transactional(readOnly = true)` or fetch strategy EAGER |
| `ValidationException` not caught | Ensure `@Valid` on request DTO + `GlobalExceptionHandler` |
| Flyway migration fails | Check SQL syntax, verify no duplicate version numbers, test locally |
| Entity not persisting | Ensure `@Transactional` on service method |
| Circular dependency error | Check imports; use interfaces to break cycles |
| Test fails with `@SpringBootTest` | Verify `application-test.yml` exists, Testcontainers running |

---

## 🔗 Quick Links

- **Architecture Details**: `SPRING_BOOT_3_ARCHITECTURE.md` (sections 1-20)
- **Folder Structure**: `SPRING_BOOT_3_FOLDER_TREE.md`
- **Migration Plan**: `SPRING_BOOT_3_MIGRATION.md`
- **Summary**: `SPRING_BOOT_3_ARCHITECTURE_SUMMARY.md`
- **Analysis**: `DOMAIN_DEPENDENCY_ANALYSIS.md`
- **Assessment**: `MODERNIZATION_REPORT.md`

---

## ⏱️ Time Estimates per Phase

| Phase | Entities | Effort | Duration |
|-------|----------|--------|----------|
| 1 | 4 (ref) | Medium | 1-2 weeks |
| 2 | 3 (master) | Medium | 1-2 weeks |
| 3 | 1 (product + enum) | High | 2-3 weeks |
| 4 | 1 (stock) | Medium | 1-2 weeks |
| 5 | 1 (order) | High | 2-3 weeks |
| 6 | Reporting | Medium | 1-2 weeks |

**Total**: 10-15 weeks (varies by team size, parallelization)

---

**Last Updated**: 2026-06-10  
**Print this page for quick reference during implementation!** 📍

