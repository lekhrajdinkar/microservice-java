package microservice.courseApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import java.util.Collections;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class CourseApp
{
	public static void main(String[] args)
	{
		System.out.println("JAVA BACKEND --- START");

		SpringApplication app = new SpringApplication(CourseApp.class);
		app.setDefaultProperties(Collections.singletonMap("spring.config.location", "classpath:/microservice/courseApp/courseApp.properties"));
		app.run(args);

		System.out.println("JAVA BACKEND --- END");
	}
}
