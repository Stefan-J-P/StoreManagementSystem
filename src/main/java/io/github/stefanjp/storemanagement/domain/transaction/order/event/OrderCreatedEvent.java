package io.github.stefanjp.storemanagement.domain.transaction.order.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderCreatedEvent(
        Long orderId,
        Long customerId,
        Long paymentId,
        LocalDateTime orderDate,
        String status,
        BigDecimal totalAmount
) {
}

