package io.github.stefanjp.storemanagement.domain.master.product.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        BigDecimal price,
        Long categoryId,
        String categoryName,
        Long tradeId,
        String tradeName,
        Long producerId,
        String producerName
) {
}

