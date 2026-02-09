# java | Spring boot | microServices | kafka | rmq | spring-batch | SpringAI 🤖 | etc
---
## 🔰2012-2024
### ✔️Java7 -java21
- [java evolution - project and Notes](JavaEvolutionModule/src/main/java/evolution)
- [Java 7 README.md](JavaEvolutionModule/src/main/java/evolution/java_7/README.md)
- [Java 8 README.md](JavaEvolutionModule/src/main/java/evolution/java_8/README.md)
- [Java 11 README.md](JavaEvolutionModule/src/main/java/evolution/java_11/README.md)
- [Java 17 README.md](JavaEvolutionModule/src/main/java/evolution/java_17/README.md)
- [Java 21 README.md](JavaEvolutionModule/src/main/java/evolution/Java_21/README.md)
- [Java 25 README.md](JavaEvolutionModule/src/main/java/evolution/java_25/README.md)

### ✔️Java Spring App
- [JavaSpringApp README.md](JavaEvolutionModule/src/main/java/evolution/javaSpringApp/README.md)
- git, maven, etc: [00_others.md](docs/2012-2024/00_01_developer_tool.md)

### ✔️SpringBoot 
[📚Notes](docs/2012-2024) | Projects:
- [Spring_01_AOP](SpringBootModule/src/main/java/AOP) 
- [Spring_02_Core](SpringBootModule/src/main/java/SpringCore) 
- [Spring_03_Properties](SpringBootModule/src/main/java/SpringProperties)
- [Spring_04_AutoConfiguration](SpringBootModule/src/main/java/SpringAutoConfiguration) 

### ✔️micro-services projects
> **BasicWebApp**
>
> [jewelleryApp, README.md](MicroserviceModule/src/main/java/basicWebApp/jewelleryApp/README.md) 
> - restApi(no DB, h/c), swagger doc, servlet, webServer, jackson, error handling, caching response, etc
>
> [courseApp, README.md](MicroserviceModule/src/main/java/basicWebApp/courseApp/README.md) 
> - h2, hibernate more, txn, tuple, pagination, etc

### ✔️RabbitMQ-projects
- environment Setup: Run Docker docker for RMQ + Management console:
- ```docker run -d --hostname my-rmq --name some-rabbit -p 5672:5672 -p 15672:15672 rabbitmq:3-management```
- **App1** - Producer/Consume | [README.md](RmqModule/src/main/java/rmq/README.md)

### ✔️ETL projects
- **Spring-Batch**  : [Notes 📚](docs/2012-2024/05_spring-batch-ETL)  |  [Project ](ETLModule/src/main/java/springbatch)

---
## 🔰2025
### ✔️Kafka-projects
- [📚 Notes](https://github.com/lekhrajdinkar/solution-engineer/tree/main/docs/06_message-broker)
- Environmnet Setup: [README_env_setup.md](KafkaModule/src/main/java/kafka/spring/README_env_setup.md)
- **App1** - `Producer/Consumer API` + **Avro schema** | [README.md](KafkaModule/src/main/java/kafka/spring/producerConsumerApp/README.md)
- **App2** - `KafkaStream API` + **JsonSerde** | [README.md](KafkaModule/src/main/java/kafka/spring/streamApp/README.md)
- 
### ✔️micro-services projects
> - **SecurityApp**
>    - [SecurityApp README.md](WebSecurityModule/src/main/java/securityApp/README.md)| OAuth2, okta, jwt, basic
> -  **modernWebApp** 🔴
>    - [shoppingApp README.md](MicroserviceModule/src/main/java/modernWebApp/shoppingApp/README.md) - ASGI, Async, websocket, grpc
>    - [stockApp README.md](MicroserviceModule/src/main/java/modernWebApp/stockApp/README.md) - streaming, modulith, temporal
>    - communication pattern: rest, grpc, websocket, messaging (kafka), rmq
>    - k8s Helm | Deployment steps https://github.com/lekhrajdinkar/microservice-java/blob/main/helm/readme.md
> -  [observabilityApp README.md](MicroserviceModule/src/main/java/observabilityApp/README.md) - otel, aws-x-rays 🔴

### ✔️Misc
- [2025](docs/2025)

---
## 🔰2026
### In-progress:
-  [Java 25 README.md](JavaEvolutionModule/src/main/java/evolution/java_25/README.md) 🔴
-  **SpringAI** [02_01_SpringAI-start.md](docs/2026/02_01_SpringAI-start.md) 🔴
  - App-1 : ...
  - App-2 : ...

---
## ▶️More
- leet-code project : https://leetcode.com/u/lekhrajdinkar/
- `mkdocs` - local setup
  ```bash
  .\mkdocs\.venv\Scripts\activate
  pip install -r requirements-netlify.txt
  mkdocs serve
  ```

---
## ▶️Side Notes
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
- use maven wrapper > update it for InteliJ

# maven warpper
- [toolchains.xml](.mvn/toolchains.xml) > update hardcoded java path

---cmd-----
mvn clean compile
mvn -v
java -version
javac -version

--- Status--- (as of Sep 2025) 
java 21 : working ✔️
java 23 : Lombok not supported ❌
java 25 : Maven not supported ❌

--- More
- <!-- 🔶 Security --> comment this part in pom.xml(root)
```

![img.png](docs/99_img/java21.png)


