package com.tiendaonline.tienda.orders.controller;

import com.tiendaonline.tienda.orders.dto.OrderResponseDTO;
import com.tiendaonline.tienda.orders.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createOrder(auth.getName()));
    }

    @GetMapping("/my")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(service.getOrdersByEmail(email));
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable Long orderId, Authentication auth) {
        return ResponseEntity.ok(service.cancelOrder(orderId, auth.getName()));
    }

    @PutMapping("/{orderId}/pay")
    public ResponseEntity<OrderResponseDTO> payOrder(@PathVariable Long orderId, Authentication auth) {
        return ResponseEntity.ok(service.payOrder(orderId, auth.getName()));
    }

}
