package edu.baoss.orderservice.controllers;

import edu.baoss.orderservice.model.dtos.OrderDto;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.model.dtos.OrderWithInstances;
import edu.baoss.orderservice.model.entities.Order;
import edu.baoss.orderservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5555", "http://localhost:4200", "http://tb-api:5555"})
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    public Order createOrder(@RequestBody OrderValue orderValue) throws ParseException {
        System.out.println(orderValue);
        return orderService.orderEntryPoint(orderValue);
    }

    @GetMapping("/{orderId}")
    public OrderWithInstances getOrder(@PathVariable("orderId") long orderId) {
        return orderService.getFullOrder(orderId);
    }

    @GetMapping
    public List<OrderDto> getAllOrders(@RequestParam(value = "userId", required = false) Long userId) {
        return orderService.getAllOrders(userId);
    }

}
