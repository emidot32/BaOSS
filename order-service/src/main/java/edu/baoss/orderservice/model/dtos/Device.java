package edu.baoss.orderservice.model.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
public class Device implements Serializable {
    String id;
    String name;
    String type;
    double price;
    String throughput;
    int portsNum;
    String portTypes;
    int guarantee;
    String standards;
    int memory;
    String frequencies;
    String protocolsAndTechnologies;
}
