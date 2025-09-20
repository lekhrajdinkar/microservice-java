package more.rmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RmqController
{
    @Autowired private RmqService srv;

    @GetMapping("/RmqSpringApp/send")
    public String send(String msg) {
       return srv.send(msg);
    }
}
