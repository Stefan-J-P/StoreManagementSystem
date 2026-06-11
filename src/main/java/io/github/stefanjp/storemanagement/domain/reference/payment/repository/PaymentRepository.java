package io.github.stefanjp.storemanagement.domain.reference.payment.repository;

import io.github.stefanjp.storemanagement.domain.reference.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

