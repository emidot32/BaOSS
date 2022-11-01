package edu.baoss.orderservice.model.dtos;

import edu.baoss.orderservice.model.entities.Order;
import edu.baoss.orderservice.model.enums.OrderStatus;
import edu.baoss.orderservice.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {
    long id;
    long userId;
    long instanceId;
    OrderStatus status;
    String startDateStr;
    String completionDateStr;
    double totalNRC;
    double totalMRC;
    String orderAim;
    int workersNum;

    public OrderDto(Order order) {
        id = order.getId();
        completionDateStr = order.getCompletionDate() == null
                        ? "" : Constants.DATE_AND_TIME_FORMAT.format(order.getCompletionDate());
        startDateStr = order.getStartDate() == null
                        ? "" : Constants.DATE_AND_TIME_FORMAT.format(order.getStartDate());
        instanceId = order.getInstance().getId();
        orderAim = order.getOrderAim();
        status = order.getStatus();
        totalMRC = order.getTotalMRC();
        totalNRC = order.getTotalNRC();
        userId = order.getUserId();
        workersNum = order.getWorkersNum();
    }
}
