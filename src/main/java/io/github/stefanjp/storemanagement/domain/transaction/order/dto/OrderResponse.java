package io.github.stefanjp.storemanagement.domain.transaction.order.dto;

import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        Long customerId,
        String customerName,
        Long paymentId,
        String paymentName,
        LocalDateTime orderDate,
        String status
) {
}

