package edu.baoss.billingservice.repositories;

import edu.baoss.billingservice.model.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
