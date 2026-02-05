package com.tiendaonline.tienda.cart.repository;

import com.tiendaonline.tienda.cart.entity.Cart;
import com.tiendaonline.tienda.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
