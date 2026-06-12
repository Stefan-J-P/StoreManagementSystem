package io.github.stefanjp.storemanagement.domain.transaction.orderitem.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        Long orderId,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice
) {
}

