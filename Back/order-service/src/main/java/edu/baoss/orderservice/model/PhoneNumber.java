package edu.baoss.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumber {
    String id;
    String phoneNumber;
    boolean used;
    String simCardNumber;
    double price;
    String countryCode;
    String pinCode;
    String pukCode;
}
