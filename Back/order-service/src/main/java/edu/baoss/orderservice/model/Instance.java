package edu.baoss.orderservice.model;

import edu.baoss.orderservice.model.enums.InstanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="instance")
public class Instance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instance_id")
    long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    InstanceStatus status;

    @Column(name = "activated_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date activatedDate;

    @Column(name = "disconnected_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date disconnectedDate;

    @Column(name = "expired_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date expiredDate;


}
