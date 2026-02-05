package com.tiendaonline.tienda.cart.dto;

import com.tiendaonline.tienda.cart.entity.Cart;
import com.tiendaonline.tienda.cart.entity.CartItem;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartResponseDTO {

    private Long id;
    private List<CartItemResponseDTO> items;
    private BigDecimal total;

}

