package io.github.stefanjp.storemanagement.domain.master.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductCreateRequest(
        @NotBlank(message = "Product name is required")
        @Size(max = 200, message = "Product name must be at most 200 characters")
        String name,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
        BigDecimal price,

        @NotNull(message = "Category id is required")
        Long categoryId,

        @NotNull(message = "Trade id is required")
        Long tradeId,

        @NotNull(message = "Producer id is required")
        Long producerId
) {
}

