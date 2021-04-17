package edu.baoss.orderservice.controllers;

import edu.baoss.orderservice.dtos.OrderValue;
import edu.baoss.orderservice.model.Order;
import edu.baoss.orderservice.model.Task;
import edu.baoss.orderservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:5555", "http://localhost:4200"})
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody OrderValue orderValue) {
        return orderService.createOrder(orderValue);
    }
}
