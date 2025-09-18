package more.kafka.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import more.kafka.spring.avro.Customer;
import more.kafka.spring.avro.Student;

@Service
public class KafkaProducerService
{
    private  ObjectMapper objectMapper = new ObjectMapper();
    @Autowired @Qualifier("generic_KafkaTemplate") private  KafkaTemplate<String, String> generic_kafkaTemplate;
    @Autowired @Qualifier("avro_KafkaTemplate_Customer") private  KafkaTemplate<String, Customer> avro_KafkaTemplate_customer;
    @Autowired @Qualifier("avro_KafkaTemplate_Student") private  KafkaTemplate<String, Student> avro_KafkaTemplate_student;

    @Value("${app.kafka.topic.generic-topic-name}") String genericTopic;
    @Value("${app.kafka.topic.student-topic-name}") String studentTopic;
    @Value("${app.kafka.topic.customer-topic-name}") String customerTopic;

    // ===========================
    // ✅ producer (generic/String)
    // ===========================
    public void sendGeneric(Object obj)
    {
        try {
            String message = objectMapper.writeValueAsString(obj);
            generic_kafkaTemplate.send(genericTopic, message);
            System.out.println("Student message sent: " + message);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing Student object: " + e.getMessage());
        }
    }

    // ===========================
    // ✅ producer (Avro)
    // ===========================
    // KafkaAvroSerializer automatically registers the schema in the Schema Registry if it doesn’t already exist.
    // curl http://localhost:8081/subjects/customer-topic-value/versions
    // If you send a new version of the schema (e.g., added a field), Schema Registry can enforce compatibility modes (BACKWARD, FORWARD, FULL).

    public void sendCustomer(Customer customer) {
        //String message = objectMapper.writeValueAsString(customer);
        avro_KafkaTemplate_customer.send(customerTopic, customer);
        System.out.println("Customer message sent: " + customer);
    }

    public void sendStudent(Student student)
    {
        //String message = objectMapper.writeValueAsString(student);
        avro_KafkaTemplate_student.send(studentTopic, student);
        System.out.println("Student message sent: " + student);
    }


}
