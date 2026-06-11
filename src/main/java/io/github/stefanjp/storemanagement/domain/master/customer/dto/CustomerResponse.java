package io.github.stefanjp.storemanagement.domain.master.customer.dto;

public record CustomerResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        Integer age,
        Long countryId,
        String countryName
) {
}

