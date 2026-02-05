package com.tiendaonline.tienda.orders.repository;

import com.tiendaonline.tienda.orders.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
