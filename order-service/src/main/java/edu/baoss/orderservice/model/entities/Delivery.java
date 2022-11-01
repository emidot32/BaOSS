package edu.baoss.orderservice.model.entities;

import edu.baoss.orderservice.model.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import static javax.persistence.CascadeType.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="deliveries")
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

    @OneToOne(cascade={MERGE, REMOVE, REFRESH, DETACH})
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", unique = true)
    Order order;

    @OneToOne(cascade = MERGE)
    @JoinColumn(name = "responsible", referencedColumnName = "empl_id")
    Employee responsible;

    @Column(name = "need_info")
    boolean needInfo;

    @ManyToMany
    @JoinTable(name = "delivery_worker",
            joinColumns = @JoinColumn(name = "delivery_id", referencedColumnName = "delivery_id"),
            inverseJoinColumns = @JoinColumn(name = "empl_id", referencedColumnName = "empl_id"))
    Set<Employee> workers;

    @ManyToOne(cascade = MERGE)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    Address address;

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", deliveryDate=" + deliveryDate +
                ", duration=" + duration +
                ", status=" + status +
                ", needInfo=" + needInfo +
                ", responsible=" + responsible.getId() +
                ", orderId=" + order.getId() +
                '}';
    }
}
