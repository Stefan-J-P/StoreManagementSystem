package io.github.stefanjp.storemanagement.domain.master.producer.controller;

import io.github.stefanjp.storemanagement.domain.master.producer.dto.ProducerCreateRequest;
import io.github.stefanjp.storemanagement.domain.master.producer.dto.ProducerResponse;
import io.github.stefanjp.storemanagement.domain.master.producer.service.ProducerService;
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
@RequestMapping("/api/v1/producers")
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerService producerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProducerResponse create(@Valid @RequestBody ProducerCreateRequest request) {
        return producerService.create(request);
    }

    @GetMapping
    public List<ProducerResponse> findAll() {
        return producerService.findAll();
    }

    @GetMapping("/{id}")
    public ProducerResponse findById(@PathVariable Long id) {
        return producerService.findById(id);
    }
}

