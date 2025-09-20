- https://www.rabbitmq.com/tutorials

---
## run rmq locally
```bash
docker run -d --hostname my-rabbit --name some-rabbit -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```
```bash
cd ./../../../../../src/main/resources/more/rmq
docker-compose -f docker-compose.yml up -d
```

- RMQ Management console UI : http://localhost:15672/#/ ✅

![img.png](img.png)

---
## RmqSpringApp
-  `AMQP 0-9-1` protocol
- https://chatgpt.com/c/68ce348e-9b24-8330-8faf-6036616d5006 🤖
- API docs: http://localhost:8095/RmqSpringApp/swagger-ui/index.html
- props : [RmqSpringApp.properties](../../../resources/more/rmq/RmqSpringApp.properties)
- main class : [RmqSpringApp.java](../java/more/rmq/RmqSpringApp.java)
  - run : `mvn spring-boot:run -Dspring-boot.run.profiles=rmq`
  - test : [RmqSpringAppTest.java](../java/more/rmq/RmqSpringAppTest.java)

## Advance
### Consumer
- **1 Manual ack/nack**
```java
@RabbitListener(queues = "${rabbit.mq.queue}", ackMode = "MANUAL")
public void receive(Message message, Channel channel) throws Exception
{
  try {
    String body = new String(message.getBody());
    log.info("Processing: {}", body);

    if (body.contains("fail")) throw new RuntimeException("Simulated failure");
    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //✅
  } catch (Exception e) {
    log.error("Error: {}", e.getMessage());
    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false); // ❌
  }
 } 
```
- **2 concurrency + prefetch** 
  - each consumer(2) only gets 5 unacked messages max)
```properties
spring.rabbitmq.listener.simple.prefetch=5
spring.rabbitmq.listener.simple.concurrency=2
```

- **3 dead letter exchange (DLX)**


- ttl
- dlx + ttl = delay queue
- dlx + ttl + retry
- dlx + ttl + retry + parking lot
- dlx + ttl + retry + parking lot + jms header
- dlx + ttl + retry + parking lot + jms header + json
- dlx + ttl + retry + parking lot + jms header + json + batch
- dlx + ttl + retry + parking lot + jms header + json + batch + multi consumer
- dlx + ttl + retry + parking lot + jms header + json + batch + multi consumer + multi queue
- dlx + ttl + retry + parking lot + jms header + json + batch + multi consumer + multi queue + multi exchange