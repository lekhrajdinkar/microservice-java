## KafkaStreamApp
- Reference:
    - JT playlist : https://www.youtube.com/watch?v=U7RZcBtP6Dw&list=PLVz2XdJiJQxz55LcpHFM6QIB-Px40w3Gt&index=4

- POC:
    - Build real-time stream processing applications using Kafka Streams API with Spring Boot.
    - Process data in motion, perform transformations, aggregations, and joins on streaming data.
    - Leverage Kafka's scalability and fault-tolerance for robust stream processing.
    - **JsonSerde** for serialization/deserialization. 👈🏻
    - This POC uses JSON serde (Spring's JsonSerde)

### overview
- main: [kafkaStreamApp.java](kafkaStreamApp.java)
- docs: http://localhost:8092/kafkaStreamApp/swagger-ui/index.html
- props: [application.yml](../../../../resources/application.yml)
- conductor console: http://localhost:8080/console/my-local-kafka-cluster

### Commands
1. Start the app (from KafkaModule directory):

```powershell
mvn -pl KafkaModule -am -DskipTests spring-boot:run
```

2. Publish a test message:

```powershell
curl -X POST -H "Content-Type: application/json" -d '{"id":"1","name":"lekhraj kumar","age":25}' http://localhost:8092/api/v1/students/publish
```

3. Consume the output topic:

```powershell
kafka-console-consumer --bootstrap-server localhost:9092 --topic stream-app-student-topic-processed --from-beginning
```