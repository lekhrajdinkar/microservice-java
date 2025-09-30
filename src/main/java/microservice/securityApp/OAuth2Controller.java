package microservice.securityApp;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/OAuth2")
public class OAuth2Controller
{
    @Autowired OAuth2ServiceImpl oAuth2Service;

    @GetMapping("/ignore1")    public String ignore1() { return "ignore1 called";}
    @GetMapping("/ignore2")    public String ignore2() { return "ignore2 called";}

    // ▶️ client-credential
    @Operation(description = "regId - cc (client_credential).  \nReturns only access token in m2m")
    @GetMapping("/get-access-token/cc")
    public String m2m() {
        return oAuth2Service.getAccessToken("cc");
    }

    // ▶️ Auth Flow
    @Operation(description = "regId - af_pkce (Authorization_flow with PKCE). \nMocking ng app behaviour. \nReturns access and id token")
    @GetMapping("/get-tokens/af_pkce")
    public String af_pkce() {
        return oAuth2Service.getAccessToken("af_pkce");
    }

    @GetMapping("/validate/tokens")
    public String validateToken() {
        String accessToken = oAuth2Service.getAccessToken("cc");
        String idToken = ""; //Todo - validate ID token

        return "token received, validation pending... todo";
    }

    /*
    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal OidcUser user) {
        return "Hello, " + user.getFullName() + " (" + user.getEmail() + ")";
    }
    */

}
