package com.example.sakshi.ecommerce.controller;
import com.example.sakshi.ecommerce.dto.response.CartResponse;
import com.example.sakshi.ecommerce.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(
            @Valid
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        CartResponse response = cartService.addToCart(userId, productId, quantity);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/view/{userId}")
    public ResponseEntity<CartResponse> viewCart(@Valid @PathVariable Long userId) {
        CartResponse response = cartService.viewCart(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeCartItem(
            @Valid
            @RequestParam Long userId,
            @RequestParam Long productId
    ) {
        cartService.removeCartItem(userId, productId);
        return ResponseEntity.noContent().build();
    }
}
