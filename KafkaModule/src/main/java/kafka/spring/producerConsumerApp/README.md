## kafkaProducerConsumerApp - producer & consumer

### POC Summary
- This POC demonstrates Kafka basics and advanced patterns using:
- **Java Spring Boot** (producers, consumers, schema integration).
- **Confluent Schema Registry** for Avro schema-based messaging.
- [📋SD Notes](https://github.com/lekhrajdinkar/solution-engineer/tree/main/docs/06_message-broker/kakfa)

- Topics used:
  - `student-topic`
  - `customer-topic`
  - `generic-topic`

### overview
- env setup: [README_env_setup.md](../../../../../../README.md)
- [https://chatgpt.com/c/68cc4d40-7964-8333-86be-2846ae7979e8](https://chatgpt.com/c/68cc4d40-7964-8333-86be-2846ae7979e8)
- main: [kafkaProducerConsumerApp.java](kafkaProducerConsumerApp.java)
- Swagger docs : http://localhost:8091/kafkaProducerConsumerApp/swagger-ui/index.html 👈🏻
- props: [kafkaProducerConsumerApp.properties](../../../../resources/more/kafka/kafkaProducerConsumerApp.properties)
- conductor console: http://localhost:8080/console/my-local-kafka-cluster
- [avro](../../../../resources/avro)

![img.png](../../../../../../../docs/99_img/2025/img/img.png)
  
### Error and its Fix
```
🔶 Caused by: org.springframework.messaging.converter.MessageConversionException: 
Cannot convert from [org.apache.avro.generic.GenericData$Record] 
to [more.kafka.spring.avro.Student] for GenericMessage
✔️Fix : config.put("specific.avro.reader", true);

---
🔶 java.lang.IllegalStateException: Producer factory does not support transactions
✔️ Fix : config.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "txn-producer-1");
```

---
### Producer Advance
```properties
# ✔️ Idempotent Producer
props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
props.put(ProducerConfig.ACKS_CONFIG, "all");
props.put(ProducerConfig.RETRIES_CONFIG, 3);

# ✔️ Transactional Producer
Send messages atomically to multiple topics

props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "txn-id-1");

producer.initTransactions();
producer.beginTransaction();
producer.send(...);
producer.commitTransaction();

or

generic_kafkaTemplate.executeInTransaction(ops -> {
  ops.send("generic-topic", "dummy-message-step-1");
  ops.send("generic-topic", "dummy-message-step-2");
  //ops.send("generic-topic-2", "dummy-message-step-3");
  // ...
  return null;
});


# ✔️  Custom Partitioner
props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.example.kafka.CustomPartitioner");

# ✔️ Batching & Compression
props.put(ProducerConfig.BATCH_SIZE_CONFIG, 32*1024); // 32 KB + key must be null
props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

# ✔️Large Message Handling
on broker :
- Increase max.request.size & message.max.bytes .
- Optionally split large payloads into chunks.
- max 10 MB

# ✔️ Schema Evolution
- Register new Avro schema version with backward/forward compatibility.
- Producer sends using latest schema; consumer reads using previous schema if compatible.

# ✔️ produce - sync + async
- sysc - send(produceRecord)
- a-sync  - send(produceRecord, `new Callback() { @override onCompletion ... }`)
```

### Consumer Advance
```java 
/*
1. ✔️ Consumer Groups & Parallelism ✅
- Horizontal scaling across partitions 
- student-avro-consumer-group (2 consumers)

2. ✔️ Manual Offset Management ✅
- check Customer  
- Fine-grained control of commit 
- factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
*/
public void consumeCustomer(@Payload Customer customer, Acknowledgment ack) {
        System.out.println("Consumed Avro Customer: " + customer);
        ack.acknowledge();  // Commit offset only after processing is successful ◀️
}

/*
- Kafka Consumer API call (not Spring-specific).
    - commitSync() = safe, waits for broker, but slower.
    - commitAsync() = faster, but no guarantee (could fail silently).
*/

// 3. ✔️ Filtering Messages   ✅      
factory.setRecordFilterStrategy(record -> {})

// 4. ✔️ Consuming Headers ✅

// 5. ✔️ Batch Consumer
// - factory.setBatchListener(true);
@KafkaListener
public void consumeCustomerBatch(List<Customer> customers) {
    log.info("Batch size: {}", customers.size());
    customers.forEach(c -> log.info("{}", c));
}

/* 6. ✔️ parallelism
By default, Spring Kafka consumes partition events sequentially, so throughput is limited.
Increase Consumer Concurrency ?

way-1 (preferred ):: factory.setConcurrency(3); // Max concurrency = partition count
 or, @KafkaListener(concurrency = "3")
way-2 ::
 private final ExecutorService executor = Executors.newFixedThreadPool(3);
 executor.submit(() -> { ... } );
 
 ---
 
 consumer-group-1
 ├── consumer-1 (thread-1) → partition-1
 └── consumer-2 (thread-2) → partition-2
OR
consumer-group-1
 └── consumer-1
       ├── thread-1 → partition-1
       └── thread-2 → partition-2
 */

// 7. exception handling | retry mechanism | DLT dead-letter topics
// Retry 3 times, then send to DLT (customer-topic.DLT.)
DefaultErrorHandler errorHandler = new DefaultErrorHandler(
        new DeadLetterPublishingRecoverer(kafkaTemplate),
        new FixedBackOff(2000L, 3L) // retry every 2s, max 3 retries
);
factory.setCommonErrorHandler(errorHandler);

// 7. ✔️Serial/Deserialize

// 7.1. Custom -> class Class1 implements Serialize<T> : override byte[] serialize(T) and vice versa
// 7.2. JSON Serde is simple but no schema evolution safety. use for dev where Kafka Streams does not require Schema Registry
    - JsonSerde<T>
// 7.3. For production → Avro/Protobuf + Schema Registry
    - genericAvro
    - avro1,avro2

// 8. # Partition Assignment :
## --- Using RangeAssignor --- 
c1: partition-0, partition-1.
c2: partition-2, partition-3.

## ---  Using RoundRobinAssignor --- 
c1: partition-0, partition-2.
c2: partition-1, partition-3.
```

### Advance :: more to do
- monitoring & metrics
- security (SSL, SASL)
- schema evolution strategies
- testing strategies (embedded Kafka, MockConsumer)
- performance tuning (batch size, linger.ms, compression)
- Message Ordering (JT) : https://www.youtube.com/watch?v=Jl-nauqEtEo

