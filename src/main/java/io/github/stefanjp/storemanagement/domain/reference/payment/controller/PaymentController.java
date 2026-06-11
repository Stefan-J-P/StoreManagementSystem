package io.github.stefanjp.storemanagement.domain.reference.payment.controller;

import io.github.stefanjp.storemanagement.domain.reference.payment.dto.PaymentCreateRequest;
import io.github.stefanjp.storemanagement.domain.reference.payment.dto.PaymentResponse;
import io.github.stefanjp.storemanagement.domain.reference.payment.service.PaymentService;
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
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse create(@Valid @RequestBody PaymentCreateRequest request) {
        return paymentService.create(request);
    }

    @GetMapping
    public List<PaymentResponse> findAll() {
        return paymentService.findAll();
    }

    @GetMapping("/{id}")
    public PaymentResponse findById(@PathVariable Long id) {
        return paymentService.findById(id);
    }
}

