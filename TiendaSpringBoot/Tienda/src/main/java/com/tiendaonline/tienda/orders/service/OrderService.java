package com.tiendaonline.tienda.orders.service;

import com.tiendaonline.tienda.cart.entity.Cart;
import com.tiendaonline.tienda.cart.entity.CartItem;
import com.tiendaonline.tienda.cart.repository.CartRepository;
import com.tiendaonline.tienda.exceptions.EmptyException;
import com.tiendaonline.tienda.exceptions.NoStockException;
import com.tiendaonline.tienda.exceptions.NotExistsException;
import com.tiendaonline.tienda.exceptions.OrderModificationException;
import com.tiendaonline.tienda.orders.OrderStatus;
import com.tiendaonline.tienda.orders.dto.OrderItemResponseDTO;
import com.tiendaonline.tienda.orders.dto.OrderResponseDTO;
import com.tiendaonline.tienda.orders.entity.Order;
import com.tiendaonline.tienda.orders.entity.OrderItem;
import com.tiendaonline.tienda.orders.repository.OrderRepository;
import com.tiendaonline.tienda.products.entity.Product;
import com.tiendaonline.tienda.products.repository.ProductRepository;
import com.tiendaonline.tienda.users.entity.User;
import com.tiendaonline.tienda.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        CartRepository cartRepository,
                        UserRepository userRepository,
                        ProductRepository productRepository
    ) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public OrderResponseDTO createOrder(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotExistsException("User with email: " + email + "not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new NotExistsException("Cart for user id: " + user.getId() + "not found"));

        if (cart.getItems().isEmpty()) {
            throw new EmptyException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal price = BigDecimal.ZERO;

        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();

            if (product.getStock() < item.getQuantity()) {
                throw new NoStockException("Not enough stock for product: " + product.getName());
            }

            product.setStock(product.getStock() - item.getQuantity());

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getPrice());

            order.getItems().add(orderItem);

            price = price.add(
                    item.getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
            );
        }

        order.setTotal(price);

        cart.getItems().clear();

        return toResponse(orderRepository.save(order));
    }

    private OrderResponseDTO toResponse(Order order) {

        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus());
        dto.setCreatedAt(order.getCreatedAt());

        List<OrderItemResponseDTO> items = order.getItems().stream().map(item -> {
            OrderItemResponseDTO i = new OrderItemResponseDTO();

            i.setProductId(item.getProduct().getId());
            i.setProductName(item.getProduct().getName());
            i.setPrice(item.getPrice());
            i.setQuantity(item.getQuantity());
            i.setSubtotal(
                    item.getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
            );

        return i;

        }).toList();

            dto.setItems(items);

            return dto;

    }

    public List<OrderResponseDTO> getOrdersByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotExistsException("User with email: " + email + "not found"));

        return orderRepository.findByUser(user)
                .stream()
                .map(this::toResponse)
                .toList();

    }

    public OrderResponseDTO cancelOrder(Long orderId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotExistsException("User with email: " + email + "not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotExistsException("Order with id: " + orderId + "not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new OrderModificationException("You cannot cancel this order");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new OrderModificationException("Only PENDING orders can be cancelled");
        }

        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(
                    product.getStock() + item.getQuantity()
            );
        }

        order.setStatus(OrderStatus.CANCELLED);

        return toResponse(orderRepository.save(order));
    }

    public OrderResponseDTO payOrder(Long orderId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotExistsException("User with email: " + email + "not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotExistsException("Order with id: " + orderId + "not found"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new OrderModificationException("You cannot pay this order");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new OrderModificationException("Only PENDING orders can be paid");
        }

        order.setStatus(OrderStatus.PAID);

        return toResponse(orderRepository.save(order));
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotExistsException("Order with id: " + id + "not found"));

        return toResponse(order);
    }

    public OrderResponseDTO updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotExistsException("Order with id: " + id + "not found"));

        OrderStatus cStatus = order.getStatus();

        if (cStatus == OrderStatus.CANCELLED || cStatus == OrderStatus.COMPLETED) {
            throw new OrderModificationException("This order cannot be modified");
        }

        if (cStatus == OrderStatus.PAID && status != OrderStatus.SHIPPED) {
            throw new OrderModificationException("PAID orders can only be SHIPPED");
        }

        if (cStatus == OrderStatus.SHIPPED && status != OrderStatus.COMPLETED) {
            throw new OrderModificationException("SHIPPED orders can only be COMPLETED");
        }

        order.setStatus(status);
        return toResponse(orderRepository.save(order));
    }

    public void markOrderAsPaidFromStripe(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotExistsException("Order with id: " + orderId + "not found"));

        if (order.getStatus() != OrderStatus.PENDING) {
            return; // idempotencia: Stripe puede llamar varias veces
        }

        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }

}
