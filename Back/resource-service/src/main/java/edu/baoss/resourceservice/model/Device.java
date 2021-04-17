package edu.baoss.resourceservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "devices")
public class Device {
    @Id
    private String id;
    private String name;
    private String type;
    private double price;
    private String throughput;
    @Field("ports_num")
    private int portsNum;
    @Field("ports_types")
    private String portTypes;
    private boolean[] used;
    @Field("for_sale")
    private boolean forSale;
    private int guarantee;
    private String standards;
    private int memory;
    @Field("mac_addresses")
    private String[] macAddresses;
    @Field("serial_numbers")
    private String[] serialNumbers;
    private String frequencies;
    @Field("protocols_and_technologies")
    private String protocolsAndTechnologies;
}
