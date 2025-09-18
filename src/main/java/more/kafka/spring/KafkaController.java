package more.kafka.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import more.kafka.spring.avro.Customer;
import more.kafka.spring.avro.Student;

@RestController
@RequestMapping("/kafka")
public class KafkaController
{
    @Autowired private KafkaProducerService producerService;

    // ===========================
    // ✅ producer (generic/String)
    // ===========================
    @PostMapping("/springApp/publish-generic/student")
    public String sendStudent_generic( @RequestBody Student student) {
        producerService.sendGeneric(student);
        return "Student message sent";
    }
    @PostMapping("/springApp/publish-generic/customer")
    public String sendCustomer_generic( @RequestBody Customer customer) {
        producerService.sendGeneric(customer);
        return "Customer message sent" ;
    }

    // ===========================
    // ✅ producer (Avro)
    // ===========================
    @PostMapping("/springApp/publish/student")
    public String sendStudent( @RequestBody Student student) {
        producerService.sendStudent(student);
        return "Student message sent";
    }
    @PostMapping("/springApp/publish/customer")
    public String sendCustomer( @RequestBody Customer customer) {
        producerService.sendCustomer(customer);
        return "Customer message sent" ;
    }

}

/* --------------------------------------------------------------

{"customerId": "C123", "customerName": "Bob", "email": "bob@example.com"}
{"id": "1", "name": "Alice", "age": 22}

    // ===== ✅ App 2 :: wikimedia (in progress)  ======
    @PostMapping("/wikimdeia")
    public String sendCustomer() {
        producerService.sendCustomer("");
        return "wikimdeia stream sent " ;
    }

*/

