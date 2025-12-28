package kafka.spring.producerConsumerApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.Collections;

@SpringBootApplication
@Component("kafka.spring")
@EnableAsync
public class kafkaProducerConsumerApp
{
	private ApplicationContext applicationContext;
	public static void main(String[] args)
	{
		SpringApplication app = new SpringApplication(kafkaProducerConsumerApp.class);
		app.setDefaultProperties(Collections.singletonMap("spring.config.location", "classpath:/more/kafka/kafkaProducerConsumerApp.properties"));
		app.run(args);
	}
}