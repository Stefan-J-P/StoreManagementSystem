package io.github.stefanjp.storemanagement.domain.reference.country.repository;

import io.github.stefanjp.storemanagement.domain.reference.country.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}


