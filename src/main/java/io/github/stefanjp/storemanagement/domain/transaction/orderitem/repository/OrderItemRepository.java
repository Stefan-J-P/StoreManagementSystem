package io.github.stefanjp.storemanagement.domain.transaction.orderitem.repository;

import io.github.stefanjp.storemanagement.domain.transaction.orderitem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}

