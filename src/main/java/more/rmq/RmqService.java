package more.rmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@Slf4j
public class RmqService
{
    @Autowired private RabbitTemplate rabbitTemplate;

    @Value("${rabbit.mq.queue}") String queueName;
    @Value("${rabbit.mq.exchange}") String exchangeName;
    @Value("${rabbit.mq.routingkey}") String key;

    public String send() {
        rabbitTemplate.convertAndSend(exchangeName, key, "hello lekhu");
        return "Message sent: ";
    }
}
