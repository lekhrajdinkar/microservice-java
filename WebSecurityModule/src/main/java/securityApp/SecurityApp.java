package securityApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collections;

@SpringBootApplication
@Component("securityApp")
public class SecurityApp
{
	private ApplicationContext applicationContext;
	public static void main(String[] args)
	{
		SpringApplication app = new SpringApplication(SecurityApp.class);
		app.setDefaultProperties(Collections.singletonMap("spring.config.location", "classpath:/securityApp/securityApp.properties"));
		app.run(args);
	}
}


