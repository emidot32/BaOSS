package edu.baoss.orderservice.dtos;

import edu.baoss.orderservice.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
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
    Address selectedAddress;
    BillingAccount selectedAccount;
    String deliveryDateStr;
    String deliveryTime;
    double totalNRC;
    double totalMRC;
    double deliveryPrice;
    ConstantPrices constantPrices;
}
