package edu.baoss.billingservice.repositories;

import edu.baoss.billingservice.model.entities.BillingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingAccountRepository extends JpaRepository<BillingAccount, Long> {
}
