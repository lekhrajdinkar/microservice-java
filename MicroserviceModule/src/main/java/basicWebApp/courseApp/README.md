# courseApp
## Overview
- jpa-entity and orm-relationship 
- custom-JPA-repo
- sequence gen [CustomIdentifier](custom/CustomIdentifier.java)
- multiple DataSources - h2 and postgres
- tuple : `javax.persistence.tuple`
- modelMapper/mapStrut
- performance : pagination
- Transaction

## Run time details 
- **Database** : H2
  - console : http://localhost:8082/courseApp/h2-console 
  - driver : https://www.h2database.com/html/main.html
    - driver lib : C:\Program Files (x86)\H2\bin
- **ApiDoc** : http://localhost:8082/courseApp/swagger-ui/index.html#/
- **Security**: not secured
- **git**: https://github.com/lekhrajdinkar/microservice-java/tree/main/src/main/java

--- 
## POC/s
### A. DAO layer
#### ✔️ ORM relationships
- [InitDB.java](runner/InitDB.java)
- [entity](repository/entity)

```
  [Category] 1---* [Course] *---* [Student]
                       |
                       1
                       |
                  [Instructor]
``` 

#### ✔️ sequence gen
- [CustomIdentifier](custom/CustomIdentifier.java)

#### ✔️ multiple DB/s 
- [config](config)
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

#### ✔️ custom JPA Repo
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
  
#### database transaction
- [Student2ServiceImpl.java](services/Student2ServiceImpl.java)
- ...

#### ModelMapper vs MapStruct
- dto to entity
- entity to dto
- ...
- [CourseDAO.java](repository/CourseDAO.java) 
- [mapper](repository/modelMapper)

#### Pagination & Sorting

#### Performance Metrics
- Integrate actuator or custom metrics.



