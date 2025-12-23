package kafka.spring;

import kafka.spring.avro.Customer;
import kafka.spring.avro.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/springbootApp/publish-generic/string")
    public String sendStudent_generic( String jsonString)
    {
        //producerService.sendGenericString(jsonString);
        producerService.sendGenericString(temp1);
        //producerService.sendGenericString(temp2);
        return "jsonString :: sent";
    }

    @PostMapping("/springbootApp/publish-generic/object")
    public String sendCustomer_generic( @RequestBody Object obj)
    {
        producerService.sendGenericObject(obj);
        return "Customer/student/etc object :: as message sent" ;
    }

    @PostMapping("/springbootApp/publish-generic/transaction")
    public String sendTransactionalCustomer() {
        return producerService.sendTransactional();
    }

    // ===========================
    // ✅ producer (Avro)
    // ===========================
    @PostMapping("/springbootApp/publish/student")
    public String sendStudent( String id,String name,int age, int count) {
        for (int i = 1; i <= count; i++) {
            Student student = Student.newBuilder().setId(id + "-" + i).setName(name + "-" + i).setAge(age + i).build();
            producerService.sendStudent(student);
        }
        return "Student/s avro :: message sent ✅";
    }
    @PostMapping("/springbootApp/publish/customer")
    public String sendCustomer( String id, String name, int count) {
        for (int i = 1; i <= count; i++) {
            Customer customer = Customer.newBuilder().setCustomerId(id + "-" + i).setCustomerName(name + "-" + i).setEmail(name + i + "@gmail.com").build();
            producerService.sendCustomer(customer);
        }
        return "Customer/s avro :: message sent ✅" ;
    }
}

/* --------------------------------------------------------------

{"type": "generic",    "subtype": "customer",    "payload": {"customerId": "C123", "customerName": "Bob", "email": "bob@example.com"}}
{"type": "generic",    "subtype": "student",    "payload": {"id": "1", "name": "Alice", "age": 22}}

{"customerId": "C123", "customerName": "Bob", "email": "bob@example.com"}
{"id": "1", "name": "Alice", "age": 22}

// ===== App 2 :: wikimedia (in progress)  ======
    @PostMapping("/wikimdeia")
    public String sendCustomer() {
        producerService.sendCustomer("");
        return "wikimdeia stream sent " ;
    }

*/

