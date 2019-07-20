package jan.stefan.hibernate.repository.implementation;

import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Shop;
import jan.stefan.hibernate.repository.generic.AbstractGenericRepository;
import jan.stefan.hibernate.repository.repositoryInterfaces.ShopRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class ShopRepositoryImpl extends AbstractGenericRepository<Shop, Long> implements ShopRepository
{
    public Optional<Shop> findOneByName(String name)
    {
        if ( name == null )
        {
            throw new MyException("SHOP REPOSITORY IMPL: findOneByName() - NAME ARGUMENT IS NULL");
        }

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<Shop> item = Optional.empty();

        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            item = entityManager
                    .createQuery("select s from Shop s where s.name = :name", Shop.class)
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
