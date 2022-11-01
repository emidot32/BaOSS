package edu.baoss.billingservice.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.CascadeType.*;
import static javax.persistence.CascadeType.DETACH;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    long id;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "from_ba", referencedColumnName = "ba_id")
//    BillingAccount fromBillingAccount;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "to_ba", referencedColumnName = "ba_id")
//    BillingAccount toBillingAccount;

    @Column(name = "external_id")
    String externalId;

    @ManyToOne(cascade={MERGE, REMOVE, REFRESH, DETACH})
    @JoinColumn(name = "from_user", referencedColumnName = "user_id")
    User user;

    @Column(name = "value", nullable = false)
    double value;

    @Column(name = "payment_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date paymentDate;

    @Column(name="purpose", nullable = false)
    String purpose;
}
