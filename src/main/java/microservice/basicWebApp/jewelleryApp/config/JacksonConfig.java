package microservice.basicWebApp.jewelleryApp.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import microservice.basicWebApp.jewelleryApp.custom.Serializer.LocalDateTimeSerializer;

@Configuration
public class JacksonConfig
{
    @Bean
    public ObjectMapper objectMapper()
    {
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(new LocalDateTimeSerializer());  // notice : new

        ObjectMapper objectMapper =  new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(module)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
    

    @Bean
    LocalDateTimeSerializer localDateTimeSerializer(){
        return new LocalDateTimeSerializer();
    }
}

