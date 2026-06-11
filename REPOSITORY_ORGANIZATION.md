# Repository Organization Strategy

**Date**: 2026-06-10  
**Analysis basis**: Full source scan of 105 legacy files + 1 new Spring Boot file  
**Status**: Repository is currently **build-broken**. Action required before any new code is written.

---

## Current Repository State (Diagnostic)

### File counts

| Location | Files | Status |
|----------|-------|--------|
| `src/main/java/jan/stefan/hibernate/` | 105 | Legacy – **will not compile** against new `pom.xml` |
| `src/main/java/io/github/stefanjp/storemanagement/` | 1 | New Spring Boot – compiles |
| `src/test/java/jan/stefan/hibernate/` | 4 | Legacy tests – **will not compile** |
| `src/test/java/io/github/stefanjp/storemanagement/` | 0 | New tests – not yet written |
| `src/main/resources/META-INF/persistence.xml` | 1 | Legacy JPA config – **active runtime conflict** |
| `src/*.txt` (data files) | 10 | Seed data – misplaced inside Maven source root |

### Why the build is broken right now

Running `mvn compile` fails immediately. The new `pom.xml` replaced all legacy dependencies with Spring Boot 3 equivalents, which are **binary-incompatible** with the legacy code:

| Legacy code uses | Status in new `pom.xml` | Impact |
|-----------------|------------------------|--------|
| `javax.persistence.*` | **Removed** — Spring Boot 3 uses `jakarta.persistence.*` | 46 import references fail |
| `com.google.gson.*` | **Removed** entirely | 5 import references fail |
| `com.mysql.cj.*` | **Removed** — replaced by PostgreSQL driver | 1 import reference fails |
| `org.hibernate.Session`, `SessionFactory`, `Transaction` | **API changed** — direct Session API removed in Spring Data JPA | 9 import references fail |
| `net.bytebuddy.*` | **No longer transitive** | 1 import reference fails |

**Total**: the compiler sees 62+ unresolvable symbols in the legacy package. The project cannot produce a JAR until this is addressed.

### Additional runtime conflicts (even if compilation were fixed)

1. **`META-INF/persistence.xml` in `src/main/resources`**  
   Spring Boot JPA auto-configuration detects this file on the classpath and attempts to use it as a JPA persistence unit configuration. The `persistence.xml` references MySQL (`com.mysql.jdbc.Driver`) and the persistence unit `"HBN"` — which does not exist in the new stack. Spring Boot's `LocalContainerEntityManagerFactoryBean` will conflict with this, causing context startup failure.

2. **11 legacy `@Entity` classes with `javax.persistence.@Entity`**  
   These would not be picked up by Hibernate 6's entity scanner (which uses `jakarta.persistence`), but their presence creates confusion and potential classloading warnings.

3. **Duplicate table names**  
   Legacy entities map to tables with the exact same names as the new Phase 1 migration (`countries`, `categories`, `trades`, `payments`). If the legacy code were ever compiled and pointed at the same database, it would collide.

---

## 1) Recommended Repository Structure

```
StoreManagementSystem/                          ← Git repository root
│
├── pom.xml                                     ← Spring Boot 3.5 Maven build (new)
├── .gitignore
├── README.md
│
│   ── Documentation ──────────────────────────────────────────────
├── DOMAIN_DEPENDENCY_ANALYSIS.md
├── MODERNIZATION_REPORT.md
├── SPRING_BOOT_3_ARCHITECTURE.md
├── SPRING_BOOT_3_FOLDER_TREE.md
├── SPRING_BOOT_3_MIGRATION.md
├── SPRING_BOOT_3_ARCHITECTURE_SUMMARY.md
├── SPRING_BOOT_3_QUICK_REFERENCE.md
├── SPRING_INITIALIZR_CONFIG.md
├── README_ARCHITECTURE_INDEX.md
│
│   ── Active Spring Boot 3 Application ───────────────────────────
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── io/github/stefanjp/storemanagement/     ← NEW root package
│   │   │       ├── StoreManagementApplication.java
│   │   │       ├── config/
│   │   │       ├── core/
│   │   │       └── domain/reference/
│   │   │           ├── country/{entity,repository,service,controller,dto}
│   │   │           ├── category/{entity,repository,service,controller,dto}
│   │   │           ├── trade/{entity,repository,service,controller,dto}
│   │   │           └── payment/{entity,repository,service,controller,dto}
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       └── db/migration/
│   │           └── V1__create_reference_tables.sql
│   └── test/
│       ├── java/io/github/stefanjp/storemanagement/
│       │   ├── integration/
│       │   └── unit/
│       └── resources/
│           └── application-test.yml
│
│   ── Legacy Reference Material (NOT compiled) ──────────────────
└── legacy/
    ├── README.md                               ← Explains what this is and when to read it
    ├── src/
    │   ├── main/
    │   │   ├── java/jan/stefan/hibernate/      ← 105 legacy source files (read-only reference)
    │   │   │   ├── App.java
    │   │   │   ├── connection/
    │   │   │   ├── dataInDbValidation/
    │   │   │   ├── dto/
    │   │   │   ├── exceptions/
    │   │   │   ├── menu/
    │   │   │   ├── model/
    │   │   │   ├── repository/
    │   │   │   └── service/
    │   │   └── resources/
    │   │       └── META-INF/
    │   │           └── persistence.xml         ← Moved OUT of Maven classpath
    │   └── test/
    │       └── java/jan/stefan/hibernate/      ← 4 legacy test files
    │           ├── AppTest.java
    │           ├── CustomerServiceTest.java
    │           ├── MyOrderTest.java
    │           └── StatisticsTest.java
    └── data/                                   ← Seed data files (were in src/)
        ├── countries.txt
        ├── names.txt
        ├── surnames.txt
        ├── category.txt
        ├── trade.txt
        ├── producersElectronics.txt
        ├── producersFurniture.txt
        ├── producersMachines.txt
        ├── producersSport.txt
        └── producersTools.txt
```

---

## 2) Where Should the Legacy Code Go?

### Option A — Move to `legacy/` at the project root ✅ **RECOMMENDED**

**What it means**: Create a `legacy/` directory at the Git repository root. Move all `jan.stefan.hibernate` source trees and the `META-INF/persistence.xml` into it. The `legacy/` directory is **not a Maven module** and is **not referenced in `pom.xml`**. Maven ignores it completely.

**Why this is correct**:
- Maven only compiles what is inside `<sourceDirectory>` (defaults to `src/main/java`) and `<testSourceDirectory>` (defaults to `src/test/java`). Moving files to `legacy/src/main/java` puts them outside both roots.
- Files remain in the same Git repository. Full history (`git log`, `git blame`) is preserved on every file.
- IntelliJ IDEA will show the `legacy/` directory in the Project tree. Developers can open, read, and search legacy files without IDE errors (mark the directory as "Excluded" or "Not a Source Root" to suppress compiler warnings).
- The `persistence.xml` is no longer on the Maven classpath, eliminating the Spring Boot JPA auto-configuration conflict.
- No `pom.xml` changes required.
- The directory name makes intent explicit: "this is the old application, kept for reference."

**Limitations**:
- Legacy code is not executable anymore (can't run the old CLI app from this repository). If you need to run it, the original commit is still in Git history.
- IntelliJ may still index legacy files and show errors in them unless the directory is excluded from compilation.

---

### Option B — Move to `src/legacy/`

**What it means**: Create `src/legacy/java/` and `src/legacy/resources/` alongside `src/main/` and `src/test/`. Maven does not recognize `src/legacy/` as a source root unless explicitly configured.

**Why it's inferior to Option A**:
- The `src/` tree conventionally means "build artifacts". Having a non-compiled subtree inside `src/` confuses new team members and most IDEs.
- Some IDE plugins auto-scan all directories under `src/` and may still flag errors.
- No advantage over `legacy/` at the root — same Git history preservation, same Maven ignorance.

**Verdict**: Valid, but more confusing. Not recommended.

---

### Option C — Exclude via `maven-compiler-plugin` `<excludes>`

**What it means**: Keep `src/main/java/jan/` in place but add `<excludes><exclude>jan/**</exclude></excludes>` to the compiler plugin configuration.

**Why it's wrong**:
- The `jan/` package is still inside the Maven source root. IDEs compile source roots independently of Maven — IntelliJ IDEA will still try to compile these files and show errors.
- The `META-INF/persistence.xml` stays on the classpath and continues to conflict with Spring Boot JPA auto-configuration.
- Running `./mvnw` from the command line works, but IDE builds break. This inconsistency causes developer frustration.
- Makes `pom.xml` harder to read by embedding an exclusion that explains nothing about intent.

**Verdict**: Technically workable but actively misleading. Do not use.

---

### Option D — Git tag + delete from main branch

**What it means**: Create a Git tag `legacy-v1.0-hibernate` pointing to the last legacy commit. Then delete the `jan/` source tree from the `main` branch entirely. Developers who need the legacy code check out the tag.

**Why it's an option worth considering**:
- The working tree is completely clean. No legacy code anywhere in the active branch.
- `mvn compile` produces zero warnings or errors related to legacy.
- Forces developers to make a deliberate context switch to reference the legacy code.

**Why it may not suit this project**:
- The stated goal is "keep legacy as reference material." A separate Git tag requires a separate checkout or IDE window, which creates friction.
- During the early migration phases, developers frequently reference legacy business logic. The context switch overhead is high.
- If a junior developer doesn't know about the tag, they may not find the reference material at all.

**Verdict**: Best long-term hygiene once the migration is complete. Consider doing this in addition to the `legacy/` approach — tag the pre-migration state now, then move files to `legacy/`.

---

### Option E — Do nothing (status quo)

**What it means**: Leave both `jan/` and `io/` packages in `src/main/java/`.

**Why it's not an option**:
- `mvn compile` fails. CI/CD pipeline fails. No new code can be built.
- The `persistence.xml` conflict causes Spring Boot context startup failure.
- This is the only option that makes the project actively non-functional.

**Verdict**: Not viable. The current state is broken and must be resolved before Phase 1 implementation begins.

---

## 3) Exact Migration Strategy

### Step 1: Fix the Build (Before writing a single new line of business code)

This is a prerequisite. Without completing Step 1, Phase 1 development cannot begin.

**Actions** (in this exact order):

```
1. Create the legacy/ directory structure:
   legacy/
   legacy/src/
   legacy/src/main/
   legacy/src/main/java/
   legacy/src/main/resources/META-INF/
   legacy/src/test/
   legacy/src/test/java/
   legacy/data/

2. Move legacy Java sources:
   src/main/java/jan/  →  legacy/src/main/java/jan/

3. Move legacy test sources:
   src/test/java/jan/  →  legacy/src/test/java/jan/

4. Move legacy JPA configuration:
   src/main/resources/META-INF/persistence.xml
       →  legacy/src/main/resources/META-INF/persistence.xml

5. Move legacy seed data files:
   src/category.txt      →  legacy/data/category.txt
   src/countries.txt     →  legacy/data/countries.txt
   src/names.txt         →  legacy/data/names.txt
   src/surnames.txt      →  legacy/data/surnames.txt
   src/trade.txt         →  legacy/data/trade.txt
   src/producers*.txt    →  legacy/data/producers*.txt

6. Create legacy/README.md  (see Section 5 for content template)

7. Tag the pre-migration state in Git:
   git tag -a legacy-hibernate-baseline -m "Last state of legacy Hibernate CLI app before Spring Boot 3 migration"
   git push origin legacy-hibernate-baseline

8. Commit the reorganization:
   git add -A
   git commit -m "chore: move legacy Hibernate code to legacy/ directory (not a Maven source root)
   
   The new pom.xml targets Spring Boot 3.5 + Java 21. The legacy code uses
   javax.persistence, mysql-connector-java, and direct Hibernate Session API,
   none of which are on the new classpath.
   
   legacy/ is NOT referenced in pom.xml and is NOT compiled.
   It is kept as reference material for the vertical-slice migration.
   
   Breaking changes in new pom.xml:
   - javax.persistence -> jakarta.persistence (Spring Boot 3 / Jakarta EE 10)
   - MySQL -> PostgreSQL
   - Manual Hibernate -> Spring Data JPA
   - Standalone CLI -> Spring Boot REST API"

9. Verify the build:
   mvn clean compile   ← must succeed
   mvn test            ← must succeed (no tests yet, that is expected)
```

---

### Step 2: Configure IntelliJ IDEA

After moving legacy code, tell IntelliJ IDEA that `legacy/` is not a source root:

```
File → Project Structure → Modules → (select module) → Sources tab
  → Find the legacy/ directory in the tree
  → Mark it as "Excluded"
```

This prevents IntelliJ from showing red errors on legacy files (which use `javax.persistence` that is no longer on the classpath) while still letting you open and read them as plain text.

Alternatively, add to `.idea/` configuration (if using IntelliJ's XML config):
```xml
<!-- In .iml file or module config -->
<sourceFolder url="file://$MODULE_DIR$/legacy" isTestSource="false" type="java-source" />
<!-- Mark as excluded instead of source -->
```

The simplest approach: right-click `legacy/` in the Project tree → Mark Directory As → Excluded.

---

### Step 3: Incremental Vertical Slice Migration (Phase 1 → Phase 6)

With the build fixed, proceed phase by phase. Each phase is independently deployable.

#### Per-phase workflow

```
For each entity in the phase (e.g., Country for Phase 1):

1. OPEN legacy reference:
   Read legacy/src/main/java/jan/stefan/hibernate/model/Country.java
   Read legacy/src/main/java/jan/stefan/hibernate/service/CountryService.java
   Read legacy/src/main/java/jan/stefan/hibernate/repository/
          repositoryInterfaces/CountryRepository.java
          implementation/CountryRepositoryImpl.java
   Read legacy/src/main/java/jan/stefan/hibernate/model/validation/
          CountryValidation.java
   Understand: field names, data types, validation rules, business logic.

2. IMPLEMENT the new vertical slice:
   src/main/java/io/github/stefanjp/storemanagement/domain/reference/country/
     entity/Country.java          ← Jakarta persistence, new field names, audit fields
     repository/CountryRepository.java  ← extends JpaRepository
     service/CountryService.java + CountryServiceImpl.java
     controller/CountryController.java
     dto/CountryCreateDto.java, CountryUpdateDto.java, CountryResponseDto.java

3. WRITE the Flyway migration:
   src/main/resources/db/migration/V1__create_reference_tables.sql
   (already scaffolded; verify schema matches entity)

4. WRITE tests:
   src/test/java/.../unit/domain/reference/country/CountryServiceTest.java
   src/test/java/.../integration/domain/reference/country/CountryControllerIT.java

5. RUN and verify:
   mvn verify  ← all tests pass
   curl http://localhost:8080/api/v1/countries  ← endpoint responds

6. COMMIT the phase:
   git commit -m "feat(phase-1): implement Country reference entity
   
   - JPA entity with jakarta.persistence, audit fields
   - Spring Data JpaRepository with findByNameIgnoreCase
   - Service + ServiceImpl with @Transactional
   - REST controller: POST/GET/PUT/DELETE /api/v1/countries
   - Create/Update/Response DTOs with Bean Validation
   - Flyway V1 migration (shared with Category, Trade, Payment)
   - Unit tests: CountryServiceTest
   - Integration tests: CountryControllerIT with Testcontainers"
```

#### Phase sequence (from DOMAIN_DEPENDENCY_ANALYSIS.md)

| Phase | Entities | Legacy reference path | New path |
|-------|----------|-----------------------|---------|
| 1 | Country, Category, Trade, Payment | `legacy/.../model/Country.java` etc. | `domain/reference/*/` |
| 2 | Customer, Producer, Shop | `legacy/.../model/Customer.java` etc. | `domain/master/*/` |
| 3 | Product (+ EGuarantee) | `legacy/.../model/Product.java` | `domain/catalog/product/*/` |
| 4 | Stock | `legacy/.../model/Stock.java` | `domain/catalog/inventory/*/` |
| 5 | MyOrder | `legacy/.../model/MyOrder.java` | `domain/order/*/` |
| 6 | Reporting | `legacy/.../dto/statModelDto/` | `domain/reporting/*/` |

#### When to delete legacy/

Delete the `legacy/` directory when all of the following are true:
- All 6 migration phases are complete and tested
- Every business rule from the legacy code has been documented or implemented in the new code
- The team has confirmed no more reference lookups are needed
- The legacy Git tag (`legacy-hibernate-baseline`) exists and is pushed

There is no time pressure. It is better to delete too late than too early.

---

## 4) Risks by Approach

### Option A: Move to `legacy/` at root (recommended)

| Risk | Severity | Mitigation |
|------|----------|-----------|
| IDE may still show errors on legacy files if not configured as Excluded | Low | Mark `legacy/` as Excluded in IntelliJ Project Structure |
| Developer accidentally edits legacy code thinking it will affect the build | Low | `legacy/README.md` with clear warning; `legacy/` is outside Maven source root |
| `persistence.xml` left in `src/main/resources/` by mistake | **High** | Verify with `mvn clean verify` — Spring Boot will fail to start if it's still there |
| `git mv` command changes history paths (breaks `git log --follow`) | Low | Use `git mv` (not shell `mv`) to preserve history |
| Over-reliance on legacy as "living documentation" instead of writing proper tests | Medium | Set a sunset date for `legacy/` deletion; document intent in `legacy/README.md` |

### Option B: Move to `src/legacy/`

| Risk | Severity | Mitigation |
|------|----------|-----------|
| Confusion about whether `src/legacy/` is compiled | Medium | Same as A, but harder to communicate intent |
| Some tools recursively process everything under `src/` | Low | Add explicit exclusions to plugins |

### Option C: Exclude via compiler plugin

| Risk | Severity | Mitigation |
|------|----------|-----------|
| IntelliJ compiles source roots independently — still shows 60+ errors | **High** | Cannot be fully mitigated; IDE build breaks |
| `persistence.xml` still on classpath — Spring Boot context fails at startup | **High** | Persistence.xml must still be manually moved |
| New developer adds a file to `jan/` expecting it to be compiled | Medium | No clear signal that the directory is excluded |
| Exclusion is silently removed if compiler plugin config is regenerated | Medium | Documentation required |

### Option D: Git tag + delete

| Risk | Severity | Mitigation |
|------|----------|-----------|
| Developers don't know about the tag | Medium | Document it in README.md prominently |
| Accidental deletion of the tag | Low | Push to remote with `git push origin legacy-hibernate-baseline` |
| Context-switching overhead during active migration | Medium | Acceptable; use IDE's "Open Recent" for a second window if needed |
| Loss of side-by-side comparison in IDE | Medium | Use `git show legacy-hibernate-baseline:path/to/File.java` |

### Option E: Status quo (do nothing)

| Risk | Severity | Mitigation |
|------|----------|-----------|
| `mvn compile` fails — CI/CD pipeline broken | **Critical** | No mitigation; must be resolved |
| `mvn spring-boot:run` fails at JPA context startup | **Critical** | No mitigation; must be resolved |
| Cannot write or test any Phase 1 code | **Critical** | Blocks all development |
| `persistence.xml` conflict | **Critical** | Must be removed from `src/main/resources/META-INF/` |

---

## 5) Recommended Approach for Incremental Vertical-Slice Migration

### Guiding principles

1. **One phase at a time, one entity at a time**  
   Never start Phase 2 until Phase 1 is complete, committed, and tested. Never implement two entities in the same PR unless they share a Flyway migration that cannot be split.

2. **Legacy code is read-only reference**  
   Use `legacy/` to understand business rules, validation logic, and data shapes. Never copy-paste from it directly — transcribe and modernize.

3. **The new code is the source of truth from day one**  
   Never run the legacy app against the new PostgreSQL database. The legacy app was built for MySQL with `hbm2ddl.auto=update`. Running it would corrupt the Flyway-managed schema.

4. **Flyway migration files are immutable once committed**  
   Once `V1__create_reference_tables.sql` is applied to any environment, it cannot be edited. Fix schema issues in a new `V{N+1}__fix_*.sql`.

5. **A phase is done when its tests pass, not when the code compiles**  
   Each phase requires unit tests, integration tests (with Testcontainers), and at least one end-to-end smoke test before the next phase begins.

### Incremental migration workflow per phase

```
BEFORE starting a phase:
  ✓ Previous phase committed and pushed
  ✓ mvn verify passes on main branch
  ✓ Legacy equivalent classes identified in legacy/

DURING a phase:
  ✓ Create feature branch: git checkout -b feat/phase-N-{entity}
  ✓ Read legacy implementation as reference
  ✓ Implement entity → repository → service → controller → DTO
  ✓ Write Flyway migration
  ✓ Write unit tests (service mocked)
  ✓ Write integration tests (Testcontainers)
  ✓ All tests pass: mvn verify

AFTER a phase:
  ✓ Code review
  ✓ Merge to main: git merge --no-ff feat/phase-N-{entity}
  ✓ Tag completion: git tag -a phase-N-complete -m "Phase N complete: {description}"
  ✓ Do not begin Phase N+1 until Phase N is merged
```

### How to read legacy code effectively during migration

For each entity, read the legacy files in this order:

```
1. legacy/.../model/{Entity}.java
   → Field names, types, JPA relationships, nullable constraints

2. legacy/.../model/validation/{Entity}Validation.java
   → Business rules that become @Valid annotations or service validators

3. legacy/.../repository/repositoryInterfaces/{Entity}Repository.java
4. legacy/.../repository/implementation/{Entity}RepositoryImpl.java
   → What query methods were used → becomes JpaRepository derived methods

5. legacy/.../service/{Entity}Service.java
   → Business logic to transcribe into new service implementations

6. legacy/.../menu/MenuService.java  (search for entity name)
   → API operations that were offered to the CLI user
   → These become REST endpoints
```

### Parallel structure reference map

When implementing a new Phase 1 entity, refer to this mapping:

```
legacy class                          →  new class
─────────────────────────────────────────────────────────────────
model/Country.java                    →  domain/reference/country/entity/Country.java
model/validation/CountryValidation.java→  core/validation/validators/ + service-level checks
repository/repositoryInterfaces/
  CountryRepository.java              →  domain/reference/country/repository/CountryRepository.java
                                         (interface only; extends JpaRepository)
repository/implementation/
  CountryRepositoryImpl.java          →  DELETED — Spring Data generates this automatically
service/CountryService.java           →  domain/reference/country/service/CountryServiceImpl.java
                                         (new CountryService.java is the interface)
dto/modelDto/CountryDto.java          →  domain/reference/country/dto/CountryResponseDto.java
                                         + CountryCreateDto.java
                                         + CountryUpdateDto.java
(no controller existed — was CLI menu) →  domain/reference/country/controller/CountryController.java
```

### Phase readiness checklist

Before marking a phase complete and moving to the next:

```
Entity Layer
  ☐ @Entity uses jakarta.persistence (NOT javax.persistence)
  ☐ @Table name matches Flyway migration table name exactly
  ☐ @Id @GeneratedValue(strategy = IDENTITY) present
  ☐ audit fields: created_at, updated_at populated via @EnableJpaAuditing

Repository Layer
  ☐ extends JpaRepository<Entity, Long>
  ☐ Custom query methods documented with // why this method exists
  ☐ No EntityManager injected manually

Service Layer
  ☐ Service interface + ServiceImpl separate
  ☐ @Transactional on write methods
  ☐ @Transactional(readOnly = true) on read methods
  ☐ All exceptions thrown are subclasses of ApiException (from core.exception)

Controller Layer
  ☐ @RestController + @RequestMapping("/api/v1/{resource}")
  ☐ @Operation (OpenAPI) on each endpoint
  ☐ @Valid on @RequestBody parameters
  ☐ Correct HTTP status codes: 201 Created, 204 No Content, 404, 409

DTO Layer
  ☐ CreateDto has @NotBlank / @NotNull / @Size validation
  ☐ ResponseDto has no validation annotations
  ☐ No entity references in DTOs (no circular JSON)

Flyway
  ☐ Migration file is named correctly: V{N}__description.sql
  ☐ Migration creates the exact table(s) the entities map to
  ☐ UNIQUE constraints match entity uniqueness rules
  ☐ NOT NULL constraints match @Column(nullable = false)

Tests
  ☐ Unit test: service method tested with mocked repository
  ☐ Unit test: validation edge cases covered
  ☐ Integration test: POST endpoint creates record (201)
  ☐ Integration test: GET endpoint returns record (200)
  ☐ Integration test: duplicate POST returns 409 Conflict
  ☐ Integration test: GET with invalid ID returns 404

Build
  ☐ mvn clean verify passes with zero test failures
  ☐ mvn spring-boot:run starts successfully
  ☐ /actuator/health returns UP
  ☐ /swagger-ui.html (dev profile) shows new endpoints
```

---

## 6) `legacy/README.md` — Recommended Content

Create this file as part of Step 1 to communicate intent clearly to anyone who opens the directory:

```markdown
# Legacy Application — Hibernate CLI (Reference Only)

## What is this?

This directory contains the original Store Management System application
written in Java 11 with standalone Hibernate 5.4 and a Swing/Console UI.

It was the baseline for the Spring Boot 3 modernization project.

## This code is NOT compiled

The `legacy/` directory is outside the Maven source roots (`src/main/java`).
It is kept as **read-only reference material** for the ongoing migration.

`mvn compile` ignores everything in this directory.

## Why is it here?

To allow developers to:
- Understand the original business rules and validation logic
- Compare old entity structures with new Spring Boot entities
- Verify that migration correctly captures all behaviour

## When reading legacy code, look for:

- `legacy/src/main/java/jan/stefan/hibernate/model/`  → domain entities
- `legacy/src/main/java/jan/stefan/hibernate/service/` → business logic
- `legacy/src/main/java/jan/stefan/hibernate/model/validation/` → validation rules
- `legacy/data/` → seed data used by the old DataManager

## Do not:

- Edit these files
- Add new files to this directory
- Run or execute any code in this directory
- Use these classes as compile-time dependencies

## Original baseline Git tag

The exact state of the application before modernization is preserved at:

  git show legacy-hibernate-baseline

## When this directory will be deleted

After all 6 migration phases are complete and all vertical slices have been
tested and deployed. Check `DOMAIN_DEPENDENCY_ANALYSIS.md` for phase status.
```

---

## 7) Summary Recommendation

| Action | Priority | Effort |
|--------|----------|--------|
| Create Git tag `legacy-hibernate-baseline` | **Immediate** | 2 min |
| Move `jan/` source tree to `legacy/` using `git mv` | **Immediate** | 5 min |
| Move `persistence.xml` to `legacy/` | **Immediate** | 2 min |
| Move `src/*.txt` data files to `legacy/data/` | **Immediate** | 2 min |
| Create `legacy/README.md` | Immediate | 5 min |
| Mark `legacy/` as Excluded in IntelliJ | Immediate | 1 min |
| Run `mvn clean verify` — confirm build passes | **Gate** | 1 min |
| Implement Phase 1 (Country, Category, Trade, Payment) | Phase 1 | 1-2 weeks |

**Total time to unblock development**: approximately 15 minutes.

The moment `mvn clean verify` passes after the move, Phase 1 development can begin immediately.

---

**Created**: 2026-06-10  
**Status**: Ready for action — execute Step 1 (fix the build) before any new code is written

