package edu.baoss.orderservice.model.entities;

import edu.baoss.orderservice.model.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="mobile_products")
public class MobileProduct implements ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mob_prod_id")
    long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "instance_id", referencedColumnName = "instance_id", unique = true)
    Instance instance;

    @Column(name="tariff_id")
    String tariffId;

    @Column(name="phone_number")
    String phoneNumber;

    @Column(name="balance")
    int balance;

    @Column(name="support_5g")
    boolean support5g;

}
