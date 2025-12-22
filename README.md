# java | springboot | microservices | kafka | rmq | spring-batch

## Continuous Annual Learning
- [index_learning_2025.md](docs/03_learning_2025/index_learning_2025.md)
- [index_learning_2026.md](docs/04_learning_2026/index_learning_2026.md)

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

### 🔸project - Java7 and above 
- [JavaSpringApp README.md](JavaEvolutionModule/src/main/java/evolution/javaSpringApp/README.md)
- [java evolution - project and Notes](JavaEvolutionModule/src/main/java/evolution)
  - [Java 7 README.md](JavaEvolutionModule/src/main/java/evolution/java_7/README.md)
  - [Java 8 README.md](JavaEvolutionModule/src/main/java/evolution/java_8/README.md)
  - [Java 11 README.md](JavaEvolutionModule/src/main/java/evolution/java_11/README.md)
  - [Java 17 README.md](JavaEvolutionModule/src/main/java/evolution/java_17/README.md)
  - [Java 21 README.md](JavaEvolutionModule/src/main/java/evolution/Java_21/README.md)
  - [Java 25 README.md](JavaEvolutionModule/src/main/java/evolution/java_25/README.md)
  - ...
- [Advance concepts Notes📚](docs/03_learning_2025)
  
### 🔸SpringBoot App
- [Notes 📚](docs/02_springboot)
- [Spring_01_AOP](SpringBootModule/src/main/java/AOP)
- [Spring_02_Core](SpringBootModule/src/main/java/SpringCore)
- [Spring_03_Properties](SpringBootModule/src/main/java/SpringProperties)
- [Spring_04_AutoConfiguration](SpringBootModule/src/main/java/SpringAutoConfiguration)
- ...

### 🔸micro-client projects
- ✔ Web basic App  
  - [jewelleryApp README.md](MicroserviceModule/src/main/java/basicWebApp/jewelleryApp/README.md) - restApi(no DB, h/c), swagger doc, servlet, webServer, jackson, error handling, caching response, etc
  - [courseApp README.md](MicroserviceModule/src/main/java/basicWebApp/courseApp/README.md) - h2, hibernate more, txn, tuple, pagination, etc
- [✔ modern Web App](MicroserviceModule/modernWebApp)
  - [shoppingApp README.md](MicroserviceModule/src/main/java/modernWebApp/shoppingApp/README.md) - ASGI, Async, websocket, grpc
  - [stockApp README.md](MicroserviceModule/src/main/java/modernWebApp/stockApp/README.md) - streaming, modulith, temporal
- More
  - [securityApp README.md](MicroserviceModule/src/main/java/securityApp/README.md) | OAuth2, okta, jwt, basic
  - [observabilityApp README.md](MicroserviceModule/src/main/java/observabilityApp/README.md) - otel, aws-x-rays

### 🔸MessageBroker projects
- [📚 Notes](https://github.com/lekhrajdinkar/solution-engineer/tree/main/docs/06_message-broker)
- [kafkaSpringApp README.md](MessageBrokerModule/src/main/java/kafka/spring/README.md)
- [rmqSpringApp README.md](MessageBrokerModule/src/main/java/rmq/README.md)
- **ETL/Spring-Batch**  
  - [Notes 📚](docs/02_springboot/05_spring-batch-ETL)  
  - [springbatch](ETLModule/src/main/java/springbatch)

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

- Set/update **java Runtime**
```
#1 pom.xml
<properties>
   <java.version>21</java.version>
   <maven.java.version>21</maven.java.version>
</properties>

#2  InteliJ    
- File → Project Structure → SDKs
- Set this as Project SDK and Module SDK
- In Settings → Build, Execution, Deployment → Compiler → Java Compiler, ensure:
    Use compiler: javac
    Target bytecode: 25 (or lower if compatibility needed)
- Check java version on runtime config for each app and validate java

---cmd-----
mvn clean compile
mvn -v
java -version
javac -version

--- Status--- (as of Sep 2025) 
java 21 : working ✔️
java 23 : Lombok not supported ❌
java 25 : Maven not supported ❌

```
![img.png](docs/99_img/java21.png)


