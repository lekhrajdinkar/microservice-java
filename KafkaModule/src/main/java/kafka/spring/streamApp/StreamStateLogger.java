package kafka.spring.streamApp;

import org.apache.kafka.streams.KafkaStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class StreamStateLogger implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(StreamStateLogger.class);

    @Autowired
    private StreamsBuilderFactoryBean streamsBuilderFactoryBean;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Register a state listener on the factory bean so we get callbacks even if KafkaStreams
        // is created after the context refresh.
        try {
            streamsBuilderFactoryBean.setStateListener((newState, oldState) -> {
                log.info("KafkaStreams state transitioned from {} to {}", oldState, newState);
            });
        } catch (Exception e) {
            log.warn("Failed to set state listener on StreamsBuilderFactoryBean: {}", e.getMessage());
        }

        // If KafkaStreams instance is already available, log its current state
        try {
            KafkaStreams ks = streamsBuilderFactoryBean.getKafkaStreams();
            if (ks != null) {
                log.info("KafkaStreams current state at refresh time: {}", ks.state());
            } else {
                log.debug("KafkaStreams instance not yet created at context refreshed time");
            }
        } catch (Exception e) {
            log.warn("Error while obtaining KafkaStreams instance: {}", e.getMessage());
        }
    }
}
