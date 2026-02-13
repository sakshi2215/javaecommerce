package com.example.sakshi.ecommerce.repository;
import com.example.sakshi.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for Product entity.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Find products by category ID.
     *
     * @param categoryId category ID
     * @return list of products in the given category
     */
    List<Product> findByCategoryId(Long categoryId);

    /**
     * Find products by name (case insensitive).
     *
     * @param name product name or keyword
     * @return list of matching products
     */
    List<Product> findByNameContainingIgnoreCase(String name);
}

