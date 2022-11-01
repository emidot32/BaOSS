package edu.baoss.orderservice.actions.provisioning;

import edu.baoss.orderservice.model.FulfilmentContext;
import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.model.entities.Task;
import edu.baoss.orderservice.model.enums.TaskStatus;
import edu.baoss.orderservice.repositories.TaskRepository;
import org.springframework.context.ApplicationContext;

import java.util.Date;

public abstract class ProvisioningAction {
    protected Task task;
    protected OrderValue orderValue;
    protected ApplicationContext applicationContext;

    public ProvisioningAction(FulfilmentContext fulfilmentContext) {
        this.orderValue = fulfilmentContext.getOrderValue();
        this.applicationContext = fulfilmentContext.getApplicationContext();
        if (fulfilmentContext.isInit()) {
            this.task = Task.builder()
                    .taskTemplate(fulfilmentContext.getTaskTemplate())
                    .status(TaskStatus.NOT_STARTED)
                    .order(orderValue.getOrder())
                    .build();
        }
    }

    public void execute() {
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setStartDate(new Date());
        saveTask();
        performAction();
        task.setCompletionDate(new Date());
        task.setStatus(TaskStatus.COMPLETED);
        saveTask();
    }

    public abstract boolean instantiationCondition();

    protected abstract void performAction();

    public Task getTask() { return task; }

    public Task saveTask() {
        return applicationContext.getBean(TaskRepository.class).save(task);
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setOrderValue(OrderValue orderValue) {
        this.orderValue = orderValue;
    }
}
