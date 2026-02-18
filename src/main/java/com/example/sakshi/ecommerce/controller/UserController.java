package com.example.sakshi.ecommerce.controller;
import com.example.sakshi.ecommerce.dto.request.RegisterUserRequest;
import com.example.sakshi.ecommerce.dto.response.UserResponse;
import com.example.sakshi.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
   /*
   In Spring Boot, the ResponseEntity class is used to represent the entire HTTP response, allowing developers to control the status code,
   headers, and body of the response with fine-grained control.
   It is a generic class, ResponseEntity<T>, where T is the type of the body you want to return*/
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterUserRequest request) {
        UserResponse response = userService.registerUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
