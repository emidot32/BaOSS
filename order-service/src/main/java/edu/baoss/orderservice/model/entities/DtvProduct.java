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
@Table(name="dtv_products")
public class DtvProduct implements ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dtv_prod_id")
    long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "instance_id", referencedColumnName = "instance_id", unique = true)
    Instance instance;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "inet_prod_id", referencedColumnName = "inet_prod_id", unique = true)
    InternetProduct internetProduct;

    @Column(name="dtv_offer_id")
    String dtvOfferId;

}
