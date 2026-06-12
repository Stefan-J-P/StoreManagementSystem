package io.github.stefanjp.storemanagement.domain.transaction.orderitem.service;

import io.github.stefanjp.storemanagement.domain.master.product.entity.Product;
import io.github.stefanjp.storemanagement.domain.master.product.repository.ProductRepository;
import io.github.stefanjp.storemanagement.domain.transaction.order.entity.Order;
import io.github.stefanjp.storemanagement.domain.transaction.order.repository.OrderRepository;
import io.github.stefanjp.storemanagement.domain.transaction.orderitem.dto.OrderItemCreateRequest;
import io.github.stefanjp.storemanagement.domain.transaction.orderitem.dto.OrderItemResponse;
import io.github.stefanjp.storemanagement.domain.transaction.orderitem.entity.OrderItem;
import io.github.stefanjp.storemanagement.domain.transaction.orderitem.repository.OrderItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderItemResponse create(OrderItemCreateRequest request) {
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(request.quantity())
                .unitPrice(request.unitPrice())
                .build();

        OrderItem saved = orderItemRepository.save(orderItem);
        return toResponse(saved);
    }

    public List<OrderItemResponse> findAll() {
        return orderItemRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public OrderItemResponse findById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));

        return toResponse(orderItem);
    }

    private OrderItemResponse toResponse(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getOrder().getId(),
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice()
        );
    }
}

