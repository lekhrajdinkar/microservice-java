package microservice.shoppingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component("microservice.shoppingApp")
@EnableAsync
public class ShoppingApp
{
	private ApplicationContext applicationContext;
	public static void main(String[] args)
	{
		SpringApplication app = new SpringApplication(ShoppingApp.class);
		app.run(args);
	}
}