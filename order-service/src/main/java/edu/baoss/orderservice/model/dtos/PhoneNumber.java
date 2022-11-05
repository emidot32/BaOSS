package edu.baoss.orderservice.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumber implements Serializable {
    String id;
    String phoneNumber;
    boolean used;
    String simCardNumber;
    double price;
    String countryCode;
    String pinCode;
    String pukCode;
    String currency;
}
