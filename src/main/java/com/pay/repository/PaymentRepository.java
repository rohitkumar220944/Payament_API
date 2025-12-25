package com.pay.repository;

 // New package: repository

import com.pay.entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentRecord, Long> {
    
    // Essential for lookup during Webhook processing
    Optional<PaymentRecord> findByStripePaymentIntentId(String stripePaymentIntentId);
}