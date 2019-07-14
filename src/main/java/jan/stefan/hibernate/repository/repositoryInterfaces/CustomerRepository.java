package jan.stefan.hibernate.repository.repositoryInterfaces;

import jan.stefan.hibernate.model.Customer;
import jan.stefan.hibernate.repository.generic.GenericRepository;

import java.util.Optional;

public interface CustomerRepository extends GenericRepository<Customer, Long>
{
    Optional<Customer> findOneByName(String name);
}
