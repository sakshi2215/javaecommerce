package com.example.sakshi.ecommerce.dto.response;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    private Long orderId;
    private LocalDateTime orderDate;
    private String status;
    private String currency;
    private Double totalAmount;
    private Double convertedAmount;
    private List<OrderItemResponse> items;

    public OrderResponse() {
    }

    public OrderResponse(Long orderId, LocalDateTime orderDate, String status, String currency, Double totalAmount, Double convertedAmount, List<OrderItemResponse> items) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.status = status;
        this.currency = currency;
        this.totalAmount = totalAmount;
        this.convertedAmount = convertedAmount;
        this.items = items;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(Double convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }
}
