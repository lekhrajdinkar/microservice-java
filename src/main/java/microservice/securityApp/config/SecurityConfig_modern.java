package microservice.securityApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
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
    @Value("${spring.security.oauth2.client.registration.cc.scope}") String scope_cc;

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
                ).permitAll()
                .anyRequest().authenticated());

        // ▶️ Option-1 : withDefaults
        // http.oauth2ResourceServer(OAuthRSConfigurer -> OAuthRSConfigurer.jwt(Customizer.withDefaults()));

        // ▶️ Option-2 : Custom Jwt Converter... throws exceptions if scope missing...
        http.oauth2ResourceServer(OAuthRSConfigurer -> OAuthRSConfigurer.jwt(jwtConfigurer -> jwtConfigurer
                .jwtAuthenticationConverter(new CustomJwtAuthenticationConverter(scope_cc))
        ));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        //return (microservice) -> microservice.ignoring().requestMatchers("/ignore1", "/ignore2");
        return (microservice) -> microservice.ignoring().requestMatchers("/not-secured/**", "/not-secured/*");
    }
}


