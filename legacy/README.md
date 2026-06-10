# Legacy Application — Hibernate CLI (Reference Only)

**Status**: Reference only — NOT compiled, NOT executed  
**Original technology**: Java 11, Hibernate 5.4, MySQL, Swing/Console UI  
**Baseline Git tag**: `legacy-hibernate-baseline`  

---

## What is this?

This directory contains the original Store Management System application before
the Spring Boot 3 modernization. It was a standalone CLI application using
manual Hibernate session management, MySQL, and a console menu-driven UI.

It is preserved here as read-only reference material for the ongoing migration.

---

## This code is NOT part of the Maven build

The `legacy/` directory is **outside** the Maven source roots:
- `src/main/java` → compiled by Maven ✅
- `legacy/src/main/java` → **ignored by Maven** ✅

Running `mvn compile`, `mvn test`, or `mvn verify` will not touch anything
inside this directory.

---

## Why is it here?

During incremental vertical-slice migration, developers need to reference:

- **Original business rules** (validation logic in `model/validation/`)
- **Entity structures** (field names, types, JPA relationships in `model/`)
- **Repository query methods** (what queries the legacy app ran)
- **Service business logic** (what operations existed)
- **Seed data format** (`data/*.txt` files used by DataManager)

Having this directory in the same Git repository means:
- No separate checkout required
- Full Git history accessible (`git log --follow`, `git blame`)
- IntelliJ can open files for reading without IDE errors (mark as Excluded)

---

## Directory contents

```
legacy/
├── README.md                          ← this file
├── src/
│   ├── main/
│   │   ├── java/jan/stefan/hibernate/ ← 105 legacy Java source files
│   │   │   ├── App.java               ← CLI entry point (manual DI wiring)
│   │   │   ├── connection/            ← Singleton EntityManagerFactory
│   │   │   ├── dataInDbValidation/    ← Cross-entity database validators
│   │   │   ├── dto/                   ← Flat DTOs and statistic projections
│   │   │   ├── exceptions/            ← MyException wrapper
│   │   │   ├── menu/                  ← Console UI (MenuPanel, MenuService, MenuStatistics)
│   │   │   ├── model/                 ← JPA entities (11 entities + enums + validators)
│   │   │   ├── repository/            ← Manual JPA repository layer
│   │   │   └── service/               ← Business services + data generators + JSON converters
│   │   └── resources/META-INF/
│   │       └── persistence.xml        ← Legacy JPA config (MySQL, HBN unit, hbm2ddl=update)
│   └── test/
│       └── java/jan/stefan/hibernate/ ← 4 legacy test files (JUnit 5 + Mockito)
└── data/                              ← Seed data text files (countries, names, etc.)
```

---

## Reference map: Legacy → New Spring Boot 3

| Legacy class | New class |
|---|---|
| `model/Country.java` | `domain/reference/country/entity/Country.java` |
| `model/validation/CountryValidation.java` | service validators + `@Valid` DTO constraints |
| `repository/repositoryInterfaces/CountryRepository.java` | `domain/reference/country/repository/CountryRepository.java` |
| `repository/implementation/CountryRepositoryImpl.java` | **deleted** — Spring Data auto-generates |
| `service/CountryService.java` | `domain/reference/country/service/CountryServiceImpl.java` |
| `dto/modelDto/CountryDto.java` | `domain/reference/country/dto/CountryResponseDto.java` |
| `menu/MenuService.java` | `domain/*/controller/*Controller.java` |
| `connection/DbConnection.java` | **deleted** — Spring Boot manages datasource |
| `exceptions/MyException.java` | `core/exception/ApiException.java` + subclasses |

---

## How to read legacy code during migration

For each entity being migrated, read files in this order:

1. `src/main/java/jan/stefan/hibernate/model/{Entity}.java`  
   → Field names, types, JPA annotations, relationships

2. `src/main/java/jan/stefan/hibernate/model/validation/{Entity}Validation.java`  
   → Business constraints → becomes `@Valid` annotations and service validators

3. `src/main/java/jan/stefan/hibernate/repository/repositoryInterfaces/{Entity}Repository.java`  
4. `src/main/java/jan/stefan/hibernate/repository/implementation/{Entity}RepositoryImpl.java`  
   → What query methods existed → becomes derived Spring Data methods

5. `src/main/java/jan/stefan/hibernate/service/{Entity}Service.java`  
   → Business logic to transcribe into new `*ServiceImpl.java`

6. `src/main/java/jan/stefan/hibernate/menu/MenuService.java` (search for entity)  
   → What CLI operations existed → these become REST endpoints

---

## Do NOT

- ❌ Edit any files in this directory
- ❌ Add files to this directory
- ❌ Reference these classes as compile-time dependencies
- ❌ Run the legacy application against the new PostgreSQL database  
  (It was built for MySQL with `hbm2ddl.auto=update` — it would corrupt the Flyway-managed schema)

---

## Recovering the exact original state

The pre-migration state is preserved as a Git tag:

```bash
# View the original codebase
git show legacy-hibernate-baseline

# Check out the original state to a separate directory
git worktree add ../legacy-app legacy-hibernate-baseline

# View a specific file from the original state
git show legacy-hibernate-baseline:src/main/java/jan/stefan/hibernate/model/Country.java
```

---

## When this directory will be deleted

After all 6 migration phases are complete and the team confirms no further
reference lookups are needed. See `DOMAIN_DEPENDENCY_ANALYSIS.md` for phase
status and completion criteria.

Migration phases:
- [ ] Phase 1: Country, Category, Trade, Payment
- [ ] Phase 2: Customer, Producer, Shop
- [ ] Phase 3: Product
- [ ] Phase 4: Stock
- [ ] Phase 5: Order
- [ ] Phase 6: Reporting

---

**Placed here**: 2026-06-10  
**Contact**: See `REPOSITORY_ORGANIZATION.md` for full rationale and migration strategy

