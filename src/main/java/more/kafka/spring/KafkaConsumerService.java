package more.kafka.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import more.kafka.spring.avro.Customer;
import more.kafka.spring.avro.Student;

@Service
@ConditionalOnProperty(
        name = "app.kafka.consumer.kafka-generic-consumer-group.enabled",
        havingValue = "true",
        matchIfMissing = false)
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
            if (message.contains("customerId")) {
                // Process Customer
                Customer customer = objectMapper.readValue(message, Customer.class);
                System.out.println("Received Customer: " + customer);
            }

            else if (message.contains("id")) {
                // Process Student
                Student student = objectMapper.readValue(message, Student.class);
                System.out.println("Received Student: " + student);
            }

            else {
                System.out.println("Unknown message: " + message);
            }
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }


    // ===========================================
    // ✅ Avro Consumer for multiple topics
    // ==========================================
    @KafkaListener(
            topics = { "${app.kafka.topic.customer-topic-name}" },
            groupId = "customer-avro-consumer-group", // "avro-consumer-group",
            containerFactory = "avro_KafkaListenerContainerFactory_Customer"
    )
    public void consumeCustomer(@Payload Customer customer) {
        System.out.println("Consumed Avro Customer: " + customer);
    }

    @KafkaListener(
            topics = { "${app.kafka.topic.student-topic-name}" },
            groupId = "student-avro-consumer-group", // "avro-consumer-group",
            containerFactory = "avro_KafkaListenerContainerFactory_Student"
    )
    public void consumeStudent(@Payload Student student) {
        System.out.println("Consumed Avro Student: " + student);
    }
}
