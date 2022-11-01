package edu.baoss.orderservice.model.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryAdditionalInfo {
    String macAddress;
    String deviceSerialNumber;
    String simCardNumber;
    String cableLength;
}
