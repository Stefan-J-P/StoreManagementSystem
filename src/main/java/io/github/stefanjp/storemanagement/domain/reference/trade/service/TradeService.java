package io.github.stefanjp.storemanagement.domain.reference.trade.service;

import io.github.stefanjp.storemanagement.domain.reference.trade.dto.TradeCreateRequest;
import io.github.stefanjp.storemanagement.domain.reference.trade.dto.TradeResponse;
import io.github.stefanjp.storemanagement.domain.reference.trade.entity.Trade;
import io.github.stefanjp.storemanagement.domain.reference.trade.repository.TradeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TradeService {

    private final TradeRepository tradeRepository;

    @Transactional
    public TradeResponse create(TradeCreateRequest request) {
        Trade trade = Trade.builder()
                .tradeName(request.tradeName().trim())
                .build();

        Trade saved = tradeRepository.save(trade);
        return toResponse(saved);
    }

    public List<TradeResponse> findAll() {
        return tradeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TradeResponse findById(Long id) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trade not found"));

        return toResponse(trade);
    }

    private TradeResponse toResponse(Trade trade) {
        return new TradeResponse(trade.getId(), trade.getTradeName());
    }
}

