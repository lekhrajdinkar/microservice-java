# MicroserviceModule

`MicroserviceModule` is currently a collection of Spring Boot proof-of-concept applications packaged as one Maven JAR. It demonstrates REST APIs, JPA, response streaming, PostgreSQL/H2 integration, AWS S3 access, and Micrometer instrumentation.

It is not currently a single microservice and it is not yet a deployable microservice suite. Four independent `@SpringBootApplication` classes, unrelated dependencies, and shared source/resource directories are combined in one artifact.

For the recommended target structure and migration sequence, see [CONSOLIDATION_PLAN.md](CONSOLIDATION_PLAN.md).

## Current project inventory

| Application or area | Entry point | Port and context path | Persistence | Purpose | Current state |
|---|---|---|---|---|---|
| Course | `basicWebApp.courseApp.CourseApp` | `8082`, `/courseApp` | File-backed H2 | JPA relationships, custom repositories, mapping, pagination, transactions | Compiles; repository/entity scan packages do not match the Java packages |
| Jewellery | `basicWebApp.jewelleryApp.JewelleryApp` | `8086`, `/jewelleryApp` | None | REST, validation, serialization, API versioning, error handling, HTTP clients | Most self-contained application |
| Stock | `modernWebApp.stockApp.StockApp` | `8084`, `/stockApp` | None | Bulk JSON, downloads, NDJSON, `ResponseBodyEmitter`, and SSE | Runnable demonstration; manually creates threads for streaming |
| Shopping | `modernWebApp.shoppingApp.ShoppingApp` | `8085`, `/shoppingApp` | Intended PostgreSQL | Customer, product, cart, and order workflow | Configuration is incomplete and package scans do not match source packages |
| AWS S3 | No entry point | None | AWS S3 | Read an S3 object | Outside every application's default component scan |
| Observability | No entry point | Properties specify `8086`, `/observabilityApp` | None | Custom Micrometer counter endpoint | Outside every application's default component scan; port conflicts with Jewellery |
| Utility | No entry point | None | None | Console-print helper | Shared by location rather than by an explicit module contract |

Inventory at review time (2026-08-08): 91 Java source files, approximately 2,965 lines in the areas above, 13 resources, four Spring Boot entry points, and no test source files.

## Repository layout

```text
MicroserviceModule/
├── pom.xml                         # Inherits all root dependencies
└── src/main/
    ├── java/
    │   ├── basicWebApp/
    │   │   ├── courseApp/          # Course application
    │   │   └── jewelleryApp/       # Jewellery application
    │   ├── modernWebApp/
    │   │   ├── shoppingApp/        # Shopping application
    │   │   └── stockApp/           # Stock application
    │   ├── aws/storage/s3/          # Dormant S3 adapter
    │   ├── observabilityApp/        # Dormant metrics components
    │   └── util/                    # Generic helper
    └── resources/microservice/      # One properties file per demo
```

## Build

From the repository root:

```powershell
.\mvnw.cmd -pl MicroserviceModule test
```

The command completed successfully on 2026-08-08 with Java release 25. It compiled 91 sources but ran no tests. The build also reported:

- no `src/main/proto` directory even though the inherited protobuf plugin runs;
- Lombok builder-default warnings;
- unmapped MapStruct target fields;
- deprecated and unchecked API usage.

The child POM declares Java 21 source/target properties, but the root compiler configuration uses `${maven.java.version}`, currently 25. The effective build therefore compiles with `--release 25`.

## Running an application in the current layout

Because the module has multiple main classes, always select one explicitly. For example:

```powershell
.\mvnw.cmd -pl MicroserviceModule spring-boot:run "-Dspring-boot.run.main-class=modernWebApp.stockApp.StockApp"
```

Equivalent main classes are:

- `basicWebApp.courseApp.CourseApp`
- `basicWebApp.jewelleryApp.JewelleryApp`
- `modernWebApp.stockApp.StockApp`
- `modernWebApp.shoppingApp.ShoppingApp`

Swagger UI is intended to be available at `<context-path>/swagger-ui/index.html` on each application's port. A successful compilation does not establish that every application starts; see the known issues below.

## Known issues

1. The root aggregator POM declares application dependencies. Every child module therefore inherits web, JPA, AMQP, Batch, gRPC, AWS, Avro, PostgreSQL, H2, and other libraries whether it uses them or not.
2. One JAR has four possible main classes, so its runtime identity and Spring Boot packaging target are ambiguous.
3. Course config scans `microservice.basicWebApp.courseApp`, but code lives under `basicWebApp.courseApp`.
4. Shopping config scans `microservice.shoppingApp`, but code lives under `modernWebApp.shoppingApp`. Its properties file also contains none of the `postgres.*` values consumed by `DatabaseConfig`.
5. Shopping requests `ShoppingApp.properties`, while the resource is named `shoppingApp.properties`. That case mismatch can fail in a packaged JAR or on a case-sensitive filesystem.
6. `aws.storage.s3` and `observabilityApp` are sibling packages, so none of the four application entry points discovers them with default component scanning.
7. Course persists H2 data beneath the user's home directory and uses `create-drop`; this is unsuitable as a shared default.
8. There are no unit, slice, integration, context-load, or architecture tests.
9. Configuration uses custom `spring.config.location` defaults. Standard `application.yml` plus profiles would be easier to override and operate.
10. Package names mix naming conventions and do not use a stable reverse-domain namespace.

## Consolidation decision

Consolidate build conventions, configuration patterns, documentation, and reusable infrastructure. Keep the four runtime applications separate because they have different APIs, data stores, ports, and operational lifecycles. Combining them into one Spring context would create a modular monolith, not simplify a microservice suite.

The first safe implementation step is characterization testing. Do not move packages or split Maven artifacts until each application's context and important endpoints have a minimal automated test boundary.

