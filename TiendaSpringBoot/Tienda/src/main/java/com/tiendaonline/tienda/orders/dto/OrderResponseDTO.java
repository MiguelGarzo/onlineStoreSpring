package com.tiendaonline.tienda.orders.dto;


import com.tiendaonline.tienda.orders.OrderStatus;
import com.tiendaonline.tienda.orders.entity.OrderItem;
import com.tiendaonline.tienda.users.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDTO {

    private Long id;
    private BigDecimal total;
    private OrderStatus status;
    private LocalDateTime createdAt;

    private List<OrderItemResponseDTO> items;

}
