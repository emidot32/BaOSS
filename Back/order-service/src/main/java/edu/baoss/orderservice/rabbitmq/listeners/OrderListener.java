package edu.baoss.orderservice.rabbitmq.listeners;

import edu.baoss.orderservice.dtos.OrderValue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@EnableRabbit
@Component
public class OrderListener {

    @RabbitListener(queues = "init-order")
    public void processInitOrder(OrderValue orderValue) {
        System.out.println(orderValue);
    }
}
