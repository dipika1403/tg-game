package com.galvanize.characterservice;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@SpringBootApplication
@EnableRabbit
@Data
public class CharacterServiceApplication implements RabbitListenerConfigurer {

    @Autowired
    CharacterServiceConfigReader characterServiceConfigReader;

    public static void main(String[] args) {
        SpringApplication.run(CharacterServiceApplication.class, args);
    }
    @Bean
    public TopicExchange getAppExchange() {
        return new TopicExchange(getCharacterServiceConfigReader().getAppExchangeName());
    }

    @Bean
    public Queue getAppQueue() {
        return new Queue(getCharacterServiceConfigReader().getAppQueueName());
    }

    @Bean
    public Binding declareBindingApp() {
        return BindingBuilder.bind(getAppQueue()).to(getAppExchange()).with(getCharacterServiceConfigReader().getAppRoutingKey());
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
}
