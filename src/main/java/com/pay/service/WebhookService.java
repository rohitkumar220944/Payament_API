package com.pay.service;


import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.pay.entity.PaymentRecord;
import com.pay.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class WebhookService {

    private final PaymentRepository paymentRepository;

    public WebhookService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void processEvent(Event event) {
        if ("payment_intent.succeeded".equals(event.getType())) {
            PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer()
                    .getObject().orElse(null);

            if (paymentIntent != null) {
                paymentRepository.findByStripePaymentIntentId(paymentIntent.getId())
                        .ifPresent(record -> {
                            record.setStatus("SUCCEEDED");
                            paymentRepository.save(record);
                        });
            }
        }
        // Add failure handling similarly if needed.
    }
}