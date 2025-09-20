package more.rmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RmqConfig
{
    @Value("${rabbit.mq.queue}") String queueName;
    @Value("${rabbit.mq.exchange}") String exchangeName;

    @Value("${rabbit.mq.queue.dlq}") String queueName_dlq;
    @Value("${rabbit.mq.exchange.dlx}") String exchangeName_dlx;

    @Value("${rabbit.mq.routingkey}") String key;
    @Value("${spring.rabbitmq.username}") String username;
    @Value("${spring.rabbitmq.password}") String password;

    //=================================================
    // ✅ ConnectionFactory, RabbitTemplate, MessageConverter
    //=================================================
    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    //comment out to use application.properties ◀️
    //@Bean
    public ConnectionFactory rmqConnectionfactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUsername(username);
        factory.setPassword(password);
        factory.setUri("amqps://localhost:5672");
        factory.setVirtualHost("/");
        return factory;
    }

    //=================================================
    // ✅  Queue, Exchange, Binding
    //=================================================
    @Bean
    Queue queue() {        return  QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", exchangeName_dlx)   // send to DLX after nack/ttl
                .withArgument("x-dead-letter-routing-key", queueName_dlq) // ◀️ queueName_dlq as routing key ??
                .quorum()
                .build();
    }

    @Bean
    DirectExchange exchange() {return ExchangeBuilder.directExchange(exchangeName).build();}

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(key);
    }

    // --- DLQ --
    @Bean
    Queue deadLetterQueue() {        return new Queue(queueName_dlq, true);    }
    @Bean
    DirectExchange deadLetterExchange() {        return new DirectExchange(exchangeName_dlx, true, false);    }
    @Bean
    Binding deadLetterBinding(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(queueName_dlq); // ◀️ queueName_dlq as routing key ??
    }

    //=================================================
    // ✅  others
    //=================================================
    //@Bean
    CommandLineRunner rabbitMQtest(RabbitTemplate rabbitTemplate){
        return (args)->{
            rabbitTemplate.convertAndSend(exchangeName, key, "{message}", m->m);
            log.info("Rabbit MQ test message sent at startup");
        };
    }
}
