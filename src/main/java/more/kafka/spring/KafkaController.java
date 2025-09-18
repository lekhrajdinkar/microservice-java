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
    String temp1  = "{\"type\": \"generic\",    \"subtype\": \"student\",    \"payload\": {\"id\": \"1\", \"name\": \"Alice\", \"age\": 22}}";
    String temp2  = "{\"type\": \"generic\",    \"subtype\": \"customer\",    \"payload\": {\"customerId\": \"C123\", \"customerName\": \"Bob\", \"email\": \"bob@example.com\"}}";
    @PostMapping("/springApp/publish-generic/string")
    public String sendStudent_generic( String jsonString)
    {
        producerService.sendGenericString(jsonString);
        return "jsonString :: sent";
    }

    @PostMapping("/springApp/publish-generic/object")
    public String sendCustomer_generic( Object obj)
    {
        producerService.sendGenericObject(obj);
        return "Customer/student/etc object :: as message sent" ;
    }

    @PostMapping("/springApp//publish-generic/transaction")
    public String sendTransactionalCustomer() {
        return producerService.sendTransactional();
    }

    // ===========================
    // ✅ producer (Avro)
    // ===========================
    @PostMapping("/springApp/publish/student")
    public String sendStudent( String id,String name,int age) {
        Student student = Student.newBuilder().setId(id).setName(name).setAge(age).build();
        producerService.sendStudent(student);
        return "Student avro :: message sent";
    }
    @PostMapping("/springApp/publish/customer")
    public String sendCustomer( String id, String name) {
        Customer customer = Customer.newBuilder() .setCustomerId(id).setCustomerName(name).setEmail(name + "@gmail.com").build();
        producerService.sendCustomer(customer);
        return "Customer avro :: message sent" ;
    }
}

/* --------------------------------------------------------------

{"type": "generic",    "subtype": "customer",    "payload": {"customerId": "C123", "customerName": "Bob", "email": "bob@example.com"}}
{"type": "generic",    "subtype": "student",    "payload": {"id": "1", "name": "Alice", "age": 22}}

{"customerId": "C123", "customerName": "Bob", "email": "bob@example.com"}
{"id": "1", "name": "Alice", "age": 22}

// ===== ✅ App 2 :: wikimedia (in progress)  ======
    @PostMapping("/wikimdeia")
    public String sendCustomer() {
        producerService.sendCustomer("");
        return "wikimdeia stream sent " ;
    }

*/

