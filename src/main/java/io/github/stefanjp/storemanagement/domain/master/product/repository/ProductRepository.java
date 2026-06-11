package io.github.stefanjp.storemanagement.domain.master.product.repository;

import io.github.stefanjp.storemanagement.domain.master.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

