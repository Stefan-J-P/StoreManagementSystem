package io.github.stefanjp.storemanagement.domain.reference.category.service;

import io.github.stefanjp.storemanagement.domain.reference.category.dto.CategoryCreateRequest;
import io.github.stefanjp.storemanagement.domain.reference.category.dto.CategoryResponse;
import io.github.stefanjp.storemanagement.domain.reference.category.entity.Category;
import io.github.stefanjp.storemanagement.domain.reference.category.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse create(CategoryCreateRequest request) {
        Category category = Category.builder()
                .name(request.name().trim())
                .build();

        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoryResponse findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        return toResponse(category);
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}

