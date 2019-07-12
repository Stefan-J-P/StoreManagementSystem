package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dto.modelDto.ShopDto;
import jan.stefan.hibernate.dto.modelDto.StockDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Shop;
import jan.stefan.hibernate.model.Stock;
import jan.stefan.hibernate.repository.repositoryInterfaces.ShopRepository;
import jan.stefan.hibernate.repository.repositoryInterfaces.StockRepository;
import jan.stefan.hibernate.service.mappers.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StockService
{
    private final StockRepository stockRepository;
    private final ShopRepository shopRepository;

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


    private Shop checkAddOrUpdateCountry(ShopDto shopDto)
    {
        if (shopDto == null)
        {
            throw new MyException("STOCK SERVICE: checkAddOrUpdateShop() // shopDto object is null");
        }
        Shop shop = ModelMapper.fromShopDtoToShop(shopDto);
        Shop shopFromDb = null;
        /*
        if (shop.getId() == null)    // find country by its ID
        {   // if country doesn't have ID ---> find country in DB by its name
            shopFromDb = shopRepository.findOneByName(shop.getName()).orElse(null);


            if (countryFromDb == null)
            {   // if country from db doesn't have name ---> add new country to data base
                countryFromDb = countryRepository.saveOrUpdate(country).orElseThrow(() -> new MyException("SHOP SERVICE: checkAddOrUpdateCountry() // countryFromDb EXCEPTION"));
                return countryFromDb;
            }
            return countryFromDb;
        }
        else
        {   // if country does have ID ---> return it
            Country countryWithId = countryRepository.findById(country.getId()).orElseThrow(() -> new MyException("SHOP SERVICE: checkAddOrUpdateCountry() // countryFromDb EXCEPTION"));
            System.out.println("--------------------------------");
            System.out.println("COUNTRY WITH ID = " + countryWithId.getId());
            System.out.println("--------------------------------");
            return countryWithId;
        }   */
        return null;
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
