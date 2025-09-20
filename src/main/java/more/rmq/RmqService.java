package more.rmq;

import io.apicurio.registry.serde.avro.AvroKafkaSerializer;
import lombok.extern.slf4j.Slf4j;
import more.rmq.avro.Student;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

import java.util.Arrays;

@Service
@Slf4j
public class RmqService
{
    @Autowired private RabbitTemplate rabbitTemplate;
    @Autowired private AvroKafkaSerializer<Student> serializer_student;

    // =====================
    // ✅ Rabbit MQ producer
    // =====================
    @Value("${rabbit.mq.exchange}") String exchangeName;
    @Value("${rabbit.mq.routingkey}") String key;
    public String send(String msg)
    {
        rabbitTemplate.convertAndSend(exchangeName, key, msg);
        log.info("✅Sent generic message: {}", msg);
        return "✅Sent generic message: "+ msg;
    }

    // -- Student2 (JSON, dto)
    @Value("${rabbit.mq.exchange.student}") String exchangeName_student;
    @Value("${rabbit.mq.routingkey.student}") String key_student;
    public String send(Student2 student2)
    {
        rabbitTemplate.convertAndSend(exchangeName_student, key_student, student2);
        log.info("✅Sent Student2 : {}", student2);
        return "✅Sent Student2: "+ student2;
    }

    // -- Student (avro)
    @Value("${rabbit.mq.queue.student}") String q_student;
    public String send(Student student)
    {
        byte[] data = serializer_student.serialize(q_student, student);
        rabbitTemplate.convertAndSend(exchangeName_student, key_student, data);
        log.info("✅Sent Student_byte : {}", data);
        return "✅Sent Student_byte : "+ Arrays.toString(data);
    }

    // =====================
    // ✅ Rabbit MQ Listener
    // =====================
    @RabbitListener(queues="${rabbit.mq.queue.student}")
    public void receiveMessage(Message message) {
        String body = new String(message.getBody());
        log.info("🟡 RabbitMQ message [ student ] Received :: {}", body);
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
