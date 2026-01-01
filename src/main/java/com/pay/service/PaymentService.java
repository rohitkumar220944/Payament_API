package com.pay.service;

import com.pay.dto.CreatePaymentRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public PaymentIntent createPayment(CreatePaymentRequest req) throws StripeException {
        if (req == null) throw new IllegalArgumentException("request is required");
        if (req.getPaymentMethodId() == null || req.getPaymentMethodId().isBlank()) {
            throw new IllegalArgumentException("paymentMethodId is required");
        }
        if (req.getAmount() <= 0) throw new IllegalArgumentException("amount must be greater than zero");
        if (req.getCurrency() == null || req.getCurrency().isBlank()) {
            throw new IllegalArgumentException("currency is required");
        }

        RequestOptions opts = RequestOptions.builder()
            .setApiKey(stripeSecretKey)
            .build();

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
            .setAmount(toMinorUnits(req.getAmount()))
            .setCurrency(req.getCurrency())
            .setPaymentMethod(req.getPaymentMethodId())
            .addPaymentMethodType("card") // string version avoids enum dependency
            .setConfirm(true)
            .setReturnUrl("http://localhost:3000")
            .setAutomaticPaymentMethods(
                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                    .setEnabled(false)
                    .build()
            )
            .setDescription(req.getDescription())
            .putMetadata("paymentMethod", nullSafe(req.getPaymentMethod()))
            .putMetadata("cardHolder", nullSafe(req.getCardHolder()))
            .build();

        return PaymentIntent.create(params, opts);
    }

    private long toMinorUnits(double amount) { return Math.round(amount * 100); }
    private String nullSafe(String v) { return v == null ? "" : v; }
}