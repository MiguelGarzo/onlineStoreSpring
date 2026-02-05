package com.tiendaonline.tienda.payment;

import com.stripe.model.InvoicePayment;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.tiendaonline.tienda.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stripe.model.Event;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/webhooks/stripe")
public class StripeWebhookController {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    private final OrderService oService;

    public StripeWebhookController(OrderService oService) {
        this.oService = oService;
    }

    @PostMapping
    public ResponseEntity<String> handleStripeEvent(
            @RequestBody byte[] payloadBytes,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) {
        String payload = new String(payloadBytes, StandardCharsets.UTF_8);
        log.info("Webhook called, payload length={}, endpointSecret={}", payload.length(), endpointSecret);

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            log.info("Valid signature for event type: {}", event.getType());
        } catch (Exception e) {
            log.error("Invalid signature: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        // Solo manejamos PaymentIntent Succeeded
        if ("payment_intent.succeeded".equals(event.getType())) {
            Object obj = event.getData().getObject();
            if (obj instanceof PaymentIntent) {
                PaymentIntent intent = (PaymentIntent) obj;

                log.info("Received PaymentIntent ID: {}", intent.getId());
                log.info("Metadata: {}", intent.getMetadata());

                if (intent.getMetadata() != null && intent.getMetadata().containsKey("orderId")) {
                    Long orderId = Long.valueOf(intent.getMetadata().get("orderId"));
                    log.info("Marking order {} as PAID", orderId);
                    oService.markOrderAsPaidFromStripe(orderId);
                } else {
                    log.warn("PaymentIntent missing metadata or orderId");
                }
            } else {
                log.warn("PaymentIntent object could not be cast properly");
            }
        } else {
            log.info("Ignoring event type: {}", event.getType());
        }

        return ResponseEntity.ok("OK");
    }
}
