package io.github.stefanjp.storemanagement.domain.master.producer.dto;

import java.time.LocalDateTime;

public record ProducerResponse(
        Long id,
        String name,
        Long countryId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

