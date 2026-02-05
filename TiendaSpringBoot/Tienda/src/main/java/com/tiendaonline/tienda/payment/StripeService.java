package com.tiendaonline.tienda.payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripeService {

    @Value("${stripe.api.key}")
    private String apiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = apiKey;
    }

    public PaymentIntent createPaymentIntent(BigDecimal amount, String currency, Long orderId) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount.multiply(BigDecimal.valueOf(100)).longValue());
        params.put("currency", currency);
        params.put("payment_method_types", List.of("card"));


        Map <String, String> metadata = new HashMap<>();
        metadata.put("orderId", orderId.toString());
        params.put("metadata", metadata);

        return PaymentIntent.create(params);
    }

}
