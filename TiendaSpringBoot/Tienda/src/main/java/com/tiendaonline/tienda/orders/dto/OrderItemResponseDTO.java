package com.tiendaonline.tienda.orders.dto;


import com.tiendaonline.tienda.orders.entity.Order;
import com.tiendaonline.tienda.products.entity.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class OrderItemResponseDTO {

    private Long productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;

    private BigDecimal subtotal;

}
