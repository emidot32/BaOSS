package edu.baoss.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="dtv_product")
public class DtvProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dtv_prod_id")
    long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", unique = true)
    Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "instance_id", referencedColumnName = "instance_id", unique = true)
    Instance instance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inet_prod_id", referencedColumnName = "inet_prod_id", unique = true)
    InternetProduct internetProduct;

    @Column(name="price_list_id")
    int priceListId;
}
