package com.example.sakshi.ecommerce.controller;
import com.example.sakshi.ecommerce.dto.request.CreateCategoryRequest;
import com.example.sakshi.ecommerce.dto.response.CategoryResponse;
import com.example.sakshi.ecommerce.entity.Category;
import com.example.sakshi.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        CategoryResponse response = categoryService.createCategory(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    //get category
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> response = categoryService.getAllCategories();
        return new ResponseEntity<>(response, HttpStatus.OK);    }

    //Get category
//    @GetMapping("/{id}")
//    public ResponseEntity<CategoryResponse> getCategoryByID(@Valid @PathVariable Long categoryId) {
//        CategoryResponse response = categoryService.getCategoryById(categoryId);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }


}
