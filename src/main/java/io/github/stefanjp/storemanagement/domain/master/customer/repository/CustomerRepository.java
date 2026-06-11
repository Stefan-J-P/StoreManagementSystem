package io.github.stefanjp.storemanagement.domain.master.customer.repository;

import io.github.stefanjp.storemanagement.domain.master.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

