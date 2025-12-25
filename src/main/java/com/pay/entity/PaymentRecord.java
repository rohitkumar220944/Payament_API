package com.pay.entity; // New package: entity

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor; // Good practice for JPA entities

@Entity
@Data
@NoArgsConstructor
public class PaymentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stripePaymentIntentId;
    private Long amount;     // paise
    private String currency;
    private String status;   // CREATED, SUCCEEDED, FAILED, etc.
    private String description;
    private String paymentMethod;
}