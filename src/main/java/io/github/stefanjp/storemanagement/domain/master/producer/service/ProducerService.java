package io.github.stefanjp.storemanagement.domain.master.producer.service;

import io.github.stefanjp.storemanagement.domain.master.producer.dto.ProducerCreateRequest;
import io.github.stefanjp.storemanagement.domain.master.producer.dto.ProducerResponse;
import io.github.stefanjp.storemanagement.domain.master.producer.entity.Producer;
import io.github.stefanjp.storemanagement.domain.master.producer.repository.ProducerRepository;
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
public class ProducerService {

    private final ProducerRepository producerRepository;
    private final CountryRepository countryRepository;

    @Transactional
    public ProducerResponse create(ProducerCreateRequest request) {
        Country country = countryRepository.findById(request.countryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found"));

        Producer producer = Producer.builder()
                .name(request.name().trim())
                .country(country)
                .build();

        Producer saved = producerRepository.save(producer);
        return toResponse(saved);
    }

    public List<ProducerResponse> findAll() {
        return producerRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProducerResponse findById(Long id) {
        Producer producer = producerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));

        return toResponse(producer);
    }

    private ProducerResponse toResponse(Producer producer) {
        return new ProducerResponse(
                producer.getId(),
                producer.getName(),
                producer.getCountry().getId(),
                producer.getCreatedAt(),
                producer.getUpdatedAt()
        );
    }
}

