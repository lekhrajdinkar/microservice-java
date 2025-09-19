package more.kafka.spring;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.kafka.listener.ContainerProperties;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaConfig2_avro_customer
{
    @Value("${spring.kafka.bootstrap-servers}") private String bootstrapServers;
    @Value("${spring.kafka.schema-registry-servers}") private String schemaRegistryUrl;
    @Value("${customer.kafka.consumer.manual.offset}") private boolean manualOffset;

    // ===========================
    // Optional topic creation
    // ===========================
    @Bean public NewTopic customerTopic() {
        return new NewTopic("customer-topic", 3, (short) 1);
    }

    // ===========================
    // consumer (avro) <String, Customer>
    // ===========================
    @Bean("avro_KafkaListenerContainerFactory_Customer")
    public ConcurrentKafkaListenerContainerFactory<String, Customer> kafkaListenerContainerFactory()
    {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class); // here ◀️
        config.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
        config.put("specific.avro.reader", true);


        ConcurrentKafkaListenerContainerFactory<String, Customer> factory = new ConcurrentKafkaListenerContainerFactory<>();
        ConsumerFactory<String, Customer> cf =  new DefaultKafkaConsumerFactory<>(config); // Consumer factory ✔️
        factory.setConsumerFactory(cf);

        // ===========================
        // Advanced settings
        // ===========================
        //factory.setBatchListener(true);

        // manual offset commit
        if (manualOffset) {
            factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        } else {
            factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.BATCH);
            // Offsets are committed automatically after the batch of records returned by poll() is processed.
            // Common in log ingestion, analytics, stream pipelines.
            // max.poll.records → maximum number of records returned per poll.
            // fetch.min.bytes, fetch.max.bytes, fetch.max.wait.ms --> control how much data the broker returns.
        }

        // Filtering Messages
        factory.setRecordFilterStrategy(record -> {
            Customer customer = (Customer) record.value();
            if (customer.getEmail() == null) { // skip if email null
                log.warn("Skipping :: since customer email is null : {}", customer);
                return true;
            }
            return false;
        });
        return factory;
    }


    // ===========================
    // producer (avro) <String, Customer>
    // ===========================
    @Bean("avro_KafkaTemplate_Customer")
    public KafkaTemplate<String, Customer> kafkaTemplate()
    {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class); // here ◀️
        config.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

        ProducerFactory<String, Customer> pf =  new DefaultKafkaProducerFactory<>(config); // Producer factory ✔️

        return new KafkaTemplate<>(pf);
    }


}

