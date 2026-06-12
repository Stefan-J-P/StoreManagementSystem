package io.github.stefanjp.storemanagement.domain.transaction.order.repository;

import io.github.stefanjp.storemanagement.domain.transaction.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

