package more.kafka.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
public class KafkaProducerController
{
    @Autowired private KafkaProducerService producerService;
    String topic = "customer_student";
    String topic_wikimedia = "wikimedia";

    // ===== ✅ Student App ======
    @PostMapping("/student")
    public String sendStudent( @RequestBody Student student) {
        producerService.sendStudent(topic, student);
        return "Student message sent to topic: " + topic;
    }

    @PostMapping("/customer")
    public String sendCustomer( @RequestBody Customer customer) {
        producerService.sendCustomer(topic, customer);
        return "Customer message sent to topic: " + topic;
    }

    // ===== ✅ wikimedia App ======
    @PostMapping("/wikimdeia")
    public String sendCustomer() {
        //producerService.sendCustomer(topic_wikimedia);
        return "wikimdeia stream sent to " + topic_wikimedia;
    }
}

/*

{"customerId": "C123", "customerName": "Bob", "email": "bob@example.com"}
{"id": "1", "name": "Alice", "age": 22}

 */

