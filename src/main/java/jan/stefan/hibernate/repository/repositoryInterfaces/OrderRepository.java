package jan.stefan.hibernate.repository.repositoryInterfaces;

import jan.stefan.hibernate.model.MyOrder;
import jan.stefan.hibernate.repository.generic.GenericRepository;

import java.util.Optional;

public interface OrderRepository extends GenericRepository<MyOrder, Long>
{
    Optional<MyOrder> findOneByNumber(Integer number);
    Optional<Integer> findLastOrderNumber();

}


