package io.github.stefanjp.storemanagement.domain.transaction.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PlaceOrderRequest(
        @NotNull(message = "Customer id is required")
        Long customerId,

        @NotNull(message = "Payment id is required")
        Long paymentId,

        @NotEmpty(message = "Items are required")
        List<@Valid PlaceOrderItemRequest> items
) {
}

