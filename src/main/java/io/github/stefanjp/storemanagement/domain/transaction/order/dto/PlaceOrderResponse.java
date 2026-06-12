package io.github.stefanjp.storemanagement.domain.transaction.order.dto;

import java.math.BigDecimal;
import java.util.List;

public record PlaceOrderResponse(
        Long orderId,
        Long customerId,
        String status,
        BigDecimal totalAmount,
        List<ItemResponse> items
) {
    public record ItemResponse(
            Long productId,
            String productName,
            Integer quantity,
            BigDecimal unitPrice,
            BigDecimal lineTotal
    ) {
    }
}

