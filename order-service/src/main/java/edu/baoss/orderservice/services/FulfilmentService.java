package edu.baoss.orderservice.services;

import edu.baoss.orderservice.actions.provisioning.ProvisioningAction;
import edu.baoss.orderservice.model.Flow;
import edu.baoss.orderservice.model.entities.Task;
import edu.baoss.orderservice.model.enums.TaskStatus;
import edu.baoss.orderservice.model.enums.TaskType;
import org.springframework.stereotype.Service;

import java.util.List;

import static edu.baoss.orderservice.model.enums.TaskType.EVENT_LISTENER_TASK;

@Service
public class FulfilmentService {
    public void startFulfilment(Flow executionFlow) {
        boolean breakPointPassed = false;
        for (ProvisioningAction action: executionFlow.getActionList()) {
            Task task = action.getTask();
            if (EVENT_LISTENER_TASK.equals(task.getTaskTemplate().getType())) {
                task.setStatus(TaskStatus.WAITING);
                breakPointPassed = true;
            }
            if (breakPointPassed) {
                action.saveTask();
            } else {
                action.execute();
            }
        }
    }

    public void continueFulfilment(Flow executionFlowAfterDelivery) {
        for (ProvisioningAction action: executionFlowAfterDelivery.getActionList()) {
            action.execute();
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
