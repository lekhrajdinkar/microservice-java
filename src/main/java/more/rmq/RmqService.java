package more.rmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        log.info("Sent message: {}", msg);
        return "Sent message: "+ msg;
    }

    // =====================
    // ✅ Rabbit MQ Listener
    // =====================
    @RabbitListener(queues="${rabbit.mq.queue}")
    public void receiveMessage(String message) {
        log.info("RabbitMQ message Received :: {}", message);
    }
}
