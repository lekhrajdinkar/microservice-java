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
    @PostMapping("/springApp/publish-generic/string")
    public String sendStudent_generic( String jsonString)
    {
        String temp1 = "{\n" +
                "\t\"id\": \"1\",\n" +
                "\t\"name\": \"Alice\",\n" +
                "\t\"age\": 22\n" +
                "}";

        String temp2 = "{\n" +
                "\t\"customerId\": \"123\",\n" +
                "\t\"customerName\": \"lekhraj\",\n" +
                "\t\"email\": \"lekhraj@gmail.com\"\n" +
                "}";

        producerService.sendGenericString(temp1);
        return "Student message sent";
    }

    @PostMapping("/springApp/publish-generic/object")
    public String sendCustomer_generic( Object obj)
    {
        producerService.sendGenericObject(obj);
        return "Customer/student object message sent" ;
    }

    // ===========================
    // ✅ producer (Avro)
    // ===========================
    @PostMapping("/springApp/publish/student")
    public String sendStudent( String id,String name,int age) {
        Student student = Student.newBuilder().setId(id).setName(name).setAge(age).build();
        producerService.sendStudent(student);
        return "Student message sent";
    }
    @PostMapping("/springApp/publish/customer")
    public String sendCustomer( String id, String name) {
        Customer customer = Customer.newBuilder() .setCustomerId(id).setCustomerName(name).setEmail(name + "@gmail.com").build();
        producerService.sendCustomer(customer);
        return "Customer message sent" ;
    }

}

/* --------------------------------------------------------------

{
	"id": "string",
	"name": "string",
	"age": 0
}

{
	"customerId": "string",
	"customerName": "string",
	"email": "string"
}

{"customerId": "C123", "customerName": "Bob", "email": "bob@example.com"}
{"id": "1", "name": "Alice", "age": 22}

    // ===== ✅ App 2 :: wikimedia (in progress)  ======
    @PostMapping("/wikimdeia")
    public String sendCustomer() {
        producerService.sendCustomer("");
        return "wikimdeia stream sent " ;
    }

*/

