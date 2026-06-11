package io.github.stefanjp.storemanagement.domain.reference.trade.repository;

import io.github.stefanjp.storemanagement.domain.reference.trade.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}

