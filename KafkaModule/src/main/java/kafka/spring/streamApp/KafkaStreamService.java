package kafka.spring.streamApp;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

import static kafka.spring.streamApp.KafkaConfigJsonProducer.TOPIC1;
import static kafka.spring.streamApp.KafkaConfigJsonProducer.TOPIC2;

@Configuration
@EnableKafkaStreams
public class KafkaStreamService {
    // ------------------------------------------
    // ✔️ Stream API - demoCode Snippet (3 steps)
    // ------------------------------------------

    @Bean // not @KafkaListener
    public KStream<String, StudentJson> kStream1_on_genericTopic()
    {
        StreamsBuilder builder = new StreamsBuilder();

        // Use the Spring-provided StreamsBuilder rather than creating a new one
        JsonSerde<StudentJson> studentSerde1 = new JsonSerde<>(StudentJson.class);
        KStream<String, StudentJson> stream1 = builder.stream(
                TOPIC1,
                Consumed.with(Serdes.String(), studentSerde1)
        ); // ▶️1. read from student-topic

        // ▶️2. Stream processing
        stream1
                .filter((key, student) -> student != null && student.getName() != null && student.getName().contains("lekhraj"))
                .peek((key, student) -> System.out.println("Key: " + key + ", student: " + student.toString()));
                //.foreach((key, student) ->  System.out.println("Key: " + key + ", student: " + student.toString()))
        ;
        // ▶️3. write to target topic
        stream1.to(TOPIC2, Produced.with(Serdes.String(), studentSerde1));
        return stream1;
    }
}
