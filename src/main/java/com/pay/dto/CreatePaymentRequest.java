package com.pay.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePaymentRequest {

    @NotNull
    private Long amount;          // rupees from frontend

    @NotNull
    private String currency;      // "inr"

    @NotNull
    private String paymentMethod; // card | upi | emi | netbanking | cod

    private String cardNumber;
    private String cardHolder;
    private String validThru;
    private String cvv;
}