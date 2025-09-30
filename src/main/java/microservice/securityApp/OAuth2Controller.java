package microservice.securityApp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/OAuth2")
public class OAuth2Controller
{

    @GetMapping("/get/access-token")
    public String m0() { return "ADMIN :: secured-api-1";}

    @GetMapping("/validate/access-token")
    public String m1() { return "ADMIN :: secured-api-1";}

    @GetMapping("/validate/id-token")
    public String m2() { return "USER :: secured-api-2";}

}
