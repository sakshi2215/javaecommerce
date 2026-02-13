package com.example.sakshi.ecommerce.repository;
import com.example.sakshi.ecommerce.entity.Cart;
import com.example.sakshi.ecommerce.entity.CartItem;
import com.example.sakshi.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /**
     * Find a cartItem by cart ID.
     *
     * @param cartId cart ID
     * @return Optional containing the cart if found
     */
    List<CartItem> findByCartId(Long cartId);

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    void deleteByCartId(Long cartId);
}

