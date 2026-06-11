package io.github.stefanjp.storemanagement.domain.master.product.service;

import io.github.stefanjp.storemanagement.domain.master.product.dto.ProductCreateRequest;
import io.github.stefanjp.storemanagement.domain.master.product.dto.ProductResponse;
import io.github.stefanjp.storemanagement.domain.master.product.entity.Product;
import io.github.stefanjp.storemanagement.domain.master.product.repository.ProductRepository;
import io.github.stefanjp.storemanagement.domain.reference.category.entity.Category;
import io.github.stefanjp.storemanagement.domain.reference.category.repository.CategoryRepository;
import io.github.stefanjp.storemanagement.domain.reference.trade.entity.Trade;
import io.github.stefanjp.storemanagement.domain.reference.trade.repository.TradeRepository;
import io.github.stefanjp.storemanagement.domain.master.producer.entity.Producer;
import io.github.stefanjp.storemanagement.domain.master.producer.repository.ProducerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final TradeRepository tradeRepository;
    private final ProducerRepository producerRepository;

    @Transactional
    public ProductResponse create(ProductCreateRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Trade trade = tradeRepository.findById(request.tradeId())
                .orElseThrow(() -> new RuntimeException("Trade not found"));

        Producer producer = producerRepository.findById(request.producerId())
                .orElseThrow(() -> new RuntimeException("Producer not found"));

        Product product = Product.builder()
                .name(request.name().trim())
                .price(request.price())
                .category(category)
                .trade(trade)
                .producer(producer)
                .build();

        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return toResponse(product);
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getTrade().getId(),
                product.getTrade().getTradeName(),
                product.getProducer().getId(),
                product.getProducer().getName()
        );
    }
}

