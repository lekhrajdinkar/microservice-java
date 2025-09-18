package more.kafka.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.Collections;

@SpringBootApplication
@Component("more.kafka.spring")
@EnableAsync
public class kafkaSpringApp
{
	private ApplicationContext applicationContext;
	public static void main(String[] args)
	{
		SpringApplication app = new SpringApplication(kafkaSpringApp.class);
		app.setDefaultProperties(Collections.singletonMap("spring.config.location", "classpath:/more/kafka/kafkaSpringApp.properties"));
		app.run(args);
	}
}