package edu.baoss.orderservice.model;

import edu.baoss.orderservice.model.dtos.OrderValue;
import edu.baoss.orderservice.model.entities.TaskTemplate;
import edu.baoss.orderservice.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationContext;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FulfilmentContext extends ActionContext {
    private TaskTemplate taskTemplate;
    private boolean init;

    public FulfilmentContext(OrderValue orderValue, ApplicationContext applicationContext, TaskTemplate taskTemplate, boolean init) {
        super(orderValue, applicationContext);
        this.taskTemplate = taskTemplate;
        this.init = init;
    }
}
