## java 25 (Sep 2025)
### overview
- docs:
  - https://www.oracle.com/java/technologies/downloads/#jdk25-windows
  - https://docs.oracle.com/en/java/javase/25/index.html
  - https://openjdk.org/projects/jdk/25/ 👈🏻
- more:
  - https://chatgpt.com/c/68e356a9-91ac-832a-9ef4-ae8cbda0bc2e
  - https://www.baeldung.com/java-25-features
  
---
### Developer
- Flexible Constructor Bodies - `this()/super()` does not to be first line.
- Primitive Types in : instanceof, switch
```
- Simplifies data transformation and type dispatching in modern APIs.
- Unifies pattern matching across primitive + object types → less boilerplate.
- More consistent with record patterns and deconstruction patterns.
```
| Feature                                      | Before Java 24   | Java 24 (Preview) |
| -------------------------------------------- | ---------------- | ----------------- |
| `i[Main.java](language/Main.java)nstanceof int`                             | ❌ Not allowed    | ✅ Allowed         |
| `switch (Object)` with `int`, `double`, etc. | ❌ Manual casting | ✅ Direct pattern  |

- Module Import Declarations
- Class-File API
- **Java Stream - Gatherers**
  - https://www.youtube.com/watch?v=If6wFkY8ux4 
  - https://www.perplexity.ai/search/java-stream-gatherers-https-ww-nqX3RLlRQlGUC0FoXQgmYw
- **Vector API**

---
### Architect
- performance:
  - Ahead-of-Time Class
  - Compact Object Headers
  - Late Barrier Expansion for G1
- Synchronize Virtual Threads without Pinning
- Security: Quantum-resistant cryptography

