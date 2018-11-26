package com.galvanize.characterservice;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Data
public class CharacterServiceConfigReader {

    @Value("${app.exchange.name}")
    private String appExchangeName;

    @Value("${app.queue.name}")
    private String appQueueName;

    @Value("${app.routing.key}")
    private String appRoutingKey;


}
