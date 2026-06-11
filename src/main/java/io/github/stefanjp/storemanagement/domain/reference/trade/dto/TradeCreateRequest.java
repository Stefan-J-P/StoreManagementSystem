package io.github.stefanjp.storemanagement.domain.reference.trade.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TradeCreateRequest(
        @NotBlank(message = "Trade name is required")
        @Size(max = 100, message = "Trade name must be at most 100 characters")
        String tradeName
) {
}

