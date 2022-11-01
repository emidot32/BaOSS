package edu.baoss.orderservice.model.entities;

import edu.baoss.orderservice.model.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="internet_products")
public class InternetProduct implements ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inet_prod_id")
    long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "instance_id", referencedColumnName = "instance_id", unique = true)
    Instance instance;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", unique = true)
    Address address;

    @Column(name="internet_offer_id")
    String internetOfferId;

    @Column(name = "ipv6_support")
    boolean ipv6Support;

    @Column(name = "fixed_ip")
    String fixedIp;

    @Column(name = "cable_len")
    int cableLen;

    @Column(name = "device")
    String deviceId;

    @OneToMany(mappedBy = "internetProduct")
    Set<UsedDevice> usedDevices;

}
