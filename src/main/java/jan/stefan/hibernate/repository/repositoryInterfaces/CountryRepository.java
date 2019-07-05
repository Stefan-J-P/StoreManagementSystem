package jan.stefan.hibernate.repository.repositoryInterfaces;

import jan.stefan.hibernate.model.Country;
import jan.stefan.hibernate.repository.generic.GenericRepository;

import java.util.Optional;

public interface CountryRepository extends GenericRepository<Country, Long>
{
    Optional<Country> findOneByName(String name);
}
