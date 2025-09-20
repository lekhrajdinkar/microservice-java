package more.rmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RmqController
{
    @Autowired private RmqService srv;

    @GetMapping("/RmqSpringApp/send/string")
    public String send(String msg) {
       return srv.send(msg);
    }

    @GetMapping("/RmqSpringApp/send/string")
    public String sendStudent(String id, String name, int age) {
        Student s = Student.builder()
                .id(id)
                .name(name)
                .age(age)
                .build();
        return srv.send(s);
    }

    @GetMapping("/RmqSpringApp/test/retry")
    public String sendFail() {
        return srv.send("fail");
    }
}
