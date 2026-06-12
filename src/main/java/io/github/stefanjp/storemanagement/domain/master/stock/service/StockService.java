package io.github.stefanjp.storemanagement.domain.master.stock.service;

import io.github.stefanjp.storemanagement.domain.master.product.entity.Product;
import io.github.stefanjp.storemanagement.domain.master.product.repository.ProductRepository;
import io.github.stefanjp.storemanagement.domain.master.stock.dto.StockCreateRequest;
import io.github.stefanjp.storemanagement.domain.master.stock.dto.StockResponse;
import io.github.stefanjp.storemanagement.domain.master.stock.entity.Stock;
import io.github.stefanjp.storemanagement.domain.master.stock.repository.StockRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;

    @Transactional
    public StockResponse create(StockCreateRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Stock stock = Stock.builder()
                .product(product)
                .quantity(request.quantity())
                .build();

        Stock saved = stockRepository.save(stock);
        return toResponse(saved);
    }

    public List<StockResponse> findAll() {
        return stockRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public StockResponse findById(Long id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        return toResponse(stock);
    }

    private StockResponse toResponse(Stock stock) {
        return new StockResponse(
                stock.getId(),
                stock.getProduct().getId(),
                stock.getProduct().getName(),
                stock.getQuantity()
        );
    }
}

