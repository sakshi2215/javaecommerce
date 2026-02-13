package com.example.sakshi.ecommerce.service;
import com.example.sakshi.ecommerce.dto.request.RegisterUserRequest;
import com.example.sakshi.ecommerce.dto.response.UserResponse;
import com.example.sakshi.ecommerce.entity.Cart;
import com.example.sakshi.ecommerce.entity.User;
import com.example.sakshi.ecommerce.exception.ResourceAlreadyExistsException;
import com.example.sakshi.ecommerce.repository.CartRepository;
import com.example.sakshi.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public UserService(UserRepository userRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }


    public UserResponse registerUser(RegisterUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // TODO: apply bcrypt
        User savedUser = userRepository.save(user);

        Cart cart = new Cart();
        cart.setUser(savedUser);
        cartRepository.save(cart);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt()
        );
    }
}
