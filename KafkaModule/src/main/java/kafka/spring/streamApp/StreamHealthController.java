package kafka.spring.streamApp;

import org.apache.kafka.streams.KafkaStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class StreamHealthController {

    private static final Logger log = LoggerFactory.getLogger(StreamHealthController.class);

    @Autowired
    private StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    @GetMapping("/management/streams/health")
    public String health() {
        KafkaStreams ks = streamsBuilderFactoryBean.getKafkaStreams();
        if (ks == null) {
            log.warn("KafkaStreams instance not yet created");
            return "UNKNOWN";
        }
        KafkaStreams.State state = ks.state();
        log.info("KafkaStreams state: {}", state);
        return state.toString();
    }
}

