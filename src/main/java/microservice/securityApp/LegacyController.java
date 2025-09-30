package microservice.securityApp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/v1")
public class LegacyController
{
    //=========================
    // Authentication
    //=========================
    @GetMapping("/basic-auth/admin")
    public String basicAuth_admin() { return "ADMIN :: secured-api-1";}

    @GetMapping("/basic-auth/user")
    public String basicAuth_user() { return "USER :: secured-api-2";}

    @GetMapping("/not-secured/test-redirect")
    public ResponseEntity<String> m3() {
          ResponseEntity httpResponse2 = ResponseEntity
                .status(302)
                .header("location", "https://auth0.com/intro-to-iam/what-is-oauth-2")
                .body("redirect");
        return httpResponse2;
    }

}
