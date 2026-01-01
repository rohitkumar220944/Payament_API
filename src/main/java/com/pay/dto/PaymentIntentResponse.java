package com.pay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentIntentResponse {
    private String id;
    private String clientSecret;
    private Long amount;    // paise
    private String currency;
    private String status;  // succeeded, requires_action, etc.
}