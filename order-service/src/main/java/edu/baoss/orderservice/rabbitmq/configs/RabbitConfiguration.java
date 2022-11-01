package edu.baoss.orderservice.rabbitmq.configs;

import edu.baoss.orderservice.Constants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfiguration {

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange orderFulfilmentExchange() {
        return new TopicExchange(Constants.ORDER_FULFILMENT_EXCHANGE);
    }

    @Bean
    public Binding startOrderFulfilmentBinding() {
        return BindingBuilder.bind(startFulfilmentQueue()).to(orderFulfilmentExchange()).with(Constants.START);
    }

    @Bean
    public Binding continueOrderFulfilmentBinding() {
        return BindingBuilder.bind(continueFulfilmentQueue()).to(orderFulfilmentExchange()).with(Constants.CONTINUE);
    }

    @Bean
    public Queue startFulfilmentQueue() {
        return new Queue(Constants.START_ORDER_FULFILMENT_QUEUE);
    }

    @Bean
    public Queue continueFulfilmentQueue() { return new Queue(Constants.CONTINUE_ORDER_FULFILMENT_QUEUE); }

}
