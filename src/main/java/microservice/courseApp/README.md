# courseApp
## Overview
- **DTO/s**:
  - category/s > course/s > section/s > lesson/s
  - instructor/s > course/s
  - student/s > course/s
  - [InitDB.java](runner/InitDB.java) | [courseApp.sql](../../../resources/microservice/courseApp/courseApp.sql) 👈🏻
  - check ER diagram DB client as well
- jpa-entity, orm-relationship, custom-JPA-repo, [CustomIdentifier](custom/CustomIdentifier.java)
- multiple-Datasources, 
- tuple, modelapper/mapStrut

## Run time details
- **Database** : H2 
- **ApiDoc** : http://localhost:8082/courseApp/swagger-ui/index.html#/
- h2 console : http://localhost:8082/courseApp/h2-console
- h2 .db file: [h2](../../../../../h2)
- **Security**: not secured
- https://github.com/lekhrajdinkar/microservice-java/tree/main/src/main/java

--- 
## POC/s
### Database
- ✔️ sequence gen: [CustomIdentifier](custom/CustomIdentifier.java)
- ✔️ multiple DB/s | [config](config)
```Java
@EnableJpaRepositories(
        basePackages = "microservice.courseApp.package_for_h2",
        entityManagerFactoryRef = "entityManagerFactory_for_h2",
        transactionManagerRef = "transactionManager_for_h2"
)
class config_H2 {}


@EnableJpaRepositories(
    basePackages = "microservice.courseApp.package_for_postgres",
    entityManagerFactoryRef = "entityManagerFactory_for_postgres",
    transactionManagerRef = "transactionManager_for_postgres"
)
class config_postgres {}
```

- ✔️ **custom** JPA Repo
  - [Student2CustomRepository.java](custom/Student2CustomRepository.java)
  - [Student2.java](repository/entity/Student2.java) -> named query defined as well.
  - ▶️[StudentController.java](controller/StudentController.java) -> CRUD
```
- CRUD Operations
    - create
    - Read List<Student>
    - Read List<Tuple>
    
- Manually manage:
    - EntityManagerFactory / sessionFactory
    - EntityManager
    - Begin transaction 
    - commit transaction
    - EntityManager :: close
```
- **ORM relationships**
  
- **database transaction**
    - ...

### API
- [CategoryApi.java](controller/CategoryApi.java) implementation ->  [CategoryController.java](controller/CategoryController.java)

- **mapper : ModelMapper vs MapStruct**
    - dto to entity
    - entity to dto
    - ...
    - [CourseDAO.java](repository/CourseDAO.java) | [mapper](repository/mapper)

- - **jackson**
- objectMapper
- JsonNode
- serialization / deserialization
- ...

### More
- [errorHandling](errorHandling)
