
## ✔️mkdocs
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
- https://leetcode.com/u/lekhrajdinkar/
### 🔸Java
- [Notes 📚](docs/01_java)
- **Projects**
  - [java_7](src/main/java/fundamentals/java_7)
  - [java_8_and_above](src/main/java/fundamentals/java_8_and_above)

---  
### 🔸SpringBoot App
- [Notes 📚](docs/02_springboot)
- **projects**
  - [Spring_01_AOP](src/main/java/com/lekhraj/java/spring/Spring_01_AOP)
  - [Spring_02_Core](src/main/java/com/lekhraj/java/spring/Spring_02_Core)
  - [Spring_03_Properties](src/main/java/com/lekhraj/java/spring/Spring_03_Properties)
  - [Spring_04_AutoConfiguration](src/main/java/com/lekhraj/java/spring/Spring_04_AutoConfiguration)

---
### 🔸Advance use-case/s
- [Notes 📚](docs/03_Advance)

---
### 🔸More
- [kafka](src/main/java/more)
- [rmq](src/main/java/more/rmq)
- ETL/Spring-Batch
  - [Notes 📚](docs/02_springboot/05_spring-batch-ETL)
  - [springbatch](src/main/java/more/springbatch)

---
### 🔸micro-services
- [courseApp](src/main/java/microservice/courseApp)
- [jewelleryApp](src/main/java/microservice/jewelleryApp)
- [shoppingApp](src/main/java/microservice/shoppingApp)
- [stockApp](src/main/java/microservice/stockApp)

---

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


