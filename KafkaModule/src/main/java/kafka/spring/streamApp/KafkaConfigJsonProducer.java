package kafka.spring.streamApp;

import kafka.spring.dto.StudentJson;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfigJsonProducer {

    @Value("${streamapp.topics.input}")
    private String topicInput;

    @Value("${streamapp.topics.output}")
    private String topicOutput;

    @Bean
    public NewTopic studentTopic1() {
        return new NewTopic(topicInput, 3, (short) 1);
    }

    @Bean
    public NewTopic studentTopic2() {
        return new NewTopic(topicOutput, 3, (short) 1);
    }

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, StudentJson> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, StudentJson> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    public String getTopicInput() { return topicInput; }
    public String getTopicOutput() { return topicOutput; }
}
