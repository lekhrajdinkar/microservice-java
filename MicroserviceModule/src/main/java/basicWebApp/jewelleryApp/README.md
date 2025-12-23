# JewelleryApp
## Overview
- Develop/consume http API
- swagger doc, API versioning
- API response caching : pending
- custom : Servlets, Filters, Listeners
- custom : converter, validator, serializer/deserializer
- jackson
- Global Error handler
- ...

## Run time details
- Database : no DB, no entity | only DTO 👈🏻
- ApiDoc : http://localhost:8086/jewelleryApp/swagger-ui/index.html#/
- no security

---
## POC/s 
###  web server
- **Annotations**
  - `@EnableWebMvc` optional
  - `@ComponentScan`(basePackages = "...")
  - ...
- **Config** 
  - [WebConfig.java](config/WebConfig.java)
    - `WebMvcConfigurer` / Cusomize Spring MVC : Cors
    - `WebServerFactoryCustomizer`
      - **WebServerFactory** -> `TomcatServletWebServerFactory`, `JettyServletWebServerFactory`, etc
      - custom port, session timeout, error pages, ...
    - implement `WebApplicationInitializer`
- **Change from Tomcat to jetty**
  - Add dependency : **spring-boot-starter-jetty**
  - Add @Bean `JettyEmbeddedServletContainerFactory` 
  
### Controller layer
#### 1. rest api + swagger doc
- [CrudController.java](controller/CrudController.java)
- [ApiVersionController.java](controller/ApiVersionController.java)

#### 2. restApi consume
- RestTemplate ✔️
    - [RestConsumeRunner.java](Runner/RestConsumeRunner.java)
    - [RestConsumeRunner_2.java](Runner/RestConsumeRunner_2.java)
- Http client (New) 🔸
    - ...

#### 3. custom: Servlets, Filters, Listeners
- servlet ✔️ [MyServlet.java](custom/servlet_filter_listener/MyServlet.java)
- Filters  🔸
- Listeners 🔸

#### 4. hibernate jpa
- custom Id generator ✔️  [CustomIdentifier.java](custom/CustomIdentifier.java)

#### 5.1. custom: converter
- [converter](custom/converter) ✔️

#### 5.2. custom: validator
- [validator](custom/validator) ✔️

#### 5.3. custom: serializer/deserializer 
- [JacksonConfig.java](config/JacksonConfig.java)
    ```properties
    spring.jackson.serialization.write-dates-as-timestamps=false
    spring.jackson.default-property-inclusion=non_null
    ```
- serialization / deserialization
  - [Serializer](custom/Serializer) - LocalDateTime ✔️
  - Deserializer  🔸
- `objectMapper`
- `JsonNode`

#### 6. Global exception handling 
- [errorHandler](errorHandler) ✔️


