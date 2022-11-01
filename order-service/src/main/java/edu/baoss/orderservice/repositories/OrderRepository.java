package edu.baoss.orderservice.repositories;

import edu.baoss.orderservice.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Set<Order> getOrdersByUserId(long userId);
}
