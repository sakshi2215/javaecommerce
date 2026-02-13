package com.example.sakshi.ecommerce.repository;
import com.example.sakshi.ecommerce.entity.Category;
import com.example.sakshi.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for Category entity.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
