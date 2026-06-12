package io.github.stefanjp.storemanagement.domain.transaction.order.service;

import io.github.stefanjp.storemanagement.domain.master.customer.entity.Customer;
import io.github.stefanjp.storemanagement.domain.master.customer.repository.CustomerRepository;
import io.github.stefanjp.storemanagement.domain.master.product.entity.Product;
import io.github.stefanjp.storemanagement.domain.master.product.repository.ProductRepository;
import io.github.stefanjp.storemanagement.domain.reference.payment.entity.Payment;
import io.github.stefanjp.storemanagement.domain.reference.payment.repository.PaymentRepository;
import io.github.stefanjp.storemanagement.domain.transaction.order.dto.OrderCreateRequest;
import io.github.stefanjp.storemanagement.domain.transaction.order.dto.OrderResponse;
import io.github.stefanjp.storemanagement.domain.transaction.order.dto.PlaceOrderItemRequest;
import io.github.stefanjp.storemanagement.domain.transaction.order.dto.PlaceOrderRequest;
import io.github.stefanjp.storemanagement.domain.transaction.order.dto.PlaceOrderResponse;
import io.github.stefanjp.storemanagement.domain.transaction.order.entity.Order;
import io.github.stefanjp.storemanagement.domain.transaction.order.entity.OrderStatus;
import io.github.stefanjp.storemanagement.domain.transaction.order.event.OrderCreatedEvent;
import io.github.stefanjp.storemanagement.domain.transaction.order.messaging.OrderEventProducer;
import io.github.stefanjp.storemanagement.domain.transaction.order.repository.OrderRepository;
import io.github.stefanjp.storemanagement.domain.transaction.orderitem.entity.OrderItem;
import io.github.stefanjp.storemanagement.domain.transaction.orderitem.repository.OrderItemRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderEventProducer orderEventProducer;

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
        orderEventProducer.publishOrderCreated(new OrderCreatedEvent(
                saved.getId(),
                customer.getId(),
                payment.getId(),
                saved.getOrderDate(),
                saved.getStatus().name(),
                BigDecimal.ZERO
        ));
        return toResponse(saved);
    }

    @Transactional
    public PlaceOrderResponse placeOrder(PlaceOrderRequest request) {
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

        Order savedOrder = orderRepository.save(order);

        List<PlaceOrderResponse.ItemResponse> items = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (PlaceOrderItemRequest itemRequest : request.items()) {
            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            BigDecimal unitPrice = product.getPrice();

            OrderItem orderItem = OrderItem.builder()
                    .order(savedOrder)
                    .product(product)
                    .quantity(itemRequest.quantity())
                    .unitPrice(unitPrice)
                    .build();

            orderItemRepository.save(orderItem);

            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(itemRequest.quantity()));
            totalAmount = totalAmount.add(lineTotal);

            items.add(new PlaceOrderResponse.ItemResponse(
                    product.getId(),
                    product.getName(),
                    itemRequest.quantity(),
                    unitPrice,
                    lineTotal
            ));
        }

        orderEventProducer.publishOrderCreated(new OrderCreatedEvent(
                savedOrder.getId(),
                customer.getId(),
                payment.getId(),
                savedOrder.getOrderDate(),
                savedOrder.getStatus().name(),
                totalAmount
        ));

        return new PlaceOrderResponse(
                savedOrder.getId(),
                customer.getId(),
                savedOrder.getStatus().name(),
                totalAmount,
                items
        );
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

