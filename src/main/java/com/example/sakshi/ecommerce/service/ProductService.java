package com.example.sakshi.ecommerce.service;

import com.example.sakshi.ecommerce.dto.request.CreateProductRequest;
import com.example.sakshi.ecommerce.dto.response.ProductResponse;
import com.example.sakshi.ecommerce.entity.Category;
import com.example.sakshi.ecommerce.entity.Product;
import com.example.sakshi.ecommerce.exception.ResourceNotFoundException;
import com.example.sakshi.ecommerce.repository.CategoryRepository;
import com.example.sakshi.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;




    public ProductResponse createProduct(CreateProductRequest request){
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category with ID " + request.getCategoryId() + " not found"
                ));
        if (request.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        if (request.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return new ProductResponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getStockQuantity(),
                category.getName()

        );
    }

    @Transactional
    public ProductResponse updateStock(Long productId, int newStockQuantity) {
        // Fetch product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product with ID " + productId + " not found"
                ));

        // validate stock
        if (newStockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }

        // update stock
        product.setStockQuantity(newStockQuantity);
        System.out.println(product.getStockQuantity());

        // save updated product
        Product updatedProduct = productRepository.save(product);
        System.out.println(updatedProduct);

        // return updated response
        return new ProductResponse(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getPrice(),
                updatedProduct.getStockQuantity(),
                updatedProduct.getCategory().getName()

        );
    }


}

