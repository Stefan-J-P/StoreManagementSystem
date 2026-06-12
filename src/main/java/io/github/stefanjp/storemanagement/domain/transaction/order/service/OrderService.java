package io.github.stefanjp.storemanagement.domain.transaction.order.service;

import io.github.stefanjp.storemanagement.domain.master.customer.entity.Customer;
import io.github.stefanjp.storemanagement.domain.master.customer.repository.CustomerRepository;
import io.github.stefanjp.storemanagement.domain.reference.payment.entity.Payment;
import io.github.stefanjp.storemanagement.domain.reference.payment.repository.PaymentRepository;
import io.github.stefanjp.storemanagement.domain.transaction.order.dto.OrderCreateRequest;
import io.github.stefanjp.storemanagement.domain.transaction.order.dto.OrderResponse;
import io.github.stefanjp.storemanagement.domain.transaction.order.entity.Order;
import io.github.stefanjp.storemanagement.domain.transaction.order.entity.OrderStatus;
import io.github.stefanjp.storemanagement.domain.transaction.order.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public OrderResponse create(OrderCreateRequest request) {
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Payment payment = paymentRepository.findById(request.paymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        Order order = Order.builder()
                .customer(customer)
                .payment(payment)
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.NEW)
                .build();

        Order saved = orderRepository.save(order);
        return toResponse(saved);
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public OrderResponse findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return toResponse(order);
    }

    private OrderResponse toResponse(Order order) {
        Customer customer = order.getCustomer();
        Payment payment = order.getPayment();
        return new OrderResponse(
                order.getId(),
                customer.getId(),
                customer.getFirstName() + " " + customer.getLastName(),
                payment.getId(),
                payment.getPaymentType(),
                order.getOrderDate(),
                order.getStatus().name()
        );
    }
}

