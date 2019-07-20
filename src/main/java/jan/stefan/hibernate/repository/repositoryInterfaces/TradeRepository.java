package jan.stefan.hibernate.repository.repositoryInterfaces;

import jan.stefan.hibernate.model.Trade;
import jan.stefan.hibernate.repository.generic.GenericRepository;

import java.util.Optional;

public interface TradeRepository extends GenericRepository<Trade, Long>
{
    Optional<Trade> findOneByName(String name);
}
