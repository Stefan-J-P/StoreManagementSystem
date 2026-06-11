package io.github.stefanjp.storemanagement.domain.reference.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PaymentCreateRequest(
        @NotBlank(message = "Payment type is required")
        @Size(max = 50, message = "Payment type must be at most 50 characters")
        String paymentType
) {
}

