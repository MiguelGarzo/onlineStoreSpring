package com.tiendaonline.tienda.orders.repository;

import com.tiendaonline.tienda.orders.entity.Order;
import com.tiendaonline.tienda.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
