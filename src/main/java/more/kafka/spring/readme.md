# Kafka Spring Example

## 1. Overview
This POC demonstrates Kafka basics and advanced patterns using:
- **Java Spring Boot** (producers, consumers, schema integration).
- **Confluent Schema Registry** for Avro schema-based messaging.

Topics used:
- `student-topic`
- `customer-topic`
- `generic-topic`

---
## 2. Environment Setup
### Docker Compose
- Kafka cluster + Zookeeper + Schema Registry + Conduktor Console already running.
- [docker-compose.yml](../../../../resources/more/kafka/docker-compose.yml)
- [platform-config.yml](../../../../resources/more/kafka/platform-config.yml)
- cluster name: `my-local-kafka-cluster`
- https://conduktor.io/get-started

```bash
cd ./../../../../../../src/main/resources/more/kafka
docker-compose -f docker-compose.yml up -d
```

- fix for cnductor-console 👈🏻
```yaml
  conduktor-console:
    image: conduktor/conduktor-console:1.26.0
    hostname: conduktor-console
    container_name: conduktor-console
    depends_on:
      - postgresql
    ports:
      - "8080:8080"
    volumes:
      - type: bind
        source: "/c/Users/Manisha/Documents/GitHub/idea/microservice-java/src/main/resources/more/kafka/platform-config.yml" # update this path 👈🏻👈🏻
        target: /opt/conduktor/platform-config.yaml
        read_only: true
    environment:
      CDK_IN_CONF_FILE: /opt/conduktor/platform-config.yaml
```

```text
[+] Running 7/7 ✅
 ✔ Network kafka_default           Created                                                                                                                                                                                 0.0s 
 ✔ Container zookeeper             Started                                                                                                                                                                                 1.6s 
 ✔ Container conduktor-monitoring  Started                                                                                                                                                                                 1.6s 
 ✔ Container postgresql            Started                                                                                                                                                                                 1.5s 
 ✔ Container conduktor-console     Started                                                                                                                                                                                 1.5s 
 ✔ Container kafka                 Started                                                                                                                                                                                 1.6s 
 ✔ Container schema-registry       Started
```

![docker1.png](../../../../resources/more/kafka/docker1.png)

---
## Spring Boot Application
- **kafkaSpringApp**
  - main: [kafkaSpringApp.java](kafkaSpringApp.java)
  - docs: http://localhost:8091/kafkaSpringApp/swagger-ui/index.html
  - props: [kafkaSpringApp.properties](../../../../resources/more/kafka/kafkaSpringApp.properties)
  - conductor console: http://localhost:8080/console/my-local-kafka-cluster
  - [avro](../../../../resources/avro)
  - ![img.png](img.png)
  
### Error and its Fix
```
Caused by: org.springframework.messaging.converter.MessageConversionException: 
Cannot convert from [org.apache.avro.generic.GenericData$Record] 
to 
[more.kafka.spring.avro.Student] for GenericMessage
---
Fix : ✔️
config.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

```