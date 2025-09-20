package more.rmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
public class RabbitMqConfig
{
    @Value("${rabbit.mq.queue}") String queueName;
    @Value("${rabbit.mq.exchange}") String exchangeName;
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
    Queue queue() {        return  QueueBuilder.durable(queueName).quorum().build();    }

    @Bean
    DirectExchange exchange() {return ExchangeBuilder.directExchange(exchangeName).build();}

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(key);
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
