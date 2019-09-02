package jan.stefan.hibernate.service;

import jan.stefan.hibernate.dto.modelDto.CountryDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Country;
import jan.stefan.hibernate.repository.repositoryInterfaces.CountryRepository;
import jan.stefan.hibernate.service.mappers.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CountryService
{
    private final CountryRepository countryRepository;

    public CountryDto addOrUpdate(CountryDto countryDto) {

        if (countryDto == null) {
            throw new MyException("COUNTRY SERVICE - addOrUpdate - country object argument is null");
        }

        Country country = ModelMapper.fromCountryDtoToCountry(countryDto);
        return ModelMapper
                .fromCountryToCountryDto(countryRepository
                        .saveOrUpdate(country)
                        .orElseThrow(() -> new MyException("COUNTRY SERVICE - addOrUpdate - cannot addOrUpdate country"))
                );
    }

    public List<CountryDto> findAll() {
        return countryRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromCountryToCountryDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id)
    {
        countryRepository.delete(id);;
    }

    public CountryDto findOneById(Long id)
    {
        return countryRepository
                .findById(id)
                .map(ModelMapper::fromCountryToCountryDto)
                .orElseThrow(()-> new MyException("COUNTRY SERVICE: findOneById() Cannot find id: " + id));
    }

    public CountryDto findOneByName(String name)
    {
        return countryRepository
                .findOneByName(name)
                .map(ModelMapper::fromCountryToCountryDto)
                .orElseThrow(() -> new MyException("COUNTRY SERVICE: findOneByName() : Cannot find name: " + name));
    }

}
