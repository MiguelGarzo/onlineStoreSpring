package com.tiendaonline.tienda.cart.repository;

import com.tiendaonline.tienda.cart.entity.Cart;
import com.tiendaonline.tienda.cart.entity.CartItem;
import com.tiendaonline.tienda.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}
