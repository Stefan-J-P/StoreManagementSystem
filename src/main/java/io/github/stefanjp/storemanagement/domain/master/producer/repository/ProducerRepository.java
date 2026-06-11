package io.github.stefanjp.storemanagement.domain.master.producer.repository;

import io.github.stefanjp.storemanagement.domain.master.producer.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducerRepository extends JpaRepository<Producer, Long> {
}

