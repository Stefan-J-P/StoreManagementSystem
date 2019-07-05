package jan.stefan.hibernate.repository.implementation;

import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Country;
import jan.stefan.hibernate.repository.repositoryInterfaces.CountryRepository;
import jan.stefan.hibernate.repository.generic.AbstractGenericRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Optional;

public class CountryRepositoryImpl extends AbstractGenericRepository<Country, Long> implements CountryRepository
{

    public Optional<Country> findOneByName(String name)
    {
        if ( name == null )
        {
            throw new MyException("FIND BY NAME - NAME IS NULL");
        }

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<Country> item = Optional.empty();

        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            item = entityManager
                    .createQuery("select c from Country c where c.name = :name", Country.class)
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
            throw new MyException("country repository find one by name exception");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return item;
    }

}
