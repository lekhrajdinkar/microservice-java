## java 25 (Sep 2025)
- Java 22 (march 2024), java 23 (Sep 2024)
- java 24 (march 2025), java 23 (Sep 2025)
- removed the support for the legacy **32-bit x86 architecture** from OpenJDK (final)

### overview
- Official docs:
  - https://www.oracle.com/java/technologies/downloads/#jdk25-windows
  - https://docs.oracle.com/en/java/javase/25/index.html
  - https://openjdk.org/projects/jdk/25/ 👈🏻
- more:
  - https://chatgpt.com/c/68e356a9-91ac-832a-9ef4-ae8cbda0bc2e - can pass
  - https://www.baeldung.com/java-25-features - can pass
  - https://www.happycoders.eu/java/java-22-feature 👈🏻👈🏻👈🏻(must see)
  
---
### Features Summary
#### ✔️ language

| Java Version | JEP / Feature | Title / Status                                       | Description                                                                                                                              | Short Example / Use / Scenario                                                                                 |
| ------------ | ------------- | ---------------------------------------------------- |------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------|
| JDK 22       | 456           | Unnamed Variables & Patterns (final)                 | Use underscore (`_`) for variables or pattern elements you don’t intend to name/use ([HappyCoders.eu][101])                              | `catch (IOException _) { … }` or pattern match: `if (o instanceof Point(_, y)) …`                              |
| JDK 25       | 513           | Flexible Constructor Bodies (final)                  | Allows certain statements before calling `super(...)` or `this(...)` in a constructor (with constraints)                                 | Validation or field setup before invoking the parent constructor                                               |
| JDK 25       | 512           | **Compact Source Files** & Instance Main Methods (final) | Supports writing Java programs without explicit class boilerplate; non-static `main` allowed; auto imports from `java.base` ([InfoQ][102]) | [📚CompactSourceFile.java](language/CompactSourceFile.java)                                                    |
| JDK 25 | 507 | Primitive Types in Patterns, instanceof & switch | Extend pattern matching, instanceof, and switch to support primitive types                                                               | if (o instanceof int i) { … } or switch (x) { case int i -> … } ,  [📚Primitive.java](language/Primitive.java) |

[101]: https://www.happycoders.eu/java/java-22-features/?utm_source=chatgpt.com "Java 22 Features (with Examples) - HappyCoders.eu"
[102]: https://www.infoq.com/news/2025/05/jdk25-instance-main-methods/?utm_source=chatgpt.com "Instance Main Methods Move from Preview to Final in JDK 25 - InfoQ"


- **Module Import Declarations**
```java
import module java.base; // New 👈🏻

// Traditionally / OLD 
// dependencies in a module were only declared in :
// module-info.java 
//      - requires directives

// ---
// Scenario-1
import module java.base;
import module java.sql;

/*error:
both class java.sql.Date in java.sql and class java.util.Date in java.util match
error: reference to Date is ambiguous
*/
import java.sql.Date; //resolve /fix
```


#### ✔️API
- **Class-File API**
- **Java Stream - Gatherers**
    - https://www.youtube.com/watch?v=If6wFkY8ux4
    - https://www.perplexity.ai/search/java-stream-gatherers-https-ww-nqX3RLlRQlGUC0FoXQgmYw
- **Vector API**
- **StableValue<>** (JEP 502)
- **ScopedValue<>** (JEP 506)
  - `ScopedValue.where(…).run(…)` 
- **PEM** (JEP 470)
  -  adds support for reading and writing cryptographic keys and certificates in PEM format via standard APIs
  -  This improves interoperability with OpenSSL-based systems and streamlines secure communications.


#### ✔️Tooling, Classfile, Streams, Modules, APIs

| Java Version | JEP / Feature | Title / Status                                        | Description                                                                                                                    | Short Example / Use / Scenario                                            |
| ------------ | ------------- | ----------------------------------------------------- |--------------------------------------------------------------------------------------------------------------------------------| ------------------------------------------------------------------------- |
| JDK 22       | 457           | Class-File API (final)                                | Standard API to parse, inspect, modify, emit Java class files (replacing or reducing dependence on ASM, BCEL) ([OpenJDK][201]) | A tool or agent reading/modifying `.class` files programmatically         |
| JDK 22       | 458           | Launch Multi-File Source-Code Programs (final)        | `java` launcher can accept multiple `.java` files and compile/run on the fly ([OpenJDK][201])                                    | `java A.java B.java` without pre-compilation                              |
| JDK 23       | 467           | Markdown Documentation Comments (final)               | JavaDoc comments may use Markdown syntax instead of or in addition to traditional JavaDoc tags ([Eclipse Foundation][202])       | `/** * # Summary * Use **bold** … */`                                     |
| JDK 24       | 483           | Ahead-of-Time Class Loading & Linking                 | Preload and prelink classes to improve startup performance (Leyden / AOT cache) ([InfoQ][203])                                   | Use AOT / CDS / class prelinking to reduce runtime class loading overhead |
| JDK 24       | 484           | Class-File API (refined)                              | Continue evolution / integration of class-file API previews into more stable form ([InfoQ][203])                                 | Tools working on class file generation or analysis use this API           |
| JDK 24       | 485           | Stream Collectors / Gatherers (final / refined)       | Finalize or refine custom stream collector operations (windowing, scans, folds) in the Stream API ([InfoQ][203])                 | Use richer operations: `stream.collect(… gatherer …)` etc.                |
| JDK 24       | 492           | Flexible Constructor Bodies (preview)                 | Allow statements before `super(...)` / `this(...)` in constructors (preview version) ([InfoQ][203])                              | Similar to the final feature, but still under preview constraints         |
| JDK 24       | 494           | Module Import Declarations (preview)                  | Syntax to allow `import module M` style declarations for modules ([InfoQ][203])                                                  | Bring all public exports of a module into import scope                    |
| JDK 24       | 495           | Simple Source Files & Instance Main Methods (preview) | Continue refinement of compact / lightweight source file entry syntax (without explicit class) ([InfoQ][203])                    | Write `void main()` at top level in small scripts                         |
| JDK 25       | 512           | Compact Source Files & Instance Main Methods (final)  | Graduate preview syntax into full feature: implicit classes, instance `main`, `java.lang.IO`, auto imports ([InfoQ][204])        | Small programs, REPL-like code, learning environment                      |
| JDK 25       | 511           | Module Import Declarations (final)                    | Import module exports directly (simplified module import) ([Oracle][205])                                                        | `import module some.module;` to bring its public exports into scope       |

[201]: https://openjdk.org/projects/jdk/22/?utm_source=chatgpt.com "JDK 22 - OpenJDK"
[202]: https://www.eclipse.org/lists/jetty-dev/msg03758.html?utm_source=chatgpt.com "[jetty-dev] JDK 25 feature set is now frozen! - Eclipse"
[203]: https://www.infoq.com/news/2025/02/java-24-so-far/?utm_source=chatgpt.com "JDK 24 and JDK 25: What We Know So Far - InfoQ"
[204]: https://www.infoq.com/news/2025/05/jdk25-instance-main-methods/?utm_source=chatgpt.com "Instance Main Methods Move from Preview to Final in JDK 25 - InfoQ"
[205]: https://www.oracle.com/ca-en/news/announcement/oracle-releases-java-25-2025-09-16/?utm_source=chatgpt.com "Oracle Releases Java 25"

#### ✔️Concurrent / Context / Execution / GC / Runtime Features
| Java Version | JEP / Feature    | Title / Status                                                 | Description                                                                                                                           | Short Example / Use / Scenario                                                      |
| ------------ | ---------------- | -------------------------------------------------------------- |---------------------------------------------------------------------------------------------------------------------------------------| ----------------------------------------------------------------------------------- |
| JDK 22       | 423              | Region Pinning for G1 (final)                                  | G1 GC can pin regions referenced by native code, so GC need not pause entirely for pinned objects ([BellSoft][301])                   | When native code has a pointer to a Java object, GC can skip relocating that region |
| JDK 24       | 404              | Generational Shenandoah (experimental)                         | Introduces generational mode to Shenandoah GC (young/old regions) ([InfoQ][302])                                                        | Use `-XX:ShenandoahGCMode=generational` to operate in generational mode             |
| JDK 24       | 491              | Synchronize Virtual Threads without Pinning                    | Permit use of `synchronized` methods with virtual threads without needing to pin threads ([InfoQ][302])                                 | Virtual-thread code can safely use `synchronized` without performance penalties     |
| JDK 24       | 490              | ZGC: Remove Non-Generational Mode                              | Drop support for the legacy (non-generational) mode of ZGC ([InfoQ][302])                                                               | Only generational ZGC remains in future versions                                    |
| JDK 24       | 486              | Permanently Disable Security Manager                           | Remove legacy `SecurityManager` support entirely ([InfoQ][302])                                                                         | Apps relying on `setSecurityManager` must migrate to alternative security models    |
| JDK 25       | (expected final) | Generational Shenandoah (move from experimental to production) | Shenandoah’s generational GC becomes production-qualified ([InfoWorld][303])                                                            | Use generational Shenandoah GC in production environments                           |
| JDK 25       | 509              | JFR CPU-Time Profiling (experimental)                          | Extend Java Flight Recorder to capture CPU-time profiling data (esp. on Linux) ([Erik Gahlin’s Blog][304])                              | Enable JFR to record method-level CPU usage, not just wall-clock events             |
| JDK 25       | (preview)        | Stable Values (preview)                                        | API for “stable” immutable data, treated similarly to constants, but with flexible initialization timing ([OpenJDK Mailing Lists][305]) | `@Stable int x = expensiveInit();` — JVM can optimize as if `final`                 |

[301]: https://bell-sw.com/blog/an-overview-of-jdk-22-features/?utm_source=chatgpt.com "An overview of JDK 22 features - BellSoft"
[302]: https://www.infoq.com/news/2025/02/java-24-so-far/?utm_source=chatgpt.com "JDK 24 and JDK 25: What We Know So Far - InfoQ"
[303]: https://www.infoworld.com/article/3846172/jdk-25-the-new-features-in-java-25.html?utm_source=chatgpt.com "JDK 25: The new features in Java 25 - InfoWorld"
[304]: https://egahlin.github.io/2025/05/31/whats-new-in-jdk-25.html?utm_source=chatgpt.com "What's new for JFR in JDK 25 - Erik Gahlin"
[305]: https://mail.openjdk.org/pipermail/jdk-dev/2025-February/009787.html?utm_source=chatgpt.com "JEP proposed to target JDK 25: 502: Stable Values (Preview)"

#### ✔️ Interop / Native / Memory / JNI & Unsafe

| Java Version | JEP / Feature | Title / Status                                           | Description                                                                                                             | Short Example / Use / Scenario                                                             |
| ------------ | ------------- | -------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------ |
| JDK 22       | 454           | Foreign Function & Memory API (final)                    | Safe, efficient mechanism to call native functions and access off-heap memory; intended to supersede JNI ([OpenJDK][401]) | Use `MemorySegment`, `Linker`, etc., to call a C function or manipulate an off-heap buffer |
| JDK 24       | 472           | Prepare to Restrict JNI                                  | Add restrictions / tighten use of JNI in favor of safer interop APIs                                                    | Encourage migration from JNI to the FFM API                                                |
| JDK 24/25    | (TBD in 25)   | Compact Object Headers (project Lilliput) (experimental) | Reduce object header size to lower memory footprint ([InfoQ][402])                                                        | More memory-efficient representation of Java objects                                       |
| JDK 25       | 470           | PEM Encodings of Cryptographic Objects (preview)         | API to encode / decode keys, certs, CRLs in PEM format (for PKI interoperability) ([Oracle][403])                         | Load `PrivateKey` from PEM, output `Certificate` to PEM                                    |

[401]: https://openjdk.org/projects/jdk/22/?utm_source=chatgpt.com "JDK 22 - OpenJDK"
[402]: https://www.infoq.com/news/2025/02/java-24-so-far/?utm_source=chatgpt.com "JDK 24 and JDK 25: What We Know So Far - InfoQ"
[403]: https://www.oracle.com/ca-en/news/announcement/oracle-releases-java-25-2025-09-16/?utm_source=chatgpt.com "Oracle Releases Java 25"
