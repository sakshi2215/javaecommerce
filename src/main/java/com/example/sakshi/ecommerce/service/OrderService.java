package com.example.sakshi.ecommerce.service;
import com.example.sakshi.ecommerce.dto.request.CheckoutRequest;
import com.example.sakshi.ecommerce.dto.response.OrderItemResponse;
import com.example.sakshi.ecommerce.dto.response.OrderResponse;
import com.example.sakshi.ecommerce.entity.*;
import com.example.sakshi.ecommerce.exception.ResourceNotFoundException;
import com.example.sakshi.ecommerce.repository.*;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CurrencyConversionService currencyConversionService; // your API wrapper

    public OrderService(CartRepository cartRepository,
                        CartItemRepository cartItemRepository,
                        ProductRepository productRepository,
                        OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        CurrencyConversionService currencyConversionService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.currencyConversionService = currencyConversionService;
    }

    @Transactional
    public OrderResponse checkout(CheckoutRequest request) {

        Long userId = request.getUserId();
        String currency = request.getCurrency();
        //Get the corresponding cart for the user
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user"));

        // Get All items from cart
        List<CartItem> items = validateAndGetCartItems(cart);

        //Deduct stock
        for (CartItem item : items) {
            Product product = item.getProduct();
            int remainingStock = product.getStockQuantity() - item.getQuantity();
            product.setStockQuantity(remainingStock);
            productRepository.save(product);
        }

        //Calculate totalAmount
        double totalAmount = items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getPriceAtTime())
                .sum();

        //Currency Conversion
        double convertedAmount;

        try {
            convertedAmount = totalAmount* currencyConversionService.conversionRate(currency);
        } catch (Exception ex) {
            throw new RuntimeException("Currency conversion failed: " + ex.getMessage());
        }

        //Create Order
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount);
        order.setConvertedAmount(convertedAmount);
        order.setCurrency(currency);
        order.setStatus("SUCCESS");
        Order savedOrder = orderRepository.save(order);

        //Create OrderItems
        List<OrderItem> orderItems = items.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtTime(cartItem.getPriceAtTime());
            return orderItemRepository.save(orderItem);
        }).collect(Collectors.toList());

        //Clear Cart
        cartItemRepository.deleteAll(items);

        //Build OrderResponse
        List<OrderItemResponse> itemResponses = orderItems.stream()
                .map(oi -> new OrderItemResponse(
                        oi.getProduct().getId(),
                        oi.getProduct().getName(),
                        oi.getQuantity(),
                        oi.getPriceAtTime(),
                        oi.getQuantity() * oi.getPriceAtTime()
                ))
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getOrderDate(),
                order.getStatus(),
                order.getCurrency(),
                order.getTotalAmount(),
                order.getConvertedAmount(),
                itemResponses
        );
    }


    public List<OrderResponse> getAllOrders(Long userID){
        List<Order> userOrder = orderRepository.findByUserId(userID);

        return userOrder.stream().map(
                order-> {
                    List <OrderItemResponse> orderItems = order.getOrderItems().stream().map(
                            item -> new OrderItemResponse(
                                 item.getProduct().getId(),
                                    item.getProduct().getName(),
                                    item.getQuantity(),
                                    item.getPriceAtTime(),
                                    item.getQuantity() * item.getPriceAtTime()
                            )
                    ).toList();




                    return new OrderResponse(
                            order.getId(),
                            order.getOrderDate(),
                            order.getStatus(),
                            order.getCurrency(),
                            order.getTotalAmount(),
                            order.getConvertedAmount(),
                            orderItems
                    );

                }).toList();
    }
    private static @NonNull List<CartItem> validateAndGetCartItems(Cart cart) {
        List<CartItem> items = cart.getCartItems();


        if (items.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        for (CartItem item : items) {
            Product product = item.getProduct();
            if (product.getStockQuantity() < item.getQuantity()) {
                throw new IllegalArgumentException(
                        "Insufficient stock for product: " + product.getName()
                );
            }
        }
        return items;
    }
}
