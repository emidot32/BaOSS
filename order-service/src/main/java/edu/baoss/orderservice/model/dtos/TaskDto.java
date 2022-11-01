package edu.baoss.orderservice.model.dtos;

import edu.baoss.orderservice.model.entities.Task;
import edu.baoss.orderservice.model.enums.TaskStatus;
import edu.baoss.orderservice.model.enums.TaskType;
import edu.baoss.orderservice.Constants;
import lombok.Data;

@Data
public class TaskDto {
    long id;
    String name;
    TaskType type;
    String description;
    TaskStatus status;
    String startDateStr;
    String completionDateStr;

    public TaskDto(Task task) {
        id = task.getId();
        name = task.getTaskTemplate().getName();
        type = task.getTaskTemplate().getType();
        description = task.getTaskTemplate().getDescription();
        status = task.getStatus();
        startDateStr = task.getStartDate() == null 
                ? "" : Constants.DATE_AND_TIME_FORMAT.format(task.getStartDate());
        completionDateStr = task.getCompletionDate() == null
                ? "" : Constants.DATE_AND_TIME_FORMAT.format(task.getCompletionDate());
    }
}
