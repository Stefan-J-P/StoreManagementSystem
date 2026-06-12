package io.github.stefanjp.storemanagement.domain.transaction.order.controller;

import io.github.stefanjp.storemanagement.domain.transaction.order.dto.OrderCreateRequest;
import io.github.stefanjp.storemanagement.domain.transaction.order.dto.OrderResponse;
import io.github.stefanjp.storemanagement.domain.transaction.order.dto.PlaceOrderRequest;
import io.github.stefanjp.storemanagement.domain.transaction.order.dto.PlaceOrderResponse;
import io.github.stefanjp.storemanagement.domain.transaction.order.service.OrderService;
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
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@Valid @RequestBody OrderCreateRequest request) {
        return orderService.create(request);
    }

    @PostMapping("/place")
    @ResponseStatus(HttpStatus.CREATED)
    public PlaceOrderResponse placeOrder(@Valid @RequestBody PlaceOrderRequest request) {
        return orderService.placeOrder(request);
    }

    @GetMapping
    public List<OrderResponse> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable Long id) {
        return orderService.findById(id);
    }
}

