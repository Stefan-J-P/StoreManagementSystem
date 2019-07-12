package jan.stefan.hibernate.dataInDbValidation;

import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Category;
import jan.stefan.hibernate.model.Country;
import jan.stefan.hibernate.model.Producer;
import jan.stefan.hibernate.repository.repositoryInterfaces.*;
import jan.stefan.hibernate.service.mappers.ModelMapper;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
@RequiredArgsConstructor
public class AbstractDataValidator<T, REPO extends Serializable> implements DataValidator<T, REPO>
{
    /*
    private final CategoryRepository categoryRepository;
    private final CountryRepository countryRepository;
    private final CustomerRepository customerRepository;
    private final ProducerRepository producerRepository;
    private final ProductRepository productRepository;      */


    @Override
    @SuppressWarnings("Duplicates")
    public T findObjectByIdAndName(T t, REPO repo)
    {
        /*
        if (t == null)
        {
            throw new MyException("checkAddOrUpdateCountry() // countryDto object is null");
        }
        Country country = ModelMapper.fromCountryDtoToCountry(t);
        Country countryFromDb = null;

        if (country.getId() == null)    // find country by its ID
        {   // if country doesn't have ID ---> find country in DB by its name
            countryFromDb = repo.findOneByName(country.getName()).orElse(null);

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
        }       */


        return null;
    }
}
