package edu.baoss.orderservice.rabbitmq.listeners;

import edu.baoss.orderservice.Constants;
import edu.baoss.orderservice.model.Flow;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.services.FlowComposer;
import edu.baoss.orderservice.services.FulfilmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@EnableRabbit
@Component
@RequiredArgsConstructor
public class EventListener {
    private final FulfilmentService fulfilmentService;
    private final FlowComposer flowComposer;


    @RabbitListener(queues = Constants.START_ORDER_FULFILMENT_QUEUE)
    public void startOrderFulfilment(OrderValue orderValue) {
        Flow executionFlow = flowComposer.createExecutionFlow(orderValue);
        fulfilmentService.startFulfilment(executionFlow);
    }

    @RabbitListener(queues = Constants.CONTINUE_ORDER_FULFILMENT_QUEUE)
    public void continueOrderFulfilment(OrderValue orderValue) {
        Flow executionFlowAfterDelivery = flowComposer.getActionsAfterDelivery(orderValue);
        fulfilmentService.continueFulfilment(executionFlowAfterDelivery);
    }

//    Set<Task> orderTasks = executionFlow.stream()
//            .filter(ProvisioningAction.class::isInstance)
//            .map(ProvisioningAction.class::cast)
//            .map(ProvisioningAction::getTask)
//            .collect(Collectors.toSet());
//        orderValue.getOrder().setTasks(orderTasks);
//        orderService.saveOrder(orderValue.getOrder());
}
