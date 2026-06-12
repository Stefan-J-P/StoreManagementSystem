package io.github.stefanjp.storemanagement.domain.transaction.order.dto;

import jakarta.validation.constraints.NotNull;

public record OrderCreateRequest(
        @NotNull(message = "Customer id is required")
        Long customerId,

        @NotNull(message = "Payment id is required")
        Long paymentId
) {
}

