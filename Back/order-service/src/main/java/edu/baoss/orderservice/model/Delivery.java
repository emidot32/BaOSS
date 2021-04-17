package edu.baoss.orderservice.model;

import edu.baoss.orderservice.model.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    long id;

    @Column(name = "delivery_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date deliveryDate;

    @Column(name = "duration")
    int duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    DeliveryStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", unique = true)
    Order order;

    @ManyToMany
    @JoinTable(name = "delivery_worker",
            joinColumns = @JoinColumn(name = "delivery_id", referencedColumnName = "delivery_id"),
            inverseJoinColumns = @JoinColumn(name = "empl_id", referencedColumnName = "empl_id"))
    Set<Employee> workers;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    Address address;    
}
