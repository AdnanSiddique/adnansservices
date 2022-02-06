package com.adnan.amqp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public record RabbitMqMessageProducer(RabbitTemplate rabbitTemplate) {

    public void publish(Object payload, String exchange, String routingKey) {
        log.info("publishing to {}, using routing {}, payload {}: ", exchange, routingKey, payload);
        rabbitTemplate.convertAndSend(exchange, routingKey, payload);
        log.info("published to {}, using routing {}, payload {}: ", exchange, routingKey, payload);
    }
}
