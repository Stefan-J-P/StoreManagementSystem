package io.github.stefanjp.storemanagement.domain.master.stock.dto;

public record StockResponse(
        Long id,
        Long productId,
        String productName,
        Integer quantity
) {
}

