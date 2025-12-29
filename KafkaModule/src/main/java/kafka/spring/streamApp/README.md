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
  - [KafkaStreamService.java](KafkaStreamService.java) has KStreams<K,V> 👈🏻
- docs: http://localhost:8092/kafkaStreamApp/swagger-ui/index.html
- props: [application.yml](../../../../resources/application.yml)
- conductor console: http://localhost:8080/console/my-local-kafka-cluster
- `@EnableKafkaStreams`
- topics:
  - input: **stream-app-student-topic**
  - output: **stream-app-student-topic-processed**

### API endpoints
- GET http://localhost:8092/management/streams/health
  - can Add **Actuator** and wire the streams health into /actuator/health.
- POST http://localhost:8092/api/v1/students/publish
- POST http://localhost:8092/api/v1/students/publish-streams/{count}

### Commands
- Start the app (from KafkaModule directory):
```powershell
mvn -pl KafkaModule -am -DskipTests spring-boot:run
```

- Publish a test message:
```powershell
curl -X POST -H "Content-Type: application/json" -d '{"id":"1","name":"lekhraj kumar","age":25}' http://localhost:8092/api/v1/students/publish
```

- Consume the output topic:
```powershell
kafka-console-consumer --bootstrap-server localhost:9092 --topic stream-app-student-topic-processed --from-beginning
```

### Stream Operations
This POC uses Spring Kafka Streams / Kafka Streams DSL. Below are concise examples of common stream operations you can use in `KafkaStreamService.java` (KStream<K,V> pipeline examples).

- filter
    Keep only records matching a predicate.
    
    Example:
        KStream<String, StudentJson> students = builder.stream("stream-app-student-topic");
        KStream<String, StudentJson> adults = students.filter((k, v) -> v != null && v.getAge() >= 18);

- mapValues
    Transform only the value, preserving the key.
    
    Example:
        KStream<String, StudentJson> upper = adults.mapValues(s -> { s.setName(s.getName().toUpperCase()); return s; });

- map
    Transform both key and value (or change the key).
    
    Example:
        KStream<String, StudentJson> keyedByName = upper.map((k, v) -> KeyValue.pair(v.getName(), v));

- flatMapValues
    Expand a value into zero or more values (useful for splitting).
    
    Example:
        KStream<String, String> nameParts = keyedByName.flatMapValues(s -> Arrays.asList(s.getName().split("\\s+")));

- peek
    Side-effect operation useful for logging or metrics (doesn't modify stream).
    
    Example:
        nameParts.peek((k, v) -> System.out.println("name-part: key=" + k + " value=" + v));

- branch
    Split stream into multiple streams by predicates.
    
    Example:
        KStream<String, StudentJson>[] branches = students.branch(
            (k, v) -> v.getAge() < 18,
            (k, v) -> v.getAge() >= 18
        );

- groupBy / aggregate / count (stateful)
    Aggregate or count by key (creates a KTable).
    
    Example (count students by name):
        KTable<String, Long> counts = students
            .map((k, v) -> KeyValue.pair(v.getName(), v))
            .groupByKey(Grouped.with(Serdes.String(), new JsonSerde<>(StudentJson.class)))
            .count(Materialized.as("student-counts-store"));

- windowed counts
    Example (tumbling window 5 minutes):
        TimeWindows tumbling = TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(5));
        KTable<Windowed<String>, Long> windowedCounts = students
            .map((k, v) -> KeyValue.pair(v.getName(), v))
            .groupByKey(Grouped.with(Serdes.String(), new JsonSerde<>(StudentJson.class)))
            .windowedBy(tumbling)
            .count();

- joins (stream-stream)
    Join two streams when keys match within a time window.
    
    Example:
        KStream<String, EnrichedStudent> enriched = students.join(
            otherStream,
            (left, right) -> new EnrichedStudent(left, right.getExtra()),
            JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(2)),
            StreamJoined.with(Serdes.String(), new JsonSerde<>(StudentJson.class), new JsonSerde<>(Other.class))
        );

- transform / transformValues
    Use when you need access to state stores or custom processing (stateful).
    
    Example:
        KStream<String, StudentJson> transformed = students.transform(() -> new MyTransformer(), "my-state-store");

- to (send to output topic)
    
    Example:
        dtoStream.to("stream-app-student-topic-processed", Produced.with(Serdes.String(), new JsonSerde<>(StudentDto.class)));

Notes:
- Use `JsonSerde` (Spring's JsonSerde) for JSON serialization/deserialization of POJOs.
- Materialize stateful operations with `Materialized.as("store-name")` to enable state stores and interactive queries.
- Choose `groupBy` vs `groupByKey` depending on whether you change the key prior to aggregation.
- Keep stream operations immutable: each operation returns a new KStream/KTable.

For concrete implementations, add these snippets into `KafkaStreamService.java` where the `KStream<String, StudentJson>` is built and chained. The README examples are minimal — adapt Serde types, store names, and window durations to your use case.
