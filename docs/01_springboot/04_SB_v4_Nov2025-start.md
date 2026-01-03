# SpringBoot 4 (Spring 7)
- deliberate step into a modern, modular, cloud-native era of Java development
- Build on Spring 7.
- java 21 or 25 are recommended.
- Java 17 minimum

## References
- https://chatgpt.com/c/6958ca23-ab64-8326-8375-7025c711f036 🤖 | Spring Boot 4 Overview
- https://www.baeldung.com/spring-boot-4-spring-framework-7 | read once
- https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide#module-dependencies
- https://www.youtube.com/watch?v=uLGPTGWIVOw (Declarative HTTP Clients Demo, JT, watched once)

## New feature/sNew feature/s
### Base Improvements
- **performance**:
  - AOT compilation and GraalVM native image support are significantly improved
- **observability**
  - Spring Boot 3 introduced **Spring Observability**.
  - Spring Boot 4 upgrades to **Micrometer 2** and integrates an **OpenTelemetry starter**
- **Modular Codebase** / Easier maintenance
  - refactoring of its own codebase into a more **smaller** modular structure.
  - Faster builds (samller code Footprint) + Cleaner dependency management (more selective)
  - won't  see  this change as developer, but under the hood.
- **resilience** is now built-in. 
  - `@EnableResilientMethods -> @Retryable,  @ConcurrencyLimit`
  
### REST Enhancements
- new `@ConfigurationPropertiesSource`
- **API versioning**: @GetMapping(`version = "1"`, produces = MediaType.TEXT_PLAIN_VALUE)
- **resilience** is now built-in. `@EnableResilientMethods -> @Retryable,  @ConcurrencyLimit`
- Declarative **HTTP Clients**, inspired from feignClient (SpringCloud)
  ```java
    //@HttpServiceClient("client1")
    @HttpExchange("https://backend-api.com") // assumed: Not secured
    public interface ChristmasJoyClient 
    {
        // Resilence4 is built-in 
        @Retryable(maxAttempts = 3, delay = 100, multiplier = 2, maxDelay = 1000)
        @ConcurrencyLimit(3)
  
        @GetExchange("/greetings?random")
        String getRandomGreeting();
    }
  
    @Configuration
    @ImportHttpServiceClients(client1.class) // or
    @ImportHttpServices( basePackages="", types={client2.class, Client3.class} )
       public class HttpClientConfig {
    }
   ```
### More Enhancements
- **Testing Improvements**
- **Null Safety With JSpecify**

