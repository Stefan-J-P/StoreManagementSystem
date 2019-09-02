package jan.stefan.hibernate.repository.repositoryInterfaces;

import jan.stefan.hibernate.model.Stock;
import jan.stefan.hibernate.repository.generic.GenericRepository;

import java.util.Optional;

public interface StockRepository extends GenericRepository<Stock, Long>
{
    Optional<Stock> findOneByName(String name);
}
