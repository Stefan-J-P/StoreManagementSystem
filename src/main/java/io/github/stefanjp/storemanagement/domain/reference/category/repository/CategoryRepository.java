package io.github.stefanjp.storemanagement.domain.reference.category.repository;

import io.github.stefanjp.storemanagement.domain.reference.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

