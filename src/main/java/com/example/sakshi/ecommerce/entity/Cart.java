package com.example.sakshi.ecommerce.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
/*
A no-args constructor is a constructor that does not take any parameters. It is either explicitly defined by the programmer or implicitly provided by the Java compiler
when no other constructors are defined in the class./
 */
//@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // If CartItem/OrderItem is removed from list,  it gets deleted automatically.
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<CartItem> cartItems;


    //To auto-set timestamps instead of manually setting in service layer.
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void addCartItem(CartItem item) {
        cartItems.add(item);
        item.setCart(this);
    }
    public void removeCartItem(CartItem item) {
        cartItems.remove(item);
        item.setCart(null);
    }
}
