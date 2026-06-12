package io.github.stefanjp.storemanagement.domain.transaction.orderitem.controller;

import io.github.stefanjp.storemanagement.domain.transaction.orderitem.dto.OrderItemCreateRequest;
import io.github.stefanjp.storemanagement.domain.transaction.orderitem.dto.OrderItemResponse;
import io.github.stefanjp.storemanagement.domain.transaction.orderitem.service.OrderItemService;
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
@RequestMapping("/api/v1/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderItemResponse create(@Valid @RequestBody OrderItemCreateRequest request) {
        return orderItemService.create(request);
    }

    @GetMapping
    public List<OrderItemResponse> findAll() {
        return orderItemService.findAll();
    }

    @GetMapping("/{id}")
    public OrderItemResponse findById(@PathVariable Long id) {
        return orderItemService.findById(id);
    }
}

