package edu.baoss.orderservice.model.entities;

import edu.baoss.orderservice.model.enums.InstanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="instances")
public class Instance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instance_id")
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InstanceStatus status;

    @Column(name = "activated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activatedDate;

    @Column(name = "disconnected_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date disconnectedDate;

    @Column(name = "expired_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredDate;

//    @Column(name = "ba_id")
//    private long billingAccountId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instance instance = (Instance) o;
        return id == instance.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
