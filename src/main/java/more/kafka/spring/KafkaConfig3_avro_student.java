package more.kafka.spring;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import more.kafka.spring.avro.Customer;
import more.kafka.spring.avro.Student;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig3_avro_student
{
    @Value("${spring.kafka.bootstrap-servers}") private String bootstrapServers;
    @Value("${spring.kafka.schema-registry-servers}") private String schemaRegistryUrl;

    // ===========================
    // Optional topic creation
    // ===========================
    @Bean public NewTopic studentTopic() {
        return new NewTopic("student-topic", 3, (short) 1);
    }

    // ===========================
    // consumer (avro) <String, Student>
    // ===========================
    @Bean("avro_KafkaListenerContainerFactory_Student")
    public ConcurrentKafkaListenerContainerFactory<String, Student> kafkaListenerContainerFactory()
    {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class); // here ◀️
        config.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

        ConcurrentKafkaListenerContainerFactory<String, Student> factory = new ConcurrentKafkaListenerContainerFactory<>();
        ConsumerFactory<String, Student> cf =  new DefaultKafkaConsumerFactory<>(config); // Consumer factory ✔️
        factory.setConsumerFactory(cf);

        return factory;
    }
    

    // ===========================
    // producer (avro) <String, Student>
    // ===========================
    @Bean("avro_KafkaTemplate_Student")
    public KafkaTemplate<String, Student> kafkaTemplate()
    {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class); // here ◀️
        config.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

        ProducerFactory<String, Student> pf =  new DefaultKafkaProducerFactory<>(config); // Producer factory ✔️

        return new KafkaTemplate<>(pf);
    }


}

