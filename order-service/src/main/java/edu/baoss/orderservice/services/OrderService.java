package edu.baoss.orderservice.services;

import edu.baoss.orderservice.model.Flow;
import edu.baoss.orderservice.model.dtos.OrderDto;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.model.dtos.OrderWithInstances;
import edu.baoss.orderservice.model.dtos.TaskDto;
import edu.baoss.orderservice.model.entities.Instance;
import edu.baoss.orderservice.model.entities.Order;
import edu.baoss.orderservice.model.entities.Task;
import edu.baoss.orderservice.model.enums.OrderStatus;
import edu.baoss.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements Serializable {
    private final FeasibilityCheckService feasibilityCheckService;
    private final RabbitTemplate template;
    private final OrderRepository orderRepository;
    private final DeliveryService deliveryService;
    private final InstanceService instanceService;
    private final FulfilmentService fulfilmentService;
    private final FlowComposer flowComposer;

    @Transactional
    public Order orderEntryPoint(OrderValue orderValue) throws ParseException {
        feasibilityCheckService.feasibilityCheck(orderValue);
        Order order = createOrderAndInstance(orderValue);
        orderValue.setOrder(order);
        //throw new RuntimeException();
        //template.convertAndSend(Constants.ORDER_FULFILMENT_EXCHANGE, Constants.START, orderValue);
        Flow executionFlow = flowComposer.createExecutionFlow(orderValue);
        fulfilmentService.startFulfilment(executionFlow);
        return order;
    }

    @Transactional
    public Order createOrderAndInstance(OrderValue orderValue) throws ParseException {
        String productsString = String.join(";", orderValue.getSelectedProducts());
        Order order = orderRepository.save(Order.builder()
                .startDate(new Date())
                .status(OrderStatus.PROCESSING)
                .products(productsString)
                .orderAim(orderValue.getOrderAim())
                .totalMRC(orderValue.getTotalMRC())
                .totalNRC(orderValue.getTotalNRC())
                .userId(orderValue.getUserId())
                .workersNum(0)
                .build());
        boolean neededDelivery = orderValue.getDeliveryTime() != null;
        if (neededDelivery) {
            deliveryService.createDelivery(orderValue, order);
        }
        Instance instance = instanceService.createInstances(orderValue);
        order.setInstance(instance);
        return order;
    }

    public List<OrderDto> getAllOrders(Long userId) {
        return (userId == null
            ? orderRepository.findAll()
            : orderRepository.getOrdersByUserId(userId))
                .stream()
                .sorted(Comparator.comparingLong(Order::getId))
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }

    public OrderWithInstances getFullOrder(long orderId) {
        OrderWithInstances orderWithInstances = new OrderWithInstances();
        orderRepository.findById(orderId)
                .ifPresent(order -> {
                    orderWithInstances.setOrder(new OrderDto(order));
                    orderWithInstances.setTasks(order.getTasks().stream()
                            .sorted(Comparator.comparingLong(Task::getId))
                            .map(TaskDto::new)
                            .collect(Collectors.toList()));
                    orderWithInstances.setProductInstances(
                            instanceService.getProductInstancesForInstance(order.getInstance(), true));
                });
        return orderWithInstances;
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

}
