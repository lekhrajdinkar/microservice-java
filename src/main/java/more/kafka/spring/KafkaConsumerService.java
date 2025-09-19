package more.kafka.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import more.kafka.spring.avro.Customer;
import more.kafka.spring.avro.Student;

@Slf4j
@Service
@ConditionalOnProperty(
        name = "app.kafka.consumer.kafka-generic-consumer-group.enabled",
        havingValue = "true",
        matchIfMissing = false
    )
public class KafkaConsumerService
{
    private final ObjectMapper objectMapper = new ObjectMapper();

    // ===============================================
    // ✅ Generic/String Consumer for multiple topics
    // ===============================================
    @KafkaListener(
            topics = { "${app.kafka.topic.generic-topic-name}" },
            groupId = "kafka-generic-consumer-group",
            containerFactory = "generic_KafkaListenerContainerFactory"
    )
    public void genericConsume(String message) {
        try {
            System.out.println("generic message received 🟡: " + message);
        } catch (Exception e) {
            System.err.println("Error processing message 🟡: " + e.getMessage());
        }
    }


    // ===========================================
    // ✅ Avro Consumer for multiple topics
    // ==========================================
    // Customer (1)
    @KafkaListener(
            topics = { "${app.kafka.topic.customer-topic-name}" },
            groupId = "customer-avro-consumer-group", // "avro-consumer-group",
            containerFactory = "avro_KafkaListenerContainerFactory_Customer"
    )
    public void consumeCustomer(
            @Payload Customer customer,
            //@Payload List<Customer> customers, // for batch listener  ◀️
            Acknowledgment ack,
            @Header("source") String source, // Consuming Headers ◀️
            @Header(KafkaHeaders.RECEIVED_KEY) String key
        )
    {
        //customers.forEach(c -> log.info("{}", c));
        log.info("Consumed: {} with key={} source={}", customer, key, source);
        ack.acknowledge();  // Commit offset only after processing is successful ◀️
    }

    // Student (2, parallelism)
    @KafkaListener(
            topics = { "${app.kafka.topic.student-topic-name}" },
            groupId = "student-avro-consumer-group", // "avro-consumer-group",
            containerFactory = "avro_KafkaListenerContainerFactory_Student"
    )
    public void consumeStudent_1(@Payload Student student) {
        System.out.println("Consumed Avro Student: " + student);
    }

    @KafkaListener(
            topics = { "${app.kafka.topic.student-topic-name}" },
            groupId = "student-avro-consumer-group", // "avro-consumer-group",
            containerFactory = "avro_KafkaListenerContainerFactory_Student"
    )
    public void consumeStudent_2(@Payload Student student) {
        System.out.println("Consumed Avro Student: " + student);
    }
}
