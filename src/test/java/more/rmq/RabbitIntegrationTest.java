package more.rmq;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
class RabbitIntegrationTest {

    static RabbitMQContainer rabbit =
            new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.11-management"));

    static {
        rabbit.start();
        System.setProperty("spring.rabbitmq.host", rabbit.getHost());
        System.setProperty("spring.rabbitmq.port", rabbit.getAmqpPort().toString());
        System.setProperty("spring.rabbitmq.username", rabbit.getAdminUsername());
        System.setProperty("spring.rabbitmq.password", rabbit.getAdminPassword());
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //@Test
    void testSendAndReceive() {
        String message = "hello testcontainers";
        rabbitTemplate.convertAndSend("demo.exchange", "demo.routing", message);

        String received = (String) rabbitTemplate.receiveAndConvert("demo.queue", 5000);
        assertThat(received).isEqualTo(message);
    }
}

