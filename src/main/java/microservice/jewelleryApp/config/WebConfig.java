package microservice.jewelleryApp.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import microservice.jewelleryApp.custom.servlet_filter_listener.MyServlet;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebConfig implements WebApplicationInitializer
{

    @Bean // for spring MVC
    WebMvcConfigurer webMvcConfigurerForApp(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*");
            }
        };
    }

    //@Bean
    WebServerFactoryCustomizer webServerfactory() // FunctionalInterface
    {
        return new WebServerFactoryCustomizer<TomcatServletWebServerFactory>(){
            @Override
            public void customize(TomcatServletWebServerFactory factory) {
                factory.setPort(8083);  // Change the port to 8081
                factory.setContextPath("/spring");  // Set the context path
            }
        };
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("AppName", "SB_99_RESTful_API");
        // register servlet,filter,webListener
    }

    @Bean
    public ServletRegistrationBean<MyServlet> myServletRegistrationBean() {
        ServletRegistrationBean<MyServlet> registrationBean =new ServletRegistrationBean<>(new MyServlet(), "/custom");
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }

    /*
    ==============================
    Change from Tomcat to jetty.
    ==============================
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jetty</artifactId>
    </dependency>

    @Bean
    public JettyEmbeddedServletContainerFactory  jettyEmbeddedServletContainerFactory() {
        JettyEmbeddedServletContainerFactory jettyContainer = new JettyEmbeddedServletContainerFactory();
        jettyContainer.setPort(9000);
        jettyContainer.setContextPath("/springbootapp");
        return jettyContainer;
    }
    */
}


