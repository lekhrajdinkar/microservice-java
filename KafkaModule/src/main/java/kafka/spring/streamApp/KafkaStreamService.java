package kafka.spring.streamApp;

import kafka.spring.dto.StudentJson;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
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
import org.apache.kafka.streams.state.KeyValueStore;
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
                Consumed.with(Serdes.String(), studentSerde1) //k,v
        );

        //========STATE-LESS OPERATIONS========//

        // ▶ Example: further filter into adults / minors
        KStream<String, StudentJson> adults = stream1.filter((k, v) -> v.getAge() >= 18);
        KStream<String, StudentJson> minors = stream1.filter((k, v) -> v.getAge() < 18);

        // ▶ Example: mapValues - transform the value while preserving the key
        // Here we simply uppercase the name in-place (for demonstration)
        KStream<String, StudentJson> upperNames = adults.mapValues(s -> {
            s.setName(s.getName() != null ? s.getName().toUpperCase() : null);
            return s;
        });

        // ▶ Example: map - change the key to student's name
        KStream<String, StudentJson> keyedByName = upperNames.map((k, v) -> KeyValue.pair(v.getName(), v));

        // ▶ Example: flatMapValues - split name into parts
        KStream<String, String> nameParts = keyedByName.flatMapValues(s -> Arrays.asList(s.getName().split("\\s+")));
        nameParts.peek((k, v) -> System.out.println("name-part: key=" + k + " value=" + v));


        // ▶ Example: branch - split stream into multiple streams by predicates
        KStream<String, StudentJson>[] branches = stream1.branch(
                (k, v) -> v != null && v.getAge() < 18,   // minors
                (k, v) -> v != null && v.getAge() >= 18   // adults
        );
        KStream<String, StudentJson> minorsBranch = branches[0];
        KStream<String, StudentJson> adultsBranch = branches[1];

        //========STATE-FULL OPERATIONS========//

        // ▶️Example:1 group & count (global counts)
        // 1.1 map(new key) > groupByKey > count (stateful)
        KTable<String, Long> counts = stream1
                .map((k, v) -> KeyValue.pair(v.getName(), v))
                .groupByKey(Grouped.with(Serdes.String(), studentSerde1))
                .count(Materialized.as("student-counts-store"));
                // topic : kafkaStreamApp-student-age-counts-store-changelog 👈🏻
        counts.toStream().peek((name, count) -> System.out.println(" 1️⃣Student name: " + name + " has count: " + count));

        // 1.2 groupBy > count (direct way)
        KTable<Integer, Long> counts_2 = stream1
                .groupBy((k,v)-> v.getAge(), Grouped.with(Serdes.Integer(), studentSerde1))
                .count(Materialized.as("student-age-counts-store"));
                // topic : kafkaStreamApp-student-age-counts-store-changelog 👈🏻
        counts_2.toStream().peek((name, count) -> System.out.println("2️⃣Student Age: " + name + " has count: " + count));


        // ▶️Example:2 windowed counts
        // Don't send same Student for next 10 seconds to see windowing effect.
        TimeWindows tumbling = TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(10));
        KStream<Windowed<String>, Long> windowedCounts = stream1
                .map((k, v) -> KeyValue.pair(v.getName(), v))
                .groupByKey(Grouped.with(Serdes.String(), studentSerde1))
                .windowedBy(tumbling)
                .count(Materialized.as("student-name-counts-store-windowed"))
                .toStream()
                .peek((windowedName, count) -> {
                    String name = windowedName.key();
                    long start = windowedName.window().start();
                    long end = windowedName.window().end();
                    System.out.println("3️⃣🕒Windowed Count - Name: " + name + ", Count: " + count + ", Window :" + (end- start));
                    if(count > 1) System.out.println(" ❗❗ Duplicate detected for name: " + name);
                });

        /*
        // Note: stream-stream joins and transform/transformValues (for stateful custom logic) are available too.
        // ▶️Example (commented): joining with another stream (requires another KStream instance `otherStream`)
        KStream<String, EnrichedStudent> enriched = stream1.join(
             otherStream,
             (left, right) -> new EnrichedStudent(left, right.getExtra()),
             JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(2)),
             StreamJoined.with(Serdes.String(), studentSerde1, otherSerde)
         );

        // Send a selected stream to output topic (you can choose which stream to send)
        upperNames.to(topicOutput, Produced.with(Serdes.String(), studentSerde1));
        */

        return stream1;
    }
}
