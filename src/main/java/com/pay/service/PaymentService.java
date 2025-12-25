package com.pay.service;

import com.pay.dto.CreatePaymentRequest;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.pay.entity.PaymentRecord; // New Import
import com.pay.repository.PaymentRepository; // New Import
import org.springframework.stereotype.Service;
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentIntent createPayment(CreatePaymentRequest request) throws Exception {
        long amountMinor = toMinorUnits(request.getAmount(), request.getCurrency());

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountMinor)
                .setCurrency(request.getCurrency())
                .setDescription("Checkout payment via frontend")
                .putMetadata("paymentMethod", request.getPaymentMethod())
                .putMetadata("cardHolder", safe(request.getCardHolder()))
                .putMetadata("cardLast4", last4(request.getCardNumber()))
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true)
                                .build()
                )
                .build();

        PaymentIntent intent = PaymentIntent.create(params);

        PaymentRecord record = new PaymentRecord();
        record.setStripePaymentIntentId(intent.getId());
        record.setAmount(intent.getAmount());
        record.setCurrency(intent.getCurrency());
        record.setDescription("Checkout payment via frontend");
        record.setStatus("CREATED");
        record.setPaymentMethod(request.getPaymentMethod());
        paymentRepository.save(record);

        return intent;
    }

    private long toMinorUnits(Long amountRupees, String currency) {
        if (amountRupees == null) return 0L;
        // INR uses paise (x100). Adjust if you add zero-decimal currencies later.
        return amountRupees * 100;
    }

    private String safe(String v) { return v == null ? "" : v; }

    private String last4(String cardNumber) {
        if (cardNumber == null) return "";
        String digits = cardNumber.replaceAll("\\D", "");
        return digits.length() >= 4 ? digits.substring(digits.length() - 4) : digits;
    }
}