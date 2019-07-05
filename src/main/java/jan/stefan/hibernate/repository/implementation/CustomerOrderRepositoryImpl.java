package jan.stefan.hibernate.repository.implementation;

import jan.stefan.hibernate.model.CustomerOrder;
import jan.stefan.hibernate.repository.repositoryInterfaces.CustomerOrderRepository;
import jan.stefan.hibernate.repository.generic.AbstractGenericRepository;

public class CustomerOrderRepositoryImpl extends AbstractGenericRepository<CustomerOrder, Long> implements CustomerOrderRepository
{
}
