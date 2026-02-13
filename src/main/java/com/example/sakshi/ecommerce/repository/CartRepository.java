package com.example.sakshi.ecommerce.repository;
import com.example.sakshi.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository for Cart entity.
 */
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Find a cart by user ID.
     *
     * @param userId user ID
     * @return Optional containing the cart if found
     */
    Optional<Cart> findByUserId(Long userId);
}

