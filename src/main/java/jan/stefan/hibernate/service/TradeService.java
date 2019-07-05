package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dto.TradeDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Trade;
import jan.stefan.hibernate.repository.repositoryInterfaces.TradeRepository;
import jan.stefan.hibernate.service.mappers.ModelMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TradeService
{
    private final TradeRepository tradeRepository;

    public TradeDto addOrUpdate(TradeDto tradeDto)
    {
        if (tradeDto == null)
        {
            throw new MyException("TRADE SERVICE: addOrUpdate() tradeDto object argument is null");
        }

        Trade trade = ModelMapper.fromTradeDtoToTrade(tradeDto);
        return ModelMapper.fromTradeToTradeDto(tradeRepository
                .saveOrUpdate(trade)
                .orElseThrow(() -> new MyException("TRADE SERVICE: addOrUpdate() cannot add/update trade"))
        );
    }


}
