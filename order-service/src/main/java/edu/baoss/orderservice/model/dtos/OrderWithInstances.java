package edu.baoss.orderservice.model.dtos;

import java.util.List;

public class OrderWithInstances {
    OrderDto order;
    List<TaskDto> tasks;
    List<ProductInstance> productInstances;

    public OrderWithInstances() {
    }

    public OrderDto getOrder() {
        return order;
    }

    public void setOrder(OrderDto order) {
        this.order = order;
    }

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }

    public List<ProductInstance> getProductInstances() {
        return productInstances;
    }

    public void setProductInstances(List<ProductInstance> productInstances) {
        this.productInstances = productInstances;
    }
}
