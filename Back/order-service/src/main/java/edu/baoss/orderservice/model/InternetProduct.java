package edu.baoss.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Set;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="internet_product")
public class InternetProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inet_prod_id")
    long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", unique = true)
    Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "instance_id", referencedColumnName = "instance_id", unique = true)
    Instance instance;

    @Column(name="price_list_id")
    int priceListId;

    @Column(name = "ipv6_support")
    boolean ipv6Support;

    @Column(name = "fixed_ip")
    String fixedId;

    @Column(name = "cable_len")
    int cableLen;

    @Column(name = "device")
    String device;

    @OneToMany(mappedBy = "internetProduct")
    Set<UsedDevice> usedDevices;
}
