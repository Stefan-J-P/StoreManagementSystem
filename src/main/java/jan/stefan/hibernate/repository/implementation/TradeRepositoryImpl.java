package jan.stefan.hibernate.repository.implementation;

import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Trade;
import jan.stefan.hibernate.repository.generic.AbstractGenericRepository;
import jan.stefan.hibernate.repository.repositoryInterfaces.TradeRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class TradeRepositoryImpl extends AbstractGenericRepository<Trade, Long> implements TradeRepository
{
    @Override
    public Optional<Trade> findOneByName(String name)
    {
        if ( name == null )
        {
            throw new MyException("SHOP REPOSITORY IMPL: findOneByName() - NAME ARGUMENT IS NULL");
        }

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<Trade> item = Optional.empty();

        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            item = entityManager
                    .createQuery("select t from Trade t where t.tradeName = :name", Trade.class)
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
            throw new MyException("Shop repository find one by name exception");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return item;
    }
}
