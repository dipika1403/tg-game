package com.galvanize.roomservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendMessage(String exchange, String routingKey, Object data) {
//    public void sendMessage(RabbitTemplate rabbitTemplate, String exchange, String routingKey, Object data) {
        LOGGER.info("Sending message to the queue using routingKey {}. Message= {}", routingKey, data);
        rabbitTemplate.convertAndSend(exchange, routingKey, data);
        LOGGER.info("The message has been sent to the queue.");
    }

}
