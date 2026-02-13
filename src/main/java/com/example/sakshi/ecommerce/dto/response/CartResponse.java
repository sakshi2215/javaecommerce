package com.example.sakshi.ecommerce.dto.response;
import java.time.LocalDateTime;
import java.util.List;

public class CartResponse {

    private Long cartId;
    private Long userId;
    private List<CartItemResponse> items;
    private Double totalAmount;
    private LocalDateTime updatedAt;

    public CartResponse() {
    }

    public CartResponse(Long cartId, Long userId, List<CartItemResponse> items, Double totalAmount, LocalDateTime updatedAt) {
        this.cartId = cartId;
        this.userId = userId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.updatedAt = updatedAt;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartItemResponse> getItems() {
        return items;
    }

    public void setItems(List<CartItemResponse> items) {
        this.items = items;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
