package com.example.sakshi.ecommerce.service;
import com.example.sakshi.ecommerce.dto.request.CreateCategoryRequest;
import com.example.sakshi.ecommerce.dto.response.CategoryResponse;
import com.example.sakshi.ecommerce.dto.response.UserResponse;
import com.example.sakshi.ecommerce.entity.Category;
import com.example.sakshi.ecommerce.exception.ResourceAlreadyExistsException;
import com.example.sakshi.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        // Validate duplicate
        categoryRepository.findByName(request.getName())
                .ifPresent(c -> {
                    throw new ResourceAlreadyExistsException(
                            "Category with name '" + request.getName() + "' already exists"
                    );
                });

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        Category savedCategory = categoryRepository.save(category);

        // Return response
        return new CategoryResponse(
                savedCategory.getId(),
                savedCategory.getName(),
                savedCategory.getDescription()
        );
    }
    public List<CategoryResponse> getAllCategories(){
        return categoryRepository.findAll().stream()
                .map(category -> {
                    CategoryResponse categoryResponse = new CategoryResponse();
                    categoryResponse.setId(category.getId());
                    categoryResponse.setName(category.getName());
                    categoryResponse.setDescription(category.getDescription());
                    return categoryResponse;
                })
                .collect(Collectors.toList());
    }
//    public CategoryResponse getCategoryById(Long categoryId){
//        Cate
//    }
}

