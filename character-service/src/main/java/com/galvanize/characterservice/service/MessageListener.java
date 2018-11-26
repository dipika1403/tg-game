package com.galvanize.characterservice.service;

import com.galvanize.tggame.entity.HeroMoveTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);

//    @Autowired
//    CharacterServiceConfigReader characterServiceConfigReader;

    @RabbitListener(queues = "${app.queue.name}")
    public void receiveMessageForApp(final HeroMoveTo heroMoveTo) {
        LOGGER.info("Received message: {} from sender queue.", heroMoveTo);

    }
}
