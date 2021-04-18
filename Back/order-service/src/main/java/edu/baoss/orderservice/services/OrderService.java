package edu.baoss.orderservice.services;

import edu.baoss.orderservice.dtos.OrderValue;
import edu.baoss.orderservice.model.Order;
import edu.baoss.orderservice.model.enums.OrderStatus;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {
    @Autowired
    FeasibilityCheck feasibilityCheck;
    @Autowired
    AmqpTemplate amqpTemplate;

    public Order createOrder(OrderValue orderValue) {
        feasibilityCheck.feasibilityCheck(orderValue);
        amqpTemplate.convertAndSend("init-order", orderValue);
        return Order.builder()
                .id(1)
                .startDate(new Date())
                .status(OrderStatus.ENTERING)
                .build();
    }
}
