package edu.baoss.billingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "from_ba", referencedColumnName = "ba_id")
    BillingAccount fromBillingAccount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "to_ba", referencedColumnName = "ba_id")
    BillingAccount toBillingAccount;

    @Min(1)
    @Column(name = "value", nullable = false)
    long value;

    @Column(name = "payment_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date paymentDate;

    @Column(name="purpose", nullable = false)
    String purpose;
}
