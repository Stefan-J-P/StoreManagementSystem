package jan.stefan.hibernate.repository.implementation;

import jan.stefan.hibernate.model.Customer;
import jan.stefan.hibernate.repository.repositoryInterfaces.CustomerRepository;
import jan.stefan.hibernate.repository.generic.AbstractGenericRepository;

public class CustomerRepositoryImpl extends AbstractGenericRepository<Customer, Long> implements CustomerRepository
{
}
