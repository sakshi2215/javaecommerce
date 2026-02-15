package com.example.sakshi.ecommerce.controller;
import com.example.sakshi.ecommerce.dto.request.CheckoutRequest;
import com.example.sakshi.ecommerce.dto.response.OrderResponse;
import com.example.sakshi.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(@Valid @RequestBody CheckoutRequest request) {
        OrderResponse response = orderService.checkout(request);
        return ResponseEntity.status(201).body(response); // 201 CREATED
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getUserOrders( @Valid @PathVariable Long userId) {
        List<OrderResponse> orders = orderService.getAllOrders(userId);
        return ResponseEntity.ok(orders);
    }
}