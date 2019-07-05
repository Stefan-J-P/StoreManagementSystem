package jan.stefan.hibernate.repository.implementation;

import jan.stefan.hibernate.model.Product;
import jan.stefan.hibernate.repository.generic.AbstractGenericRepository;
import jan.stefan.hibernate.repository.repositoryInterfaces.ProductRepository;

public class ProductRepositoryImpl extends AbstractGenericRepository<Product, Long> implements ProductRepository
{
}
