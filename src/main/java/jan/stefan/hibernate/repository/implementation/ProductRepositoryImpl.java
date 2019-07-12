package jan.stefan.hibernate.repository.implementation;

import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Product;
import jan.stefan.hibernate.repository.generic.AbstractGenericRepository;
import jan.stefan.hibernate.repository.repositoryInterfaces.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class ProductRepositoryImpl extends AbstractGenericRepository<Product, Long> implements ProductRepository
{
    public Optional<Product> findOneByName(String name)
    {
        if ( name == null )
        {
            throw new MyException("PRODUCT REPOSITORY IMPL: findOneByName() - NAME ARGUMENT IS NULL");
        }

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<Product> item = Optional.empty();

        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            item = entityManager
                    .createQuery("select p from Product p where p.name = :name", Product.class)
                    .setParameter("name", name)
                    .getResultList()
                    .stream()
                    .findFirst();
            tx.commit();

        } catch (Exception e)
        {
            if (tx != null) {
                tx.rollback();
            }
            throw new MyException("Product repository find one by name exception");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return item;
    }


}
