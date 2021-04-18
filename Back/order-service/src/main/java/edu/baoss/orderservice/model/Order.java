package edu.baoss.orderservice.model;

import edu.baoss.orderservice.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="ordr")
public class Order implements Serializable {
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

    @Column(name = "total_mrc")
    double totalMRC;

    @Column(name = "total_nrc")
    double totalNRC;

    @Column(name = "order_aim", nullable = false)
    String orderAim;

    @Column(name = "workers_num")
    int workersNum;

    @Column(name = "invoice_path")
    String invoicePath;
    
    @Column(name = "products")
    String products;
    
    @OneToMany(mappedBy = "order")
    Set<Task> tasks;
    
    
    
}
