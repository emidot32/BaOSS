package edu.baoss.orderservice.model.dtos;

import edu.baoss.orderservice.model.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderValue implements Serializable {
    long userId;
    String[] selectedProducts;
    PhoneNumber selectedPhoneNumber;
    Tariff selectedTariff;
    boolean support5g;
    boolean deliveryAndActivationMobile;
    InternetOffer selectedSpeed;
    Device selectedDevice;
    DtvOffer selectedChannelNumber;
    boolean fixedIpSupport;
    boolean installation;
    int cableLength;
    double cablePriceTotal;
    AddressDto selectedAddress;
    String deliveryDateStr;
    String deliveryTime;
    double totalNRC;
    double totalMRC;
    double deliveryPrice;
    ConstantPrices constantPrices;
    String orderAim;
    Order order;
    String macAddress;
    long deliveryId;
}
