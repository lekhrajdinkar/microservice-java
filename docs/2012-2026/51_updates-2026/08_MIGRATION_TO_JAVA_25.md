Migration summary — upgrade to Java 25

What I changed

- Root POM (`pom.xml`)
  - Set `<java.version>` and `<maven.java.version>` to `25`.
  - Configured `maven-compiler-plugin`:
    - `<release>25</release>`
    - `<useModulePath>false</useModulePath>`
    - Excluded `module-info.java` from compilation
    - Registered `annotationProcessorPaths` for Lombok and MapStruct
  - Bumped Lombok to `1.18.40`.
  - Removed non-existent modules from the `<modules>` list (`module1`, `module2`).

- Modules
  - Updated per-module `pom.xml` files that specified `maven.compiler.source/target` to `25`.
  - Added dependency from `SpringBootModule` to `JavaEvolutionModule` to resolve internal imports.

- Code fixes
  - Removed `module-info.java` files that caused JPMS resolution issues.
  - Corrected import in `SpringBootModule/src/main/java/SpringbootApp.java`.
  - Fixed topic names in `KafkaModule/src/test/java/kafka/spring/StudentStreamsTopologyTest.java` to match topology.
  - Created missing directory `WebSecurityModule/src/main/resources/avro` for Avro plugin.

Build & verification

- Verified Java runtime: `mvn -v` reported Java 25 on PATH.
- Ran incremental builds and resolved compilation failures.
- Final build command used:

```
mvn "-Dspring-boot.repackage.skip=true" clean package
```

- Result: BUILD SUCCESS; unit tests executed where present.

Notes & recommendations

- Review any deprecated API warnings and MapStruct unmapped target warnings shown during build; these are not blockers but worth addressing.
- If you use JPMS modules intentionally, reintroduce `module-info.java` files and update POMs to enable module-path compilation (requires more careful dependency module-info metadata).
- Consider pinning plugin versions (compiler, surefire, surefire providers) in the root POM for reproducible builds.

If you want, I can:

- Create a git branch and commit these changes.
- Open a PR description with the migration summary.
- Re-enable repackaging and produce runnable fat-jars for each Spring Boot module.

/ End of migration summary
