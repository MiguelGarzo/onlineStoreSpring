package com.tiendaonline.tienda.cart.service;

import com.tiendaonline.tienda.cart.dto.CartItemResponseDTO;
import com.tiendaonline.tienda.cart.dto.CartResponseDTO;
import com.tiendaonline.tienda.cart.entity.Cart;
import com.tiendaonline.tienda.cart.entity.CartItem;
import com.tiendaonline.tienda.cart.repository.CartItemRepository;
import com.tiendaonline.tienda.cart.repository.CartRepository;
import com.tiendaonline.tienda.exceptions.NotExistsException;
import com.tiendaonline.tienda.products.entity.Product;
import com.tiendaonline.tienda.products.repository.ProductRepository;
import com.tiendaonline.tienda.users.entity.User;
import com.tiendaonline.tienda.users.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository,
            UserRepository userRepository
    ) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public void addProduct(Long productId) {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotExistsException("User with email: " + email + "not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotExistsException("Product with id: " + productId + "not found"));

        CartItem item = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseGet(() -> {
                    CartItem newitem = new CartItem();
                    newitem.setCart(cart);
                    newitem.setProduct(product);
                    newitem.setQuantity(0);
                    newitem.setPrice(product.getPrice());
                    return newitem;
                });

        item.setQuantity(item.getQuantity() + 1);

        cartItemRepository.save(item);
    }

    public CartResponseDTO getCartByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotExistsException("User with email: " + email + "not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new NotExistsException("No cart for user with id: " + user.getId() + "has found"));

        return toResponse(cart);
    }

    private CartResponseDTO toResponse(Cart cart) {
        CartResponseDTO dto = new CartResponseDTO();
        dto.setId(cart.getId());

        List<CartItemResponseDTO> items = cart.getItems().stream().map(item -> {
            CartItemResponseDTO itemResponseDTO = new CartItemResponseDTO();
            itemResponseDTO.setProductId(item.getProduct().getId());
            itemResponseDTO.setProductName(item.getProduct().getName());
            itemResponseDTO.setPrice(item.getProduct().getPrice());
            itemResponseDTO.setQuantity(item.getQuantity());
            itemResponseDTO.setSubtotal(
                    item.getProduct().getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
            );
            return itemResponseDTO;
        }).toList();

        dto.setItems(items);

        BigDecimal total = items.stream()
                .map(CartItemResponseDTO::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dto.setTotal(total);

        return dto;
    }

    public void removeProduct(Long productId) {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotExistsException("User with email: " + email + "not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new NotExistsException("No cart for user with id:" + user.getId() + "has found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotExistsException("Product with id: " + productId + "not found"));

        CartItem item = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new NotExistsException("Item with id: " + product.getId() + "not found in cart with id: " + cart.getId()));

        if (item.getQuantity() <= 1) {
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(item.getQuantity() - 1);
            cartItemRepository.save(item);
        }

    }



}
