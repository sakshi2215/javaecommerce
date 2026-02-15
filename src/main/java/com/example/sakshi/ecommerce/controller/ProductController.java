package com.example.sakshi.ecommerce.controller;
import com.example.sakshi.ecommerce.dto.request.CreateProductRequest;
import com.example.sakshi.ecommerce.dto.response.ProductResponse;
import com.example.sakshi.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PatchMapping("/{productId}/updatestock")
    public ResponseEntity<ProductResponse> updateStock(
            @Valid
            @PathVariable Long productId,
            @RequestParam int quantity
    ) {
        ProductResponse response = productService.updateStock(productId, quantity);
        return ResponseEntity.ok(response);
    }
}

