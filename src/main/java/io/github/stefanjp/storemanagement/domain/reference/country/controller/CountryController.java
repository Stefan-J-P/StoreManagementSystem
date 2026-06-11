package io.github.stefanjp.storemanagement.domain.reference.country.controller;

import io.github.stefanjp.storemanagement.domain.reference.country.dto.CountryCreateRequest;
import io.github.stefanjp.storemanagement.domain.reference.country.dto.CountryResponse;
import io.github.stefanjp.storemanagement.domain.reference.country.service.CountryService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/countries")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CountryResponse create(@Valid @RequestBody CountryCreateRequest request) {
        return countryService.create(request);
    }

    @GetMapping
    public List<CountryResponse> findAll() {
        return countryService.findAll();
    }

    @GetMapping("/{id}")
    public CountryResponse findById(@PathVariable Long id) {
        return countryService.findById(id);
    }
}


