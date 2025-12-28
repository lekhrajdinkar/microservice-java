package kafka.spring.streamApp;

import kafka.spring.dto.StudentJson;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
@EnableKafkaStreams
public class KafkaStreamService {

    @Value("${streamapp.topics.input}")
    private String topicInput;

    @Value("${streamapp.topics.output}")
    private String topicOutput;

    @Bean
    public KStream<String, StudentJson> kStream1_on_genericTopic(StreamsBuilder builder)
    {
        JsonSerde<StudentJson> studentSerde1 = new JsonSerde<>(StudentJson.class);
        KStream<String, StudentJson> stream1 = builder.stream(
                topicInput,
                Consumed.with(Serdes.String(), studentSerde1)
        );

        stream1
                .filter((key, student) -> student != null && student.getName() != null && student.getName().contains("lekhraj"))
                .peek((key, student) -> System.out.println("Key: " + key + ", student: " + student.toString()));

        stream1.to(topicOutput, Produced.with(Serdes.String(), studentSerde1));
        return stream1;
    }
}
