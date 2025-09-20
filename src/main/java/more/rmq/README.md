## run rmq locally
```bash
docker run -d --hostname my-rabbit --name some-rabbit -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```
```bash
cd ./../../../../../src/main/resources/more/rmq
docker-compose -f docker-compose.yml up -d
```

- RMQ Management console UI : http://localhost:15672/#/ ✅

![img.png](img.png)

---
## RmqSpringApp
- API docs: http://localhost:8095/RmqSpringApp/swagger-ui/index.html
- props : [RmqSpringApp.properties](../../../resources/more/rmq/RmqSpringApp.properties)
- main class : [RmqSpringApp.java](../java/more/rmq/RmqSpringApp.java)
  - run : `mvn spring-boot:run -Dspring-boot.run.profiles=rmq`
  - test : [RmqSpringAppTest.java](../java/more/rmq/RmqSpringAppTest.java)
