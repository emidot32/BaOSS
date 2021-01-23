package edu.baoss.billingservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="billing_account")
public class BillingAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ba_id")
    long id;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    User user;

    @Column(name = "account_number", unique = true)
    String accountNumber;

    @Min(0)
    @Column(name = "balance")
    long balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillingAccount that = (BillingAccount) o;
        return id == that.id &&
                balance == that.balance &&
                Objects.equals(accountNumber, that.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, balance);
    }

    @JsonIgnore
    @Transient
    @OneToMany(mappedBy = "toBillingAccount")
    Set<Payment> received;

    @JsonIgnore
    @Transient
    @OneToMany(mappedBy = "fromBillingAccount")
    Set<Payment> sent;

    @Override
    public String toString() {
        return "BillingAccount{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}
