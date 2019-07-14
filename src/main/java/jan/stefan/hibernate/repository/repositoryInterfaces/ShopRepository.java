package jan.stefan.hibernate.repository.repositoryInterfaces;

import jan.stefan.hibernate.model.Shop;
import jan.stefan.hibernate.repository.generic.GenericRepository;

import java.util.Optional;

public interface ShopRepository extends GenericRepository<Shop, Long>
{
    Optional<Shop> findOneByName(String name);
}
