package microservice.basicWebApp.jewelleryApp.Runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

//@Component
@Slf4j
public class RestConsumeRunner_2 implements CommandLineRunner
{
    String url1 = "http://localhost:8083/spring/security/oauth/resource/api-1";
    @Autowired RestTemplate rest;
    //@Autowired    OAuth2TokenServiceImpl OAuth2srv;

    @Override
    public void run(String... args) throws Exception
    {
        HttpHeaders headers = new HttpHeaders();
        //headers.add("Authorization", OAuth2srv.getAccessToken());
        headers.add("Authorization", "token");
        HttpEntity<String> httpEntity_ForGet = new HttpEntity<>(headers);
        ResponseEntity<String> response = rest.exchange(url1, HttpMethod.GET, httpEntity_ForGet, String.class);
        log.info("\nREST-call-1 \nurl : {}, \nresponse : {}",url1, response);
    }
}
