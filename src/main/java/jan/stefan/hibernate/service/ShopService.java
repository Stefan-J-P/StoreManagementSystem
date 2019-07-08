package jan.stefan.hibernate.service;

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
    private final CountryRepository countryRepository;
    private final CountryService countryService;
    private final ScannerService scannerService;
    private final MyErrorService myErrorService;
    private final ShopValidation shopValidation;

    public void ifCountryIsNull(ShopDto shopDto)
    {
        Shop shop = ModelMapper.fromShopDtoToShop(shopDto);
        Country country = shop.getCountry();
        // jezeli nie ma id - pobieramy po nazwie
        Country countryFromDb = countryRepository
                .findOneByName(country.getName())
                // jezeli nie ma kraju o takiej nazwie to dodajemy taki do bazy
                .orElse(null);
        if (countryFromDb == null) {
            countryFromDb = countryRepository
                    .saveOrUpdate(country)
                    .orElseThrow(() -> new MyException("cannot add country: " + country));
        }
        shop.setCountry(countryFromDb);
    }


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

        /*
            1. sprawdzam czy jest kraj po id jezeli jest pobieram go i ustawiam dla shop
            2. sprawdz czy jest kraj po nazwie jezeli jest pobieram go i ustawiam dla shop
            3. jezeli nie ma kraju ani po id ani po nazwie a podano nazwe to wtedy dodajemy kraj a potem dodany
               kraj ustawiamy dla shop
        */

        Shop shop = ModelMapper.fromShopDtoToShop(shopDto);
        /*
        Country country = shop.getCountry();

        if (country == null)
        {
            throw new MyException("cannot add shop without country");
        }

        if (country.getId() == null)
        {

            // jezeli nie ma id - pobieramy po nazwie
            Country countryFromDb = countryRepository
                    .findOneByName(country.getName())
                    // jezeli nie ma kraju o takiej nazwie to dodajemy taki do bazy
                    .orElse(null);

            if (countryFromDb == null)
            {
                countryFromDb = countryRepository
                        .saveOrUpdate(country)
                        .orElseThrow(() -> new MyException("cannot add country: " + country));
            }

            System.out.println(country);
            shop.setCountry(countryFromDb);
        }
        else
        {
            // jezeli jest id pobieramy po id
            Country countryFromDb = countryRepository
                    .findById(country.getId())
                    // jezeli nie ma kraju o takim id to rzucamy wyjatek
                    .orElseThrow(() -> new MyException("no country with id " + country.getId()));

            shop.setCountry(countryFromDb);
        }       */

        Country result = checkAddOrUpdateCountry(shopDto.getCountryDto());
        shop.setCountry(result);

        return ModelMapper.fromShopToShopDto(shopRepository
                .saveOrUpdate(shop)
                .orElseThrow(() -> new MyException("SHOP SERVICE: addOrUpdate() exception: cannot addOrUpdate customer")));
    }


    public Country checkAddOrUpdateCountry(CountryDto countryDto)
    {
        if (countryDto == null)
        {
            throw new MyException("SHOP SERVICE: checkAddOrUpdateCountry() // countryDto object is null");
        }
        Country country = ModelMapper.fromCountryDtoToCountry(countryDto);
        Country countryFromDb = null;

        if (country.getId() == null)    // find country by its ID
        {   // if country doesn't have ID ---> find country in DB by its name
            countryFromDb = countryRepository.findOneByName(country.getName()).orElse(null);
            System.out.println("--------------------------------");
            System.out.println("COUNTRY FROM DB = " + countryFromDb.getName());
            System.out.println("--------------------------------");

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
        }
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


    public ShopDto addNewShop()
    {
        ShopDto shopDto = new ShopDto();

        shopDto.setName(scannerService.getString("Enter the name of the Shop:"));
        shopDto.setCountryDto(CountryDto.builder().name(scannerService.getString("Enter the name of the country:")).build());

        Map<String, String> errorsShop = shopValidation.validate(shopDto);

        if (!shopValidation.hasErrors())
        {
            addOrUpdate(shopDto);
        }
        else
        {
            errorsShop.forEach((k, v) -> System.out.println(k + " " + v));
            myErrorService.addOrUpdateOneMyError(MyErrorDto.builder()
                    .message("Error while inserting Shop into table")
                    .dateTime(LocalDateTime.now())
                    .build());
        }
        return shopDto;
    }
















}
