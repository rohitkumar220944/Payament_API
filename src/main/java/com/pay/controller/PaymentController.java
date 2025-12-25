package com.pay.controller;


import com.pay.dto.CreatePaymentRequest;
import com.pay.dto.PaymentIntentResponse;
import com.pay.service.PaymentService;
import com.stripe.model.PaymentIntent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentIntentResponse> createPayment(@RequestBody CreatePaymentRequest request) {
        try {
            PaymentIntent intent = paymentService.createPayment(request);
            PaymentIntentResponse response = new PaymentIntentResponse(
                    intent.getId(),
                    intent.getClientSecret(),
                    intent.getAmount(),
                    intent.getCurrency(),
                    intent.getStatus()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}