package com.example.sakshi.ecommerce.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateCategoryRequest {

    @NotBlank(message = "Name is required")
    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 50, message = "Category name must be between 2 and 50 characters")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Category name must contain only alphabets and spaces"
    )
    private String name;

    @Size(max = 200, message = "Description must not exceed 200 characters")
    private String description;

    public CreateCategoryRequest() {
    }

    public CreateCategoryRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

