# MicroserviceModule consolidation plan

## Goal

Turn `MicroserviceModule` from one ambiguous demonstration JAR into a small, consistent Maven service suite in which each deployable application:

- has one entry point and one artifact;
- owns only its required dependencies and configuration;
- can build, test, run, and deploy independently;
- uses consistent package, API, observability, and documentation conventions;
- shares code only through an intentional, narrow contract.

This plan preserves the demonstrations. It changes their boundaries and operating model rather than rewriting all behavior at once.

## Recommended target

```text
MicroserviceModule/                         # packaging=pom; aggregator only
├── pom.xml
├── README.md
├── course-service/
│   ├── pom.xml
│   └── src/{main,test}/...
├── jewellery-service/
│   ├── pom.xml
│   └── src/{main,test}/...
├── stock-service/
│   ├── pom.xml
│   └── src/{main,test}/...
├── shopping-service/
│   ├── pom.xml
│   └── src/{main,test}/...
└── support/
    ├── observability-starter/              # only if at least two services need identical setup
    └── aws-s3-adapter/                     # only after a service owner is identified
```

Use packages such as `com.lekhraj.microservices.course` and `com.lekhraj.microservices.stock`. Keep the `@SpringBootApplication` at each package root so default component scanning is sufficient.

Do not create a broad `common` module. Shared DTOs, entities, and business services would couple releases. Extract a support module only when two consumers have the same stable infrastructure need.

## Dependency ownership

Move dependencies out of the repository root's `<dependencies>` section. The root should aggregate modules and use `<dependencyManagement>` / `<pluginManagement>` for versions and conventions.

| Target module | Direct dependency groups |
|---|---|
| `course-service` | Spring Web, Validation, Data JPA, H2, OpenAPI, MapStruct, Lombok, Actuator, Test |
| `jewellery-service` | Spring Web, Validation, OpenAPI, Lombok, Actuator, Test; AWS S3 only if this service owns that endpoint |
| `stock-service` | Spring Web, OpenAPI, Actuator, Test |
| `shopping-service` | Spring Web, Validation, Data JPA, PostgreSQL, OpenAPI, Lombok, Actuator, Testcontainers PostgreSQL |
| `observability-starter` | Actuator and Prometheus registry, with auto-configuration rather than an application entry point |
| `aws-s3-adapter` | AWS SDK S3 and focused tests |

RabbitMQ, Avro, gRPC, Batch, OkHttp EventSource, and protobuf generation have no active implementation in this module. Remove them from these service classpaths until a feature needs them. This also prevents the protobuf plugin from running where no `.proto` files exist.

## Migration map

| Current path | Target | Notes |
|---|---|---|
| `basicWebApp/courseApp` | `course-service` | Correct repository/entity scanning and retain H2 only in local/test profiles |
| `basicWebApp/jewelleryApp` | `jewellery-service` | Keep HTTP/API demonstrations together initially |
| `modernWebApp/stockApp` | `stock-service` | Replace raw thread creation with a managed executor in a later behavior change |
| `modernWebApp/shoppingApp` | `shopping-service` | Add externalized PostgreSQL properties and repository integration tests |
| `observabilityApp` | Each service or `observability-starter` | Actuator may make the custom counter config unnecessary; decide from a concrete metric requirement |
| `aws/storage/s3` | Owning service or `aws-s3-adapter` | Do not activate globally; identify its API owner first |
| `util/Print.java` | Delete, keep local, or test utility | Avoid publishing a module for a single console helper |

## Phased execution

### Phase 0 — Establish a baseline

1. Pin one supported Java version in the root POM. Choose Java 21 for the least migration risk, or upgrade Spring Boot and dependencies before choosing Java 25.
2. Record the effective POM and dependency tree for `MicroserviceModule`.
3. Add one `@SpringBootTest` context test per application, using isolated properties.
4. Add MockMvc smoke tests for the principal controller groups.
5. For Course and Shopping, add repository tests that prove entity and repository scanning.
6. Make the baseline build run in CI.

Exit criteria: every intended application either starts in a test context or has a documented, reproducible failure. No move begins with an unknown baseline.

### Phase 1 — Repair current runtime boundaries

1. Make package declarations and `@EnableJpaRepositories` / entity scan targets agree.
2. Fix the Shopping properties filename case.
3. Define Shopping PostgreSQL settings through environment-backed configuration properties; do not commit credentials.
4. Decide which service owns the S3 endpoint. Move it under that application package temporarily or explicitly import it.
5. Integrate observability consistently through Actuator configuration.
6. Give each app a unique management/API port only where separate ports are actually required.

Exit criteria: all four apps pass context and smoke tests from the existing single module.

### Phase 2 — Split the Maven artifact

1. Change `MicroserviceModule/pom.xml` to `packaging=pom` and declare four child modules.
2. Move one low-risk app first: `stock-service` is the best pilot because it has no database and only four Java files.
3. Give the new module its own dependencies, `application.yml`, Boot plugin main class, and tests.
4. Repeat with Jewellery, Course, then Shopping in increasing persistence complexity.
5. Build both the moved module and the remaining legacy source during each small migration; avoid a single large move.

Exit criteria: `mvn -pl MicroserviceModule verify` produces four unambiguous executable artifacts and runs all service tests.

### Phase 3 — Standardize configuration and operations

For each service:

- replace custom `spring.config.location` setup with `application.yml`;
- add `application-local.yml` and `application-test.yml` where useful;
- use environment placeholders for database and cloud settings;
- expose Actuator health, info, and Prometheus endpoints intentionally;
- add an OpenAPI title/version and document the base URL;
- add a Dockerfile only after the executable JAR is deterministic;
- provide local dependencies through one Compose file at the suite level;
- define readiness/liveness behavior before adapting the existing Helm deployment.

Exit criteria: a new developer can start each service from its README without editing tracked files.

### Phase 4 — Improve behavior behind stable APIs

1. Replace field injection with constructor injection.
2. Move Spring MVC annotations out of service-layer method signatures.
3. Return typed response models and appropriate HTTP statuses instead of status strings.
4. Add centralized, service-appropriate error responses.
5. Replace raw `new Thread(...)` streaming work with a managed `TaskExecutor`; define timeout and disconnect handling.
6. Resolve Lombok builder defaults and MapStruct unmapped fields rather than suppressing warnings globally.
7. Add database migrations (Flyway or Liquibase) before using PostgreSQL beyond a demo.
8. Add Testcontainers only to services with external infrastructure.

Exit criteria: compiler warnings are reviewed, critical API paths have tests, and services shut down cleanly.

### Phase 5 — CI and deployment

1. Detect changed modules and run their tests, while retaining a periodic full build.
2. Build and tag one image per service.
3. Generate an SBOM and run dependency/security scanning per artifact.
4. Parameterize the Helm chart with an explicit service image, port, context path, probes, resources, and secrets.
5. Deploy Stock first, then Jewellery, Course, and Shopping.

Exit criteria: each service is independently releasable and rollback does not require rebuilding unrelated services.

## Test strategy

Use a small test pyramid per service:

| Layer | Minimum coverage |
|---|---|
| Unit | Business calculations, validation, mapping, and error branches |
| Web slice | Route, request validation, content type, status, and response schema |
| Persistence slice | Entity mappings, queries, transactions, and migrations |
| Context | One application startup test with test configuration |
| Integration | PostgreSQL via Testcontainers for Shopping; S3-compatible fixture or mocked SDK for the S3 adapter |
| Architecture | Package/module dependency rules after package normalization |

Streaming tests should verify content type, early data delivery, termination, and executor cleanup without waiting for all production delays.

## Proposed configuration contract

Prefer standard property names and typed `@ConfigurationProperties`. A Shopping local profile could use this shape:

```yaml
spring:
  application:
    name: shopping-service
  datasource:
    url: ${SHOPPING_DB_URL:jdbc:postgresql://localhost:5432/shopping}
    username: ${SHOPPING_DB_USER:shopping}
    password: ${SHOPPING_DB_PASSWORD:shopping}
  jpa:
    hibernate:
      ddl-auto: validate

server:
  port: ${SERVER_PORT:8085}
  servlet:
    context-path: /shopping
```

Local defaults are acceptable for disposable development infrastructure. Production secrets must come from the runtime secret provider.

## Risks and controls

| Risk | Control |
|---|---|
| Package moves silently change component scanning | Add context and slice tests before moving files |
| API paths change during cleanup | Capture existing paths in MockMvc tests and version intentional changes |
| JPA behavior differs between H2 and PostgreSQL | Use PostgreSQL Testcontainers for Shopping and keep H2 scoped to Course demos |
| Shared module becomes a coupling point | Extract only stable infrastructure with at least two real consumers |
| Large Maven split is difficult to review | Pilot Stock, then migrate one service per change |
| Root POM cleanup breaks unrelated repository modules | Move dependencies module-by-module and run the full reactor after each group |
| Java 25/library incompatibility appears later | Align the declared and effective Java release and test it in CI |

## Definition of done

Consolidation is complete when:

- four service artifacts each have exactly one main class;
- the root and `MicroserviceModule` aggregators contain no application dependencies;
- each service passes unit, context, and relevant integration tests;
- every dependency and plugin has an active owner;
- configuration is externally overridable with no committed production secrets;
- AWS and observability code is either owned and tested or removed/quarantined;
- service READMEs contain purpose, prerequisites, run/test commands, ports, primary endpoints, and dependencies;
- CI and deployment operate per service.

## Recommended first implementation slice

The next change should remain small and reversible:

1. Align the effective Java release.
2. Add a Stock context test and controller streaming tests.
3. Create `stock-service` as the pilot child module.
4. Move only Stock code/resources and its direct dependencies.
5. Verify the Stock executable JAR and the full reactor.

That slice proves the target layout without involving database migration or changing the other applications.
