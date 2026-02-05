package com.tiendaonline.tienda.cart.controller;

import com.tiendaonline.tienda.cart.dto.CartResponseDTO;
import com.tiendaonline.tienda.cart.entity.Cart;
import com.tiendaonline.tienda.cart.service.CartService;
import com.tiendaonline.tienda.users.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<Void> add(@PathVariable Long productId) {
        service.addProduct(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<CartResponseDTO> getMyCart(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(service.getCartByUserEmail(email));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<String> remove(@PathVariable Long productId) {
        service.removeProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
