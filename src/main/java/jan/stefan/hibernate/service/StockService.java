package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dto.modelDto.StockDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Stock;
import jan.stefan.hibernate.repository.repositoryInterfaces.StockRepository;
import jan.stefan.hibernate.service.mappers.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StockService
{
    private final StockRepository stockRepository;

    public StockDto addOrUpdate(StockDto stockDto)
    {
        if (stockDto == null)
        {
            throw new MyException("STOCK SERVICE: addOrUpdate() stockDto object argument is null");
        }

        Stock stock = ModelMapper.fromStockDtoToStock(stockDto);
        return ModelMapper.fromStockToStockDto(stockRepository
                .saveOrUpdate(stock)
                .orElseThrow(() -> new MyException("STOCK SERVICE: addOrUpdate() cannot add / update stock")));
    }

    public List<StockDto> findAll()
    {
        return stockRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromStockToStockDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id)
    {
        stockRepository.delete(id);
    }

    public StockDto findOneById(Long id)
    {
        return stockRepository
                .findById(id)
                .map(ModelMapper::fromStockToStockDto)
                .orElseThrow(() -> new MyException("STOCK SERVICE: findOneById() cannot find id" + id));
    }


}
