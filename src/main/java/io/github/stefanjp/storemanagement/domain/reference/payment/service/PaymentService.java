package io.github.stefanjp.storemanagement.domain.reference.payment.service;

import io.github.stefanjp.storemanagement.domain.reference.payment.dto.PaymentCreateRequest;
import io.github.stefanjp.storemanagement.domain.reference.payment.dto.PaymentResponse;
import io.github.stefanjp.storemanagement.domain.reference.payment.entity.Payment;
import io.github.stefanjp.storemanagement.domain.reference.payment.repository.PaymentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentResponse create(PaymentCreateRequest request) {
        Payment payment = Payment.builder()
                .paymentType(request.paymentType().trim())
                .build();

        Payment saved = paymentRepository.save(payment);
        return toResponse(saved);
    }

    public List<PaymentResponse> findAll() {
        return paymentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PaymentResponse findById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));

        return toResponse(payment);
    }

    private PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(payment.getId(), payment.getPaymentType());
    }
}

