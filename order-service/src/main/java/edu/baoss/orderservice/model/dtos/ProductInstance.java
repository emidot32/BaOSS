package edu.baoss.orderservice.model.dtos;

import edu.baoss.orderservice.model.entities.Instance;
import edu.baoss.orderservice.model.enums.InstanceStatus;
import edu.baoss.orderservice.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ProductInstance {
    long id;
    long instanceId;
    long userId;
    InstanceStatus status;
    String activatedDateStr;
    String disconnectedDateStr;
    String expiredDate;
    String product;

}
