package more.kafka.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import more.kafka.spring.avro.Customer;
import more.kafka.spring.avro.Student;

import java.util.concurrent.CompletableFuture;

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

    // ==========================================
    // ✅ producer (generic - String/object)
    // ===========================================
    public void sendGenericObject(Object obj)
    {
        try {
            String message = objectMapper.writeValueAsString(obj); // Convert object to JSON string first
            generic_kafkaTemplate.send(genericTopic, message);
            System.out.println("generic :: objectMapper.writeValueAsString(obj) :: sent ✅" + message);
        } catch (JsonProcessingException e) {
            System.err.println("Error sendGenericObject : " + e.getMessage());
        }
    }

    public void sendGenericString(String jsonString)
    {
        generic_kafkaTemplate.send(genericTopic, jsonString);
        System.out.println("Generic jsonString message sent ✅ " + jsonString);
    }

    public String  sendTransactional() {
        generic_kafkaTemplate.executeInTransaction(ops -> {
            ops.send("generic-topic", "dummy-message-step-1");
            ops.send("generic-topic", "dummy-message-step-2");
            //ops.send("generic-topic-2", "dummy-message-step-3");
            // ...
            return null;
        });
        return "Transactional dummy message/s sent to multiple topics!";
    }

    // ===========================
    // ✅ producer (Avro)
    // ===========================
    // KafkaAvroSerializer automatically registers the schema in the Schema Registry if it doesn’t already exist.
    // curl http://localhost:8081/subjects/customer-topic-value/versions
    // If you send a new version of the schema (e.g., added a field), Schema Registry can enforce compatibility modes (BACKWARD, FORWARD, FULL).

    public void sendCustomer(Customer customer) {
        //String message = objectMapper.writeValueAsString(customer);
        CompletableFuture<SendResult<String, Customer>> future = avro_KafkaTemplate_customer.send(customerTopic, customer);
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.err.println("Failed to send message: " + ex.getMessage());
            } else {
                System.out.println("Message sent successfully with offset: " + result.getRecordMetadata().offset());
            }
        });
    }

    public void sendStudent(Student student)
    {
        //String message = objectMapper.writeValueAsString(student);
        ProducerRecord<String, Student> record = new ProducerRecord<>(studentTopic, String.valueOf(System.currentTimeMillis()), student);
        record.headers().add("source", "kafkaSpringApp".getBytes()); // ◀️
        record.timestamp(); // ◀️

        CompletableFuture<SendResult<String, Student>> future = avro_KafkaTemplate_student.send(record);

        //avro_KafkaTemplate_student.send(studentTopic, student);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.err.println("Failed to send message: " + ex.getMessage());
            } else {
                System.out.println("Message sent successfully with offset: " + result.getRecordMetadata().offset());
            }
        });
    }

}
