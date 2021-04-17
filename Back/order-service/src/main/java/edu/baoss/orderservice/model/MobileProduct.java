package edu.baoss.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="mobile_product")
public class MobileProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mob_prod_id")
    long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", unique = true)
    Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "instance_id", referencedColumnName = "instance_id", unique = true)
    Instance instance;

    @Column(name="tariff_id")
    int tariffId;

    @Column(name="phone_number")
    String phoneNumber;

    @Column(name="balance")
    int balance;

    @Column(name="support_5g")
    boolean support5g;

}
