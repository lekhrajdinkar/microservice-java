package more.rmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class RmqService
{
    @Autowired private RabbitTemplate rabbitTemplate;

    // =====================
    // ✅ Rabbit MQ producer
    // =====================
    @Value("${rabbit.mq.exchange}") String exchangeName;
    @Value("${rabbit.mq.routingkey}") String key;

    public String send(String msg) {
        rabbitTemplate.convertAndSend(exchangeName, key, msg);
        log.info("✅Sent message: {}", msg);
        return "✅Sent message: "+ msg;
    }

    public String send(Student student) {
        rabbitTemplate.convertAndSend(exchangeName, key, student);
        log.info("✅Sent Student : {}", student);
        return "✅Sent Student: "+ student;
    }

    // =====================
    // ✅ Rabbit MQ Listener
    // =====================
    //@RabbitListener(queues="${rabbit.mq.queue}")
    public void receiveMessage(String message) {
        log.info("RabbitMQ message Received :: {}", message);
    }

    // -- Advance --
    //basicAck  → tell broker message is done.
    //basicNack → reject + don’t requeue (so it can go to DLQ).
    @RabbitListener(queues = "${rabbit.mq.queue}", ackMode = "MANUAL")
    public void receive(Message message, Channel channel) throws Exception
    {
        long tag = message.getMessageProperties().getDeliveryTag();
        String body = new String(message.getBody());
        try {
            log.info("Processing: {}", body);
            if (body.contains("fail"))
                throw new RuntimeException("Simulated failure");

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //✅
        }
        catch (Exception e) {
            int retries = (int) message.getMessageProperties()
                    .getHeaders()
                    .getOrDefault("x-death-count", 0);

            if (retries >= 3) {
                System.err.println("Message failed after retries, sending to DLQ: " + body);
                channel.basicNack(tag, false, false); // ❌ send to DLQ
            } else {
                System.err.println("Retry attempt failed: " + body);
                throw e; // let Spring Retry handle backoff & requeue ◀️
            }
        }
    }
}
