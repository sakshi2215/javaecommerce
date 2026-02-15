package com.example.sakshi.ecommerce.dto.request;
import jakarta.validation.constraints.*;

public class CheckoutRequest {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be positive")
    private Long userId;

    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be a 3-letter ISO code")
    @Pattern(
            regexp = "^[A-Z]{3}$",
            message = "Currency must be a valid 3-letter uppercase code"
    )
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

