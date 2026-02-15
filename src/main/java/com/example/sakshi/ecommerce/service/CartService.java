package com.example.sakshi.ecommerce.service;
import com.example.sakshi.ecommerce.dto.response.CartItemResponse;
import com.example.sakshi.ecommerce.dto.response.CartResponse;
import com.example.sakshi.ecommerce.entity.Cart;
import com.example.sakshi.ecommerce.entity.CartItem;
import com.example.sakshi.ecommerce.entity.Product;
import com.example.sakshi.ecommerce.entity.User;
import com.example.sakshi.ecommerce.exception.ResourceNotFoundException;
import com.example.sakshi.ecommerce.repository.CartItemRepository;
import com.example.sakshi.ecommerce.repository.CartRepository;
import com.example.sakshi.ecommerce.repository.ProductRepository;
import com.example.sakshi.ecommerce.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(UserRepository userRepository, CartRepository cartRepository, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }
    @Transactional
    public CartResponse addToCart(Long userId, Long productId, int quantity) {

        //  Validate quantity
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        //  Fetch user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        //  Fetch cart (create if not exists)
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        //  Fetch product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        //  Validate stock
        if (product.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock available");
        }

        // Check if cart already has this product
        Optional<CartItem> existingItem =
                cartItemRepository.findByCartAndProduct(cart, product);

        if (existingItem.isPresent()) {

            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + quantity;

            // Validate stock again
            if (product.getStockQuantity() < newQuantity) {
                throw new IllegalArgumentException("Not enough stock for requested quantity");
            }

            item.setQuantity(newQuantity);
            cartItemRepository.save(item);

        } else {

            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setPriceAtTime(product.getPrice());

            cartItemRepository.save(newItem);
        }

        cart.setUpdatedAt(LocalDateTime.now());

        return mapToCartResponse(cart);
    }


    public CartResponse viewCart(Long userId) {


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));


        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        return mapToCartResponse(cart);


    }

    @Transactional
    public void removeCartItem(Long userId, Long productId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        CartItem item = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElseThrow(() -> new ResourceNotFoundException("Item not in cart"));

        cartItemRepository.delete(item);
    }




    private CartResponse mapToCartResponse(Cart cart) {

        List<CartItemResponse> items = cart.getCartItems().stream()
                .map(item -> new CartItemResponse(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPriceAtTime(),
                        item.getTotalPrice()
                ))
                .toList();

        double totalAmount = items.stream()
                .mapToDouble(CartItemResponse::getSubtotal)
                .sum();

        return new CartResponse(
                cart.getId(),                    // cartId
                cart.getUser().getId(),          // userId
                items,                           // list of CartItemResponse
                totalAmount,                     // totalAmount
                cart.getUpdatedAt()              // updatedAt
        );
    }

}
