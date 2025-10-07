package rmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.Collections;

@SpringBootApplication
@Component("more.rmq")
@EnableAsync
public class RmqSpringApp
{
	private ApplicationContext applicationContext;
	public static void main(String[] args)
	{
		SpringApplication app = new SpringApplication(RmqSpringApp.class);
		app.setDefaultProperties(Collections.singletonMap("spring.config.location", "classpath:/more/rmq/RmqSpringApp.properties"));
		app.run(args);
	}
}