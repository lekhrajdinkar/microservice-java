package more.kafka.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService
{
    private  ObjectMapper objectMapper = new ObjectMapper();
    @Autowired @Qualifier("generic_KafkaTemplate") private  KafkaTemplate<String, String> generic_kafkaTemplate;
    @Value("${app.kafka.topic.generic-topic-name}") String genericTopic;

    public void sendStudent(Object student)
    {
        try {
            String message = objectMapper.writeValueAsString(student);
            generic_kafkaTemplate.send(genericTopic, message);
            System.out.println("Student message sent: " + message);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing Student object: " + e.getMessage());
        }
    }

    public void sendCustomer(Object customer) {
        try {
            String message = objectMapper.writeValueAsString(customer);
            generic_kafkaTemplate.send(genericTopic, message);
            System.out.println("Customer message sent: " + message);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing Customer object: " + e.getMessage());
        }
    }
}
