package microservice.securityApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig_modern
{
    @Bean
    // ▶️ WebMvcConfigurer : addCorsMappings
    WebMvcConfigurer webMvcConfigurerForApp(){
        return new WebMvcConfigurer()
        {
            // ✅ CORS
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*");
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain_1(HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests(registry -> registry
                .requestMatchers("/swagger-ui/**",
                        "/actuator/**",
                        "/v3/api-docs/**",
                        "/h2-console",
                        "/micrometer/**"
                        //"/**"
                )
                .permitAll()
                .anyRequest()
                .authenticated());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //return (microservice) -> microservice.ignoring().requestMatchers("/ignore1", "/ignore2");
        return (microservice) -> microservice.ignoring().requestMatchers("/not-secured/**", "/not-secured/*");
    }
}


