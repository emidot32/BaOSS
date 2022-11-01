package edu.baoss.orderservice.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.baoss.orderservice.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    long id;

    @Column(name = "user_id")
    long userId;

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

    @Column(name = "products")
    String products;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.MERGE)
    @JsonIgnore
    Set<Task> tasks;

    @ManyToOne(cascade={MERGE, REMOVE, REFRESH, DETACH})
    @JoinColumn(name = "instance_id", referencedColumnName = "instance_id")
    Instance instance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", status=" + status +
                ", startDate=" + startDate +
                ", completionDate=" + completionDate +
                ", totalMRC=" + totalMRC +
                ", totalNRC=" + totalNRC +
                ", orderAim='" + orderAim + '\'' +
                ", workersNum=" + workersNum +
                ", products='" + products + '\'' +
                ", instance=" + instance +
                '}';
    }
}
