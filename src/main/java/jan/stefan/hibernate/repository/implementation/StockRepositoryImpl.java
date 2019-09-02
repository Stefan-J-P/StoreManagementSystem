package jan.stefan.hibernate.repository.implementation;

import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Shop;
import jan.stefan.hibernate.model.Stock;
import jan.stefan.hibernate.repository.generic.AbstractGenericRepository;
import jan.stefan.hibernate.repository.repositoryInterfaces.StockRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class StockRepositoryImpl extends AbstractGenericRepository<Stock, Long> implements StockRepository
{
    public Optional<Stock> findOneByName(String name)
    {
        if ( name == null )
        {
            throw new MyException("STOCK REPOSITORY IMPL: findOneByName() - NAME ARGUMENT IS NULL");
        }

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<Stock> item = Optional.empty();

        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            item = entityManager
                    .createQuery("select s from Stock s where s.name = :name", Stock.class)
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
            throw new MyException("Stock repository find one by name exception");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return item;
    }
}
