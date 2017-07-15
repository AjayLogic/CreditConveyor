package com.dyplom.repository;

import com.dyplom.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("paymentRepository")
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
