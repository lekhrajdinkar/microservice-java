package more.kafka.spring;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map;

@Configuration
public class KafkaConfig
{
    @Value("${spring.kafka.bootstrap-servers}") private String bootstrapServers;

    @Bean("generic_KafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        ConsumerFactory<String, String> cf =  new DefaultKafkaConsumerFactory<>(config); // Producer factory ✔️
        factory.setConsumerFactory(cf);
        return factory;
    }

    @Bean("generic_KafkaTemplate")
    public KafkaTemplate<String, String> kafkaTemplate() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        ProducerFactory<String, String> pf =  new DefaultKafkaProducerFactory<>(config); // Producer factory ✔️
        return new KafkaTemplate<>(pf);
    }



    // Optionally auto-create topics if not present
    @Bean
    public NewTopic genericTopic() {
        return new NewTopic("generic-topic", 3, (short) 1);
    }

    // Enable/Disable listener bean via application.properties
    @Bean
    public KafkaConsumerService kafkaConsumerService() {
        return new KafkaConsumerService();
    }
}

