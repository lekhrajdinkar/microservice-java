# courseApp
## Overview
- DTO/s:
  - category/s > course/s > section/s > lesson/s
  - instructor/s > course/s
  - student/s > course/s

## Run time details
- **Database** : H2 
- **ApiDoc** : http://localhost:8082/courseApp/swagger-ui/index.html#/
- h2 console : http://localhost:8082/courseApp/h2-console
- **Security**: not secured

--- 
## POC/s
### Database
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
  - ▶️[Student2Runner.java](runner/Student2Runner.java) -> CRUD
```
- CRUD Operations
    - create
    - Read List<Student>
    - Read List<Tuple>
    
- Manually manage:
    - EntityManagerFactory / sessionFactory
    - Begin transaction 
    - commit transaction
```


### API
- [CategoryApi.java](controller/CategoryApi.java) implementation ->  [CategoryController.java](controller/CategoryController.java)