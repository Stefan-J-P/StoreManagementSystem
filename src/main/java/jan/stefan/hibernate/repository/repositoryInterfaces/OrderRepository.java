package jan.stefan.hibernate.repository.repositoryInterfaces;

import jan.stefan.hibernate.model.Order;
import jan.stefan.hibernate.repository.generic.GenericRepository;

import java.util.Optional;

public interface OrderRepository extends GenericRepository<Order, Long>
{
    Optional<Order> findOneByNumber(Long number);

}


