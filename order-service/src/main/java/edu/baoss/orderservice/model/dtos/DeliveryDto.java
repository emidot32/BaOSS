package edu.baoss.orderservice.model.dtos;

import edu.baoss.orderservice.model.entities.Address;
import edu.baoss.orderservice.model.entities.Delivery;
import edu.baoss.orderservice.model.enums.DeliveryStatus;
import edu.baoss.orderservice.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class DeliveryDto implements Serializable {
    long id;
    String deliveryDateStr;
    int duration;
    DeliveryStatus status;
    long orderId;
    AddressDto address;
    String products;
    DeliveryAdditionalInfo additionalInfo;
    boolean needInfo;
    boolean responsible;
    boolean deliveryStarted;


    public DeliveryDto(Delivery delivery, DeliveryAdditionalInfo additionalInfo, boolean responsible, boolean deliveryStarted) {
        id = delivery.getId();
        deliveryDateStr = Constants.DATE_AND_TIME_FORMAT.format(delivery.getDeliveryDate());
        duration = delivery.getDuration();
        status = delivery.getStatus();
        orderId = delivery.getOrder().getId();
        needInfo = delivery.isNeedInfo();
        address = new AddressDto(delivery.getAddress());
        this.responsible = responsible;
        this.deliveryStarted = deliveryStarted;
        this.products = delivery.getOrder().getProducts();
        this.additionalInfo = additionalInfo;
    }
}
