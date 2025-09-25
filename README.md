# java | springboot | microservices | kafka | rmq | spring-batch

## ✔️ documentation
- `mkdocs` 
  ```bash
  .\mkdocs\.venv\Scripts\activate
  pip install -r requirements-netlify.txt
  mkdocs serve
  ```
---
## ✔️CD/CI
- Deployment steps: https://github.com/lekhrajdinkar/microservice-java/blob/main/helm/readme.md

---
## ✔️POCs / projects
### 🔸leetcode
https://leetcode.com/u/lekhrajdinkar/

### 🔸Java7+ project
- [java Notes 📚](docs/01_java)
- [java evolution - project](src/main/java/evolution)
- [Advance concepts Notes📚](docs/03_Advance)
  - Advance project 1 (ms) : [javaSpringApp](src/main/java/evolution/javaSpringApp)
  - ...
  
### 🔸SpringBoot App
- [Notes 📚](docs/02_springboot)
- [Spring_01_AOP](src/main/java/springbootApp/AOP)
- [Spring_02_Core](src/main/java/springbootApp/SpringCore)
- [Spring_03_Properties](src/main/java/springbootApp/SpringProperties)
- [Spring_04_AutoConfiguration](src/main/java/springbootApp/SpringAutoConfiguration)
- ...

### 🔸micro-services projects
- 📚 check `readme.md` ( in each project for more **details** )
- web basic concept/s:  
  - [jewelleryApp](src/main/java/microservice/jewelleryApp) - restApi,swagger, servlet, jackson, error handling, etc
  - [courseApp](src/main/java/microservice/courseApp) - hibernate more, etc
- security: 
  - [shoppingApp](src/main/java/microservice/shoppingApp) 
  - [securityApp](src/main/java/microservice/securityApp) | OAuth2, okta, jwt
- More:
  - Advance web:  [stockApp](src/main/java/microservice/stockApp) | streaming, modulith, temporal
  - OTEL : [observabilityApp](src/main/java/microservice/observabilityApp)

### 🔸MessageBroker projects
- [📚 Notes](https://github.com/lekhrajdinkar/solution-engineer/tree/main/docs/06_message-broker)
- [kafka](src/main/java/more)
- [rmq](src/main/java/more/rmq)
- **ETL/Spring-Batch**  | [Notes 📚](docs/02_springboot/05_spring-batch-ETL)  | [springbatch](src/main/java/more/springbatch)

---
## ✔️ other/s
```
--spring.config.location=classpath:/custom-config.properties
--spring.config.additional-location=classpath:/custom-config.properties

--spring.profiles.active=dev

👉 Priority Rule:
Order of property resolution (highest wins):
  Command-line args (--key=value)
  spring.config.location file(s)
  spring.config.additional-location file(s)
  application-{profile}.properties
  application.properties
```


