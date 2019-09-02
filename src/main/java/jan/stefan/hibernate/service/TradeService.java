package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dto.modelDto.TradeDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Trade;
import jan.stefan.hibernate.repository.repositoryInterfaces.TradeRepository;
import jan.stefan.hibernate.service.mappers.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<TradeDto> findAll()
    {
        return tradeRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromTradeToTradeDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id)
    {
        tradeRepository.delete(id);
    }

    public TradeDto findOneById(Long id)
    {
        return tradeRepository
                .findById(id)
                .map(ModelMapper::fromTradeToTradeDto)
                .orElseThrow(() -> new MyException("TRADE SERVICE: findOneById() cannot find id" + id));
    }

    public TradeDto findOneByName(String name)
    {
        return tradeRepository
                .findOneByName(name)
                .map(ModelMapper::fromTradeToTradeDto)
                .orElseThrow(() -> new MyException("TRADE SERVICE: findOneById() cannot find name: " + name));
    }

}
