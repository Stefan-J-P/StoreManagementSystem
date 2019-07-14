package jan.stefan.hibernate.repository.repositoryInterfaces;

import jan.stefan.hibernate.model.Product;
import jan.stefan.hibernate.repository.generic.GenericRepository;

import java.util.Optional;

public interface ProductRepository extends GenericRepository<Product, Long>
{
    Optional<Product> findOneByName(String name);
}
