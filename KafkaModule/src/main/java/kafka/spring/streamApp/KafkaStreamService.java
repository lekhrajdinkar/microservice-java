package kafka.spring.streamApp;

import kafka.spring.dto.StudentJson;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;
import java.util.Arrays;

@Configuration
@EnableKafkaStreams
public class KafkaStreamService {

    @Value("${streamapp.topics.input}")
    private String topicInput;

    @Value("${streamapp.topics.output}")
    private String topicOutput;

    @Bean
    public KStream<String, StudentJson> kStream1_demo(StreamsBuilder builder)
    {
        JsonSerde<StudentJson> studentSerde1 = new JsonSerde<>(StudentJson.class);
        KStream<String, StudentJson> stream1 = builder.stream(
                topicInput,
                Consumed.with(Serdes.String(), studentSerde1)
        );

        // Example: basic filter + peek (side-effect for logging)
        KStream<String, StudentJson> filtered = stream1
                .filter((key, student) -> student != null && student.getName() != null && student.getName().contains("lekhraj"))
                .peek((key, student) -> System.out.println("Key: " + key + ", student: " + student.toString()));

        // Example: further filter into adults / minors
        KStream<String, StudentJson> adults = filtered.filter((k, v) -> v.getAge() >= 18);
        KStream<String, StudentJson> minors = filtered.filter((k, v) -> v.getAge() < 18);

        // Example: mapValues - transform the value while preserving the key
        // Here we simply uppercase the name in-place (for demonstration)
        KStream<String, StudentJson> upperNames = adults.mapValues(s -> {
            s.setName(s.getName() != null ? s.getName().toUpperCase() : null);
            return s;
        });

        // Example: map - change the key to student's name
        KStream<String, StudentJson> keyedByName = upperNames.map((k, v) -> KeyValue.pair(v.getName(), v));

        // Example: flatMapValues - split name into parts
        KStream<String, String> nameParts = keyedByName.flatMapValues(s -> Arrays.asList(s.getName().split("\\s+")));
        nameParts.peek((k, v) -> System.out.println("name-part: key=" + k + " value=" + v));

        // Example: branch - split stream into multiple streams by predicates
        KStream<String, StudentJson>[] branches = stream1.branch(
                (k, v) -> v != null && v.getAge() < 18,   // minors
                (k, v) -> v != null && v.getAge() >= 18   // adults
        );
        KStream<String, StudentJson> minorsBranch = branches[0];
        KStream<String, StudentJson> adultsBranch = branches[1];

        // Example: groupBy / count (stateful). Count students by name.
        KTable<String, Long> counts = stream1
                .map((k, v) -> KeyValue.pair(v.getName(), v))
                .groupByKey(Grouped.with(Serdes.String(), studentSerde1))
                .count(Materialized.as("student-counts-store"));

        // Example: windowed counts (tumbling window of 5 minutes)
        TimeWindows tumbling = TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(5));
        KTable<Windowed<String>, Long> windowedCounts = stream1
                .map((k, v) -> KeyValue.pair(v.getName(), v))
                .groupByKey(Grouped.with(Serdes.String(), studentSerde1))
                .windowedBy(tumbling)
                .count();

        // Note: stream-stream joins and transform/transformValues (for stateful custom logic) are available too.
        // Example (commented): joining with another stream (requires another KStream instance `otherStream`)
        // KStream<String, EnrichedStudent> enriched = stream1.join(
        //     otherStream,
        //     (left, right) -> new EnrichedStudent(left, right.getExtra()),
        //     JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(2)),
        //     StreamJoined.with(Serdes.String(), studentSerde1, otherSerde)
        // );

        // Send a selected stream to output topic (you can choose which stream to send)
        upperNames.to(topicOutput, Produced.with(Serdes.String(), studentSerde1));

        // return the original stream as bean (or any derived stream you prefer)
        return stream1;
    }
}
