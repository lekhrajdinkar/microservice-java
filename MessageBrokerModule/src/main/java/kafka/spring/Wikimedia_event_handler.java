package kafka.spring;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

class Wikimedia_event_handler implements EventHandler
{
    KafkaProducer<String, String> kafkaProducer;
    String topic;

    public Wikimedia_event_handler(KafkaProducer<String, String> kafkaProducer, String topic){
        this.kafkaProducer = kafkaProducer;
        this.topic = topic;
    }

    @Override
    public void onOpen() { }

    @Override
    public void onClosed() {
        kafkaProducer.close();
    }

    @Override
    public void onMessage(String event, MessageEvent messageEvent) {
        System.out.println("Error in Stream Reading"+messageEvent.getData());
        // asynchronous
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, messageEvent.getData());
        kafkaProducer.send(producerRecord );
    }

    @Override
    public void onComment(String comment) { }

    @Override
    public void onError(Throwable t) {
        System.out.println("Error in Stream Reading"+t.getMessage());
    }
}

