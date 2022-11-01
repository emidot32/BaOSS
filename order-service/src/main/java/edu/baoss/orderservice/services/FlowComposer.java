package edu.baoss.orderservice.services;

import edu.baoss.orderservice.actions.provisioning.ProvisioningAction;
import edu.baoss.orderservice.actions.provisioning.common.*;
import edu.baoss.orderservice.actions.provisioning.dtv.ActivateDtvChannelsAction;
import edu.baoss.orderservice.actions.provisioning.internet.*;
import edu.baoss.orderservice.actions.provisioning.mobile.Activate5GAction;
import edu.baoss.orderservice.actions.provisioning.mobile.NotifyUserAction;
import edu.baoss.orderservice.actions.provisioning.mobile.ReserveSimCardAction;
import edu.baoss.orderservice.actions.provisioning.mobile.WaitingDeliverymanConfirmationAction;
import edu.baoss.orderservice.model.Flow;
import edu.baoss.orderservice.model.FulfilmentContext;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.model.entities.Task;
import edu.baoss.orderservice.model.entities.TaskTemplate;
import edu.baoss.orderservice.model.enums.TaskType;
import edu.baoss.orderservice.repositories.TaskTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;

import static edu.baoss.orderservice.Constants.*;

@Service
@RequiredArgsConstructor
public class FlowComposer {
    @Lazy
    private final ApplicationContext applicationContext;

    private final TaskTemplateRepository taskTemplateRepository;

    private static final Map<String, Function<FulfilmentContext, ProvisioningAction>> TASK_NAME_TO_ACTION = new HashMap<>() {{
            put(INIT_ORDER_TASK_NAME, InitializeOrderAction::new);
            put(RESERVE_SIM_CARD_TASK_NAME, ReserveSimCardAction::new);
            put(RESERVE_CABLE_TASK_NAME, ReserveCableAction::new);
            put(RESERVE_DEVICE_TASK_NAME, ReserveDeviceAction::new);
            put(NOTIFY_USER_TASK_NAME, NotifyUserAction::new);
            put(NOTIFY_FITTER_TASK_NAME, NotifyFitterOrDeliverymanAction::new);
            put(ACTIVATE_5G_TASK_NAME, Activate5GAction::new);
            put(WAITING_FITTER_CONFIRMATION_TASK_NAME, WaitingFitterConfirmationAction::new);
            put(WAITING_DELIVERYMAN_CONFIRMATION_TASK_NAME, WaitingDeliverymanConfirmationAction::new);
            put(WAITING_MAC_ADDRESS_TASK_NAME, WaitingMacAddressAction::new);
            put(CONNECT_TO_NETWORK_TASK_NAME, ConnectToNetworkAction::new);
            put(ACTIVATE_DTV_CHANNELS_TASK_NAME, ActivateDtvChannelsAction::new);
            put(PROVIDE_FIXED_IP_TASK_NAME, ProvideFixedIpAction::new);
            put(NRC_PAYMENT_TASK_NAME, GetPaymentAction::new);
            put(COMPLETE_ORDER_TASK_NAME, CompleteOrderAction::new);
        }};

    public Flow createExecutionFlow(OrderValue orderValue) {
        Map<String, Task> nameToTask = new HashMap<>();
        List<TaskTemplate> all = taskTemplateRepository.findAll();
        List<ProvisioningAction> actions = all.stream()
                .filter(tt -> Arrays.stream(orderValue.getSelectedProducts())
                        .anyMatch(product -> tt.getProducts().contains(product)))
                .map(tt -> TASK_NAME_TO_ACTION.get(tt.getName()).apply(new FulfilmentContext(orderValue, applicationContext, tt, true)))
                .filter(ProvisioningAction::instantiationCondition)
                .peek(action -> nameToTask.put(action.getTask().getTaskTemplate().getName(), action.getTask()))
                .toList();
        actions.forEach(action -> action.getTask().getTaskTemplate().getDependencies()
                .forEach(dep -> {
                    if (nameToTask.get(dep.getName()) != null) {
                        action.getTask().addDependency(nameToTask.get(dep.getName()));
                    }
                })
        );
        //System.out.println(actions);
        return new Flow(actions);
    }

    public Flow getActionsAfterDelivery(OrderValue orderValue) {
        List<ProvisioningAction> executionFlowAfterDelivery = new ArrayList<>();
        for (Task task: orderValue.getOrder().getTasks()) {
            if (BooleanUtils.toBoolean(task.getTaskTemplate().getParams().get(AFTER_DELIVERY))) {
                FulfilmentContext context = new FulfilmentContext(orderValue, applicationContext, task.getTaskTemplate(), false);
                ProvisioningAction action = TASK_NAME_TO_ACTION.get(task.getTaskTemplate().getName()).apply(context);
                action.setTask(task);
                executionFlowAfterDelivery.add(action);
            }
        }
        return new Flow(executionFlowAfterDelivery);
    }

    private ProvisioningAction createProvisioningAction(TaskTemplate taskTemplate, OrderValue orderValue) {
        try {
            FulfilmentContext fulfilmentContext = new FulfilmentContext(orderValue, applicationContext, taskTemplate, true);
            return (ProvisioningAction) Class.forName(taskTemplate.getActionName()).getConstructor(FulfilmentContext.class).newInstance(fulfilmentContext);
        } catch (NoSuchMethodException | ClassNotFoundException | InvocationTargetException
                 | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Impossible to create " + taskTemplate.getActionName() + " object due to: " + e);
        }
    }

    private boolean isOneOf(TaskType type, TaskType ... taskTypes) {
        for (TaskType taskType: taskTypes) {
            if (taskType.equals(type))
                return true;
        }
        return false;
    }

}
