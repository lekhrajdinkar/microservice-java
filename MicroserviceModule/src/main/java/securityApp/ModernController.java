package securityApp;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping
public class ModernController
{
    @Autowired OAuth2ServiceImpl oAuth2Service;
    @Value("${spring.security.oauth2.client.registration.cc.scope}") String scope_cc;

    //======================
    // ✅ PART-1 : get Token
    //======================
    @GetMapping("/not-secured/test-api-1")    public String ignore1() { return "test-api-1 security ignored";}

    // ▶️ client-credential
    @Operation(
            summary = "regId - cc",
            description = "Returns only access token in m2m"
    )
    @GetMapping("/not-secured/get-access-token/cc")
    public String m2m() {
        return oAuth2Service.getAccessToken_and_parse("cc");
    }

    // ▶️ Auth Flow... not working yet. will do same from ng app
    @Operation(
            summary = "regId - af_pkce",
            description = "Mocking ng app behaviour. Returns access and id token"
    )
    @GetMapping("/not-secured/get-tokens/af_pkce")
    public String af_pkce() {
        return oAuth2Service.getAccessToken_and_parse("af_pkce");
    }

    //============================================
    // ✅ PART-2 : Use Token in Resource API
    //============================================
    @GetMapping("/secured/resource-1")  String resource1(@AuthenticationPrincipal Jwt jwt) {
        return "Resource-1 accessed by subject: " + jwt.getSubject();
    }

    @PreAuthorize("hasAuthority('SCOPE_app_read_lekhraj')")
    @GetMapping("/secured/resource-2/200")  String resource2_scope_200(@AuthenticationPrincipal Jwt jwt) {
        jwt.getClaims().forEach((k,v)->log.info(" ▶️ {} : {}", k,v));
        return "accessToken with scp [ app_read_lekhraj ] can access resource-2";
    }

    @Operation(description = "SCOPE_app_read_lekhraj_2 not exist, so will fail ❌")
    @PreAuthorize("hasAuthority('SCOPE_app_read_lekhraj_2')")
    @GetMapping("/secured/resource-2/403")  String resource2_scope_403() {
        return "accessToken with scp [ app_read_lekhraj ] can access resource-2";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') and #jwt.claims['location'] == 'Irvine'")
    @GetMapping("/secured/resource-3")  String resource3_rbac() {
        return "only admin can access resource-3";
    }

    /*
    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal OidcUser user) {
        return "Hello, " + user.getFullName() + " (" + user.getEmail() + ")";
    }
    */

}
