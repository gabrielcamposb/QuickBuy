package com.QuickBuy.repository;

import com.QuickBuy.model.Product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
    boolean existsByName(String name);
    boolean existsById(Long id);
}
