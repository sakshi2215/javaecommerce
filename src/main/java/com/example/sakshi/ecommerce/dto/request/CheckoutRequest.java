package com.example.sakshi.ecommerce.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CheckoutRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Currency is required")
    private String currency;

    public CheckoutRequest() {
    }

    public CheckoutRequest(Long userId, String currency) {
        this.userId = userId;
        this.currency = currency;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

