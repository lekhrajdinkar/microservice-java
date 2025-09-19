package more.kafka.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import more.kafka.spring.avro.Customer;
import more.kafka.spring.avro.Student;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@ConditionalOnProperty(
        name = "app.kafka.consumer.kafka-generic-consumer-group.enabled",
        havingValue = "true",
        matchIfMissing = false
    )
public class KafkaConsumerService
{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ExecutorService executor = Executors.newFixedThreadPool(3);

    // ===============================================
    // ✅ Generic/String Consumer for multiple topics
    // ===============================================
    @KafkaListener(
            topics = { "${app.kafka.topic.generic-topic-name}" },
            groupId = "kafka-generic-consumer-group",
            containerFactory = "generic_KafkaListenerContainerFactory"
    )
    public void genericConsume(String message) {
        try {
            log.info("generic message received 🟡: " + message);
        } catch (Exception e) {
            System.err.println("Error processing message 🟡: " + e.getMessage());
        }
    }


    // ===========================================
    // ✅ Avro Consumer for multiple topics
    // ==========================================
    // ------------------------------------------
    // ✔️ Customer (2, regular + batch) - keep one at a time: comment one
    // ------------------------------------------
    /* Disabled
    @KafkaListener(
            topics = { "${app.kafka.topic.customer-topic-name}" },
            groupId = "customer-avro-consumer-group", // "avro-consumer-group",
            containerFactory = "avro_KafkaListenerContainerFactory_Customer"
    ) */
    public void consumeCustomer(
            @Payload Customer customer,
            Acknowledgment ack,
            @Header("source") String source, // Consuming Headers ◀️
            @Header(KafkaHeaders.RECEIVED_KEY) String key
        )
    {
            // process in another thread
            executor.submit(() -> {
            try {
                log.info("Consumed: {} with key={} source={}", customer, key, source);
            } finally {
                ack.acknowledge(); // commit after processing ◀️
            }
        });
    }


    // enabled
    @ConditionalOnProperty(name = "customer.kafka.consumer.in.batch", havingValue = "true")
    @KafkaListener(
            topics = { "${app.kafka.topic.customer-topic-name}" },
            groupId = "customer-avro-consumer-group", // "avro-consumer-group",
            containerFactory = "avro_KafkaListenerContainerFactory_batch_Customer", // batch-enabled factory ◀️,
            concurrency = "2", //◀️ max = 3
            properties = {
                    "spring.kafka.listener.type=batch",
                    "max.poll.records=5"  // max records per poll. lower value would kill throughput
            }
    )
    public void consumeCustomer_batch(
            List<ConsumerRecord<String, Customer>> records,
            Acknowledgment acknowledgment
    )
    {
        for (ConsumerRecord<String, Customer> record : records) {
            log.info("----- Record Metadata -----");
            log.info("Topic     : {}", record.topic());
            log.info("Partition : {}", record.partition());
            log.info("Offset    : {}", record.offset());
            log.info("Key       : {}", record.key());
            log.info("Timestamp : {}", record.timestamp());

            log.info("Headers   : ");
            record.headers().forEach(header ->
                    log.info("   {} = {}", header.key(), new String(header.value()))
            );

            log.info("Value     : {}", record.value());
            log.info("----------------------------");
        }
        acknowledgment.acknowledge();
    }



    // ------------------------------------------
    // ✔️ Student (2, parallelism) : both enabled
    // ------------------------------------------
    @KafkaListener(
            topics = { "${app.kafka.topic.student-topic-name}" },
            groupId = "student-avro-consumer-group", // "avro-consumer-group",
            containerFactory = "avro_KafkaListenerContainerFactory_Student"
    )
    public void consumeStudent_1(@Payload Student student) {
        log.info("Consumed Avro Student: " + student);
    }

    @KafkaListener(
            topics = { "${app.kafka.topic.student-topic-name}" },
            groupId = "student-avro-consumer-group", // "avro-consumer-group",
            containerFactory = "avro_KafkaListenerContainerFactory_Student"
    )
    public void consumeStudent_2(@Payload Student student) {
        log.info("Consumed Avro Student: " + student);
    }
}
