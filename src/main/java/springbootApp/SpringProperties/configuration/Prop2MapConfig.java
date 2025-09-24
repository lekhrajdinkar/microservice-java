package springbootApp.SpringProperties.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:prop-to-map.yml")
public class Prop2MapConfig {
}


