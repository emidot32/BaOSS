package edu.baoss.billingservice.model.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {
    long id;
    long userId;
    long instanceId;
    String status;
    String startDateStr;
    String completionDateStr;
    double totalNRC;
    double totalMRC;
    String orderAim;
    int workersNum;

}
