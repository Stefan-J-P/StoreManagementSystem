package io.github.stefanjp.storemanagement.domain.reference.country.service;

import io.github.stefanjp.storemanagement.domain.reference.country.dto.CountryCreateRequest;
import io.github.stefanjp.storemanagement.domain.reference.country.dto.CountryResponse;
import io.github.stefanjp.storemanagement.domain.reference.country.entity.Country;
import io.github.stefanjp.storemanagement.domain.reference.country.repository.CountryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CountryService {

    private final CountryRepository countryRepository;

    @Transactional
    public CountryResponse create(CountryCreateRequest request) {
        Country country = Country.builder()
                .name(request.name().trim())
                .build();

        Country saved = countryRepository.save(country);
        return toResponse(saved);
    }

    public List<CountryResponse> findAll() {
        return countryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CountryResponse findById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found"));

        return toResponse(country);
    }

    private CountryResponse toResponse(Country country) {
        return new CountryResponse(country.getId(), country.getName());
    }
}


