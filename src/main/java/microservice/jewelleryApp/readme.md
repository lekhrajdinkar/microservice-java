# JewelleryApp
## Overview
- Database : no
- ApiDoc : http://localhost:8086/jewelleryApp/swagger-ui/index.html#/

## POC/s
### 0 web server
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

### 1. rest api + swagger doc
- **CRUD operations**: [JewelleryController.java](JewelleryController.java)
  - GET ✔️
  - POST ✔️
  - PUT  🔸
  - DELETE 🔸
  - PATCH 🔸
  - ...
- Global exception handling ✔️
  - [errorHandler](errorHandler)

### 2. restApi consume
- RestTemplate ✔️
    - [RestConsumeRunner.java](Runner/RestConsumeRunner.java)
    - [RestConsumeRunner_2.java](Runner/RestConsumeRunner_2.java)
- Http client (New) 🔸
    - ...

### 3. custom: Servlets, Filters, Listeners
- servlet ✔️ [MyServlet.java](custom/servlet_filter_listener/MyServlet.java)
- Filters  🔸
- Listeners 🔸

### 4. hibernate jpa
- custom Id generator ✔️  [CustomIdentifier.java](custom/CustomIdentifier.java)

### 5.1. custom: converter
- [converter](custom/converter) ✔️

### 5.2. custom: validator
- [validator](custom/validator) ✔️

### 5.3. custom: serializer/deserializer 
- [JacksonConfig.java](config/JacksonConfig.java)
    ```properties
    spring.jackson.serialization.write-dates-as-timestamps=false
    spring.jackson.default-property-inclusion=non_null
    ```
- [Serializer](custom/Serializer) ✔️
- Deserializer  🔸

