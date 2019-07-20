package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dataInDbValidation.DataBaseValidator;
import jan.stefan.hibernate.dto.modelDto.CountryDto;
import jan.stefan.hibernate.dto.modelDto.MyErrorDto;
import jan.stefan.hibernate.dto.modelDto.ShopDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Country;
import jan.stefan.hibernate.model.Shop;
import jan.stefan.hibernate.model.validation.ShopValidation;
import jan.stefan.hibernate.repository.repositoryInterfaces.CountryRepository;
import jan.stefan.hibernate.repository.repositoryInterfaces.ShopRepository;
import jan.stefan.hibernate.service.mappers.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ShopService
{
    private final ShopRepository shopRepository;
    private  final DataBaseValidator dataBaseValidator;

    public ShopDto addOrUpdate(ShopDto shopDto)
    {
        if (shopDto == null)
        {
            throw new MyException("SHOP SERVICE: Shop object is null");
        }

        if (shopDto.getCountryDto() == null)
        {
            throw new MyException("SHOP SERVICE: Country object is null");
        }

        Shop shop = ModelMapper.fromShopDtoToShop(shopDto);
        Country result = dataBaseValidator.countryDbValidator(shopDto.getCountryDto());
        shop.setCountry(result);

        return ModelMapper.fromShopToShopDto(shopRepository
                .saveOrUpdate(shop)
                .orElseThrow(() -> new MyException("SHOP SERVICE: addOrUpdate() exception: cannot addOrUpdate customer")));
    }

    public List<ShopDto> findAll()
    {
        return shopRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromShopToShopDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id)
    {
        shopRepository.delete(id);
    }

    public ShopDto findOneById(Long id)
    {
        return shopRepository.findById(id)
                .map(ModelMapper::fromShopToShopDto)
                .orElseThrow(() -> new MyException(""));
    }

}
