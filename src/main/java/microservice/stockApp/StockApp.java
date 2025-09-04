package microservice.stockApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.Collections;

@SpringBootApplication
@Component("microservice.stockApp")
@EnableAsync
public class StockApp
{
	private ApplicationContext applicationContext;
	public static void main(String[] args)
	{
		SpringApplication app = new SpringApplication(StockApp.class);
		app.setDefaultProperties(Collections.singletonMap("spring.config.location", "classpath:/microservice/stockApp/stockApp.properties")); //way-1
		app.run(args);
	}
}