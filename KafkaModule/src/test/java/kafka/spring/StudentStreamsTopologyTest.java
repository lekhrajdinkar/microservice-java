package kafka.spring;

import kafka.spring.streamApp.StudentJson;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.test.TestRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class StudentStreamsTopologyTest
{
    private TopologyTestDriver testDriver;
    private JsonSerde<StudentJson> studentSerde;

    @BeforeEach
    public void setup()
    {
        StreamsBuilder builder = new StreamsBuilder();
        studentSerde = new JsonSerde<>(StudentJson.class);

        builder.stream("stream-app-student-topic", org.apache.kafka.streams.kstream.Consumed.with(Serdes.String(), studentSerde))
                .filter((k, v) -> v != null && v.getName() != null && v.getName().toLowerCase().contains("lekhraj"))
                .mapValues(v -> {
                    if (v.getName() != null) v.setName(v.getName().toUpperCase());
                    return v;
                })
                .to("stream-app-student-topic-processed", org.apache.kafka.streams.kstream.Produced.with(Serdes.String(), studentSerde));

        Topology topology = builder.build();

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:9092");

        testDriver = new TopologyTestDriver(topology, props);
    }

    @AfterEach
    public void tearDown() {
        testDriver.close();
    }

    @Test
    public void testFilterAndTransform()
    {
        TestInputTopic<String, StudentJson> input = testDriver.createInputTopic(
                "student-topic", Serdes.String().serializer(), studentSerde.serializer());

        TestOutputTopic<String, StudentJson> output = testDriver.createOutputTopic(
                "student-topic-processed", Serdes.String().deserializer(), studentSerde.deserializer());

        StudentJson s1 = new StudentJson();
        s1.setName("lekhraj kumar");
        input.pipeInput("key1", s1);

        assertFalse(output.isEmpty());
        var rec = output.readKeyValue();
        assertEquals("LEKHRAJ KUMAR", rec.value.getName());
    }
}
