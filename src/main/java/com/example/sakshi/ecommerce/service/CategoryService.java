package com.example.sakshi.ecommerce.service;
import com.example.sakshi.ecommerce.dto.request.CreateCategoryRequest;
import com.example.sakshi.ecommerce.dto.response.CategoryResponse;
import com.example.sakshi.ecommerce.entity.Category;
import com.example.sakshi.ecommerce.exception.ResourceAlreadyExistsException;
import com.example.sakshi.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


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
}

