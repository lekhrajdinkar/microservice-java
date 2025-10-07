package modernWebApp.shoppingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.Collections;

@SpringBootApplication
@Component("microservice.modernWebApp.shoppingApp")
@EnableAsync
public class ShoppingApp
{
	private ApplicationContext applicationContext;
	public static void main(String[] args)
	{
		SpringApplication app = new SpringApplication(ShoppingApp.class);
		app.setDefaultProperties(Collections.singletonMap("spring.config.location", "classpath:/microservice/modernWebApp/ShoppingApp.properties"));
		app.run(args);
	}
}