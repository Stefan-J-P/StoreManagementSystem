package io.github.stefanjp.storemanagement.domain.master.producer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProducerCreateRequest(
        @NotBlank(message = "Producer name is required")
        @Size(max = 150, message = "Producer name must be at most 150 characters")
        String name,

        @NotNull(message = "Country id is required")
        Long countryId
) {
}

