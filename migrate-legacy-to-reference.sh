#!/usr/bin/env bash
# =============================================================================
# migrate-legacy-to-reference.sh
#
# PURPOSE:
#   Moves the legacy Hibernate CLI code out of the Maven source roots
#   into legacy/ (not compiled, read-only reference).
#
#   Fixes a CRITICAL BUILD FAILURE:
#   The new pom.xml (Spring Boot 3.5 / Jakarta EE 10) is incompatible with
#   the legacy code (javax.persistence, mysql-connector-java, Hibernate Session API).
#   Maven cannot compile the project until the legacy source tree is moved.
#
# WHAT IT DOES:
#   1. Tags the current state as "legacy-hibernate-baseline" in Git
#   2. Uses git mv to move all legacy source files (preserves Git history)
#   3. Moves legacy data files (*.txt) to legacy/data/
#   4. Verifies the Maven build passes after the move
#
# WHAT IT DOES NOT DO:
#   - It does NOT modify any business class content
#   - It does NOT delete anything
#   - It does NOT change pom.xml
#
# PREREQUISITES:
#   - Git repository initialized
#   - Run from the repository root (where pom.xml is)
#   - No uncommitted changes (the script will abort if there are)
#
# USAGE:
#   chmod +x migrate-legacy-to-reference.sh
#   ./migrate-legacy-to-reference.sh
#
# =============================================================================

set -euo pipefail

REPO_ROOT="$(git rev-parse --show-toplevel)"
cd "$REPO_ROOT"

# ─── Pre-flight checks ───────────────────────────────────────────────────────

echo "=== Pre-flight checks ==="

# Ensure we are inside a Git repository
if ! git rev-parse --git-dir > /dev/null 2>&1; then
  echo "ERROR: Not inside a Git repository. Aborting."
  exit 1
fi

# Ensure there are no uncommitted changes (we need a clean state to tag)
if ! git diff --quiet || ! git diff --cached --quiet; then
  echo "ERROR: You have uncommitted changes."
  echo "       Please commit or stash before running this script."
  git status --short
  exit 1
fi

echo "✓ Git repository clean"

# ─── Step 1: Tag the current state ───────────────────────────────────────────

echo ""
echo "=== Step 1: Tagging pre-migration state ==="

if git tag | grep -q "^legacy-hibernate-baseline$"; then
  echo "  Tag 'legacy-hibernate-baseline' already exists — skipping tag creation."
else
  git tag -a legacy-hibernate-baseline \
    -m "Last state of legacy Hibernate CLI app before Spring Boot 3 modernization.

Technology: Java 11, Hibernate 5.4, MySQL, Swing/Console UI
Package root: jan.stefan.hibernate
Entry point: src/main/java/jan/stefan/hibernate/App.java
Source files: 105 Java files

This tag preserves the exact runnable state of the original application.
To view: git show legacy-hibernate-baseline
To checkout: git worktree add ../legacy-app legacy-hibernate-baseline"

  echo "✓ Tagged as 'legacy-hibernate-baseline'"
  echo "  To push: git push origin legacy-hibernate-baseline"
fi

# ─── Step 2: Move legacy main Java sources ───────────────────────────────────

echo ""
echo "=== Step 2: Moving legacy main Java sources ==="

SRC_MAIN_JAVA="src/main/java/jan"
LEGACY_MAIN_JAVA="legacy/src/main/java/jan"

if [ -d "$SRC_MAIN_JAVA" ]; then
  git mv "$SRC_MAIN_JAVA" "$LEGACY_MAIN_JAVA"
  echo "✓ Moved $SRC_MAIN_JAVA → $LEGACY_MAIN_JAVA"
else
  echo "  $SRC_MAIN_JAVA does not exist (already moved?) — skipping."
fi

# ─── Step 3: Move legacy test Java sources ───────────────────────────────────

echo ""
echo "=== Step 3: Moving legacy test Java sources ==="

SRC_TEST_JAVA="src/test/java/jan"
LEGACY_TEST_JAVA="legacy/src/test/java/jan"

if [ -d "$SRC_TEST_JAVA" ]; then
  git mv "$SRC_TEST_JAVA" "$LEGACY_TEST_JAVA"
  echo "✓ Moved $SRC_TEST_JAVA → $LEGACY_TEST_JAVA"
else
  echo "  $SRC_TEST_JAVA does not exist (already moved?) — skipping."
fi

# ─── Step 4: Move persistence.xml OUT of Maven classpath ─────────────────────

echo ""
echo "=== Step 4: Moving persistence.xml out of Maven classpath ==="

PERSISTENCE_SRC="src/main/resources/META-INF/persistence.xml"
PERSISTENCE_DST="legacy/src/main/resources/META-INF/persistence.xml"

if [ -f "$PERSISTENCE_SRC" ]; then
  git mv "$PERSISTENCE_SRC" "$PERSISTENCE_DST"
  echo "✓ Moved $PERSISTENCE_SRC → $PERSISTENCE_DST"
  echo "  (Eliminates Spring Boot JPA auto-configuration conflict)"
else
  echo "  $PERSISTENCE_SRC does not exist (already moved?) — skipping."
fi

# ─── Step 5: Move seed data *.txt files ──────────────────────────────────────

echo ""
echo "=== Step 5: Moving legacy seed data files ==="

DATA_FILES=(
  "src/category.txt"
  "src/countries.txt"
  "src/names.txt"
  "src/surnames.txt"
  "src/trade.txt"
  "src/producerseElectronics.txt"
  "src/producersFurniture.txt"
  "src/producersMachines.txt"
  "src/producersSport.txt"
  "src/producersTools.txt"
)

for f in "${DATA_FILES[@]}"; do
  if [ -f "$f" ]; then
    filename=$(basename "$f")
    git mv "$f" "legacy/data/$filename"
    echo "✓ Moved $f → legacy/data/$filename"
  else
    echo "  $f does not exist — skipping."
  fi
done

# Also check root-level duplicates (same names at project root)
ROOT_TXT_FILES=(
  "category.txt"
  "countries.txt"
  "names.txt"
  "surnames.txt"
  "trade.txt"
  "producerseElectronics.txt"
  "producersFurniture.txt"
  "producersMachines.txt"
  "producersSport.txt"
  "producersTools.txt"
  "initial-customer-data.json"
  "countries.json"
)

for f in "${ROOT_TXT_FILES[@]}"; do
  if [ -f "$f" ]; then
    filename=$(basename "$f")
    # Avoid overwriting if already exists
    if [ ! -f "legacy/data/$filename" ]; then
      git mv "$f" "legacy/data/$filename"
      echo "✓ Moved root-level $f → legacy/data/$filename"
    else
      echo "  legacy/data/$filename already exists — leaving $f in place"
    fi
  fi
done

# ─── Step 6: Commit the reorganization ───────────────────────────────────────

echo ""
echo "=== Step 6: Committing the reorganization ==="

git add -A

if git diff --cached --quiet; then
  echo "  Nothing to commit — all moves may have been already applied."
else
  git commit -m "chore: move legacy Hibernate code to legacy/ (not a Maven source root)

The new pom.xml targets Spring Boot 3.5 + Java 21 + Jakarta EE 10.
The legacy code is incompatible with the new classpath:

  javax.persistence  →  REMOVED (Spring Boot 3 uses jakarta.persistence)
  mysql-connector    →  REMOVED (replaced by postgresql driver)
  Hibernate Session  →  REMOVED (replaced by Spring Data JPA)
  com.google.gson    →  REMOVED (Jackson is used instead)
  jaxb-api           →  REMOVED

The legacy/ directory is NOT referenced in pom.xml.
Maven ignores it completely. It is kept as read-only reference.

Files moved:
  src/main/java/jan/                      → legacy/src/main/java/jan/
  src/test/java/jan/                      → legacy/src/test/java/jan/
  src/main/resources/META-INF/persistence.xml → legacy/src/main/resources/...
  src/*.txt                               → legacy/data/

See REPOSITORY_ORGANIZATION.md for full rationale and migration strategy."

  echo "✓ Committed reorganization"
fi

# ─── Step 7: Verify the build ────────────────────────────────────────────────

echo ""
echo "=== Step 7: Verifying Maven build ==="
echo "  Running: mvn clean compile -q"
echo "  (This MUST succeed — if it fails, check for remaining legacy references)"
echo ""

if mvn clean compile -q; then
  echo ""
  echo "✓ mvn compile: PASSED"
else
  echo ""
  echo "✗ mvn compile: FAILED"
  echo ""
  echo "  Possible causes:"
  echo "  1. Some legacy files were not moved (check src/main/java/ for remaining .java files)"
  echo "  2. The new StoreManagementApplication.java has an import error"
  echo "  3. A dependency is missing from pom.xml"
  echo ""
  echo "  Diagnostic command:"
  echo "    find src/main/java -name '*.java' | head -20"
  exit 1
fi

# ─── Done ────────────────────────────────────────────────────────────────────

echo ""
echo "════════════════════════════════════════════════════════════════"
echo " MIGRATION COMPLETE"
echo "════════════════════════════════════════════════════════════════"
echo ""
echo " ✓ Legacy code preserved in legacy/ (Git history intact)"
echo " ✓ persistence.xml removed from Maven classpath"
echo " ✓ Maven build passes (mvn compile)"
echo " ✓ Git tag: legacy-hibernate-baseline"
echo ""
echo " NEXT STEPS:"
echo "   1. Push the tag:       git push origin legacy-hibernate-baseline"
echo "   2. Push the commit:    git push"
echo "   3. Mark legacy/ as Excluded in IntelliJ IDEA:"
echo "      File → Project Structure → Modules → Sources tab"
echo "      Right-click legacy/ → Mark Directory As → Excluded"
echo "   4. Begin Phase 1 implementation:"
echo "      See DOMAIN_DEPENDENCY_ANALYSIS.md → Phase 1"
echo "      See SPRING_BOOT_3_QUICK_REFERENCE.md for patterns"
echo ""

