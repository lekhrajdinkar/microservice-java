package more.kafka.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(
        name = "app.kafka.consumer.kafka-generic-consumer-group.enabled",
        havingValue = "true",
        matchIfMissing = false)
public class KafkaConsumerService
{
    private final ObjectMapper objectMapper = new ObjectMapper();

    // =====================================
    // ✅ Generic Consumer for multiple topics
    // ====================================
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
}
