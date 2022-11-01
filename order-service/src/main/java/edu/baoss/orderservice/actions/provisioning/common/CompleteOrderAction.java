package edu.baoss.orderservice.actions.provisioning.common;

import edu.baoss.orderservice.actions.provisioning.ProvisioningAction;
import edu.baoss.orderservice.model.FulfilmentContext;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.model.entities.Instance;
import edu.baoss.orderservice.model.entities.Order;
import edu.baoss.orderservice.model.entities.Task;
import edu.baoss.orderservice.model.enums.InstanceStatus;
import edu.baoss.orderservice.model.enums.OrderStatus;
import edu.baoss.orderservice.model.enums.TaskStatus;
import edu.baoss.orderservice.model.enums.TaskType;
import edu.baoss.orderservice.repositories.TaskRepository;
import edu.baoss.orderservice.services.InstanceService;
import edu.baoss.orderservice.services.OrderService;

import java.util.Date;

import static edu.baoss.orderservice.Constants.COMPLETE_ORDER_TASK_DESCRIPTION;
import static edu.baoss.orderservice.Constants.COMPLETE_ORDER_TASK_NAME;

public class CompleteOrderAction extends ProvisioningAction {
    OrderService orderService;
    InstanceService instanceService;

    public CompleteOrderAction(FulfilmentContext context) {
        super(context);
        this.orderService = context.getApplicationContext().getBean(OrderService.class);
        this.instanceService = context.getApplicationContext().getBean(InstanceService.class);
    }

    @Override
    protected void performAction() {
        Order order = orderValue.getOrder();
        Date completionDate = new Date();
        order.setCompletionDate(completionDate);
        order.setStatus(OrderStatus.COMPLETED);
        Instance instance = order.getInstance();
        instance.setActivatedDate(completionDate);
        instance.setStatus(InstanceStatus.ACTIVE);
        orderService.saveOrder(order);
        instanceService.saveInstance(instance);
    }

    @Override
    public boolean instantiationCondition() {
        return true;
    }
}
