package edu.baoss.orderservice.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingAccount implements Serializable {
    private long id;

    private UserDto user;

    private String accountNumber;

    private double balance;
}
