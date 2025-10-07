## Java 11 (LTS)
### Overview
- https://chatgpt.com/c/8cbcbca6-603c-4831-9da3-54bf9eb49bd5
- https://chatgpt.com/c/4dd4601b-75ba-473c-93fb-48626341df24

---
### 🔰 Developer
#### ✔️JShell
- A Read-Eval-Print Loop (REPL) tool.

#### ✔️Module System
- purpose:
   - for better modularizing of code.
   - help to encapsulate packages.
   - improving maintainability
- self-contained unit:
  - Which packages it exposes to other modules 
  - Which modules it depends on
- demo: [module1](../../../../../module1) |  [module2](../../../../../module2)

#### ✔️language/compiler Enhancement
- Local-Variable Type Inference - **var**
  - used in lambda expressions
  - ...
- Interfaces > private methods
- try-with-resources statement
- **Stream API** ++ : 
  - `takeWhile`
  - `dropWhile`
- **Optional** ++ 
   - ifPresentOrElse(Consumer,Runnable)
   - stream() 
   - isEmpty() 
- **Diamond Operator** ++
- Nest-Based Access Control

#### ✔️compiler Enhancement
- **Multi-Release JARs**
  - Allowed a single JAR to support multiple versions of Java.
- **run as a script**
  - Allowed running a single-file program without compilation (`java MyClass.java`).

#### ✔️API 
- new **HTTP/2 Client**, for below protocol support:
  - HTTP/2
  - WebSocket
  - https://chatgpt.com/c/bfa71868-583f-471e-aa68-34c3c207fe0d 👈🏻
- **Multi-Resolution Image API**
- **OS Process API**
  - Improved API for managing and controlling OS processes.
  
---
### 🔰Architect
#### ✔️performance
- **Compact Strings** 
    - compact strings were introduced to improve memory usage for strings 
    - that only contain Latin-1 characters (ISO-8859-1). 
    - These strings are represented as **byte arrays internally** instead of char arrays, saving memory.
- **GC**
  - G1 garbage collector, now supports full GC **parallelism**.
  - **Epsilon**: A no-op garbage collector, useful for performance testing and short-lived jobs.
- **Application Class-Data Sharing** 
  - Improves startup time 
  - reduces memory footprint by sharing common class metadata **across JVM instances**.
- **heap**:
  - Experimental support for allocating the Java object heap on alternative memory devices

#### ✔️ Security
- **TLS 1.3** - protocol support.
- **Open-source root certificates** to improve the OpenJDK build process.

#### ✔️tool
- **Java Flight Recorder (JFR)**   
 - A low-overhead **data collection framework** for troubleshooting and profiling
 - CPU-time-based profiling (amount of CPU time spent in specific methods or threads)
   - `-XX:StartFlightRecording=filename=cpu-time.jfr`

