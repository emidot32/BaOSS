package edu.baoss.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ordr")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    OrderStatus status;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date startDate;

    @Column(name = "completion_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date completionDate;

    @Column(name = "total_cost")
    int totalCost;

    @Column(name = "order_aim", nullable = false)
    String orderAim;

    @Column(name = "workers_num")
    int workersNum;

    @Column(name = "invoice_path")
    String invoicePath;
    
    @Column(name = "order_types")
    String orderTypes;
    
    @OneToMany(mappedBy = "order")
    Set<Task> tasks;
    
    
    
}
