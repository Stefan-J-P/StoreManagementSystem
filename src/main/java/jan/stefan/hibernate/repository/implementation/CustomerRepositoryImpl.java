package jan.stefan.hibernate.repository.implementation;

import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Customer;
import jan.stefan.hibernate.repository.repositoryInterfaces.CustomerRepository;
import jan.stefan.hibernate.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class CustomerRepositoryImpl extends AbstractGenericRepository<Customer, Long> implements CustomerRepository
{
    public Optional<Customer> findOneByEmail(String email)
    {
        if (email == null )
        {
            throw new MyException("CUSTOMER REPOSITORY IMPL: findOneByEmail() - NAME ARGUMENT IS NULL");
        }

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<Customer> item = Optional.empty();

        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            item = entityManager
                    .createQuery("select c from Customer c where c.email = :email", Customer.class)
                    .setParameter("email", email)
                    .getResultList()
                    .stream()
                    .findFirst();
            tx.commit();

        } catch (Exception e)
        {
            if (tx != null) {
                tx.rollback();
            }
            throw new MyException("Customer repository find one by email exception");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return item;
    }

    @Override
    public Optional<Customer> findOneByName(String name)
    {
        if (name == null )
        {
            throw new MyException("CUSTOMER REPOSITORY IMPL: findOneByEmail() - NAME ARGUMENT IS NULL");
        }

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<Customer> item = Optional.empty();

        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            item = entityManager
                    .createQuery("select c from Customer c where c.name = :name", Customer.class)
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
            throw new MyException("Customer repository find one by email exception");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return item;
    }

    @Override
    public Optional<Customer> findOneBySurname(String surname)
    {
        if (surname == null )
        {
            throw new MyException("CUSTOMER REPOSITORY IMPL: findOneByEmail() - NAME ARGUMENT IS NULL");
        }

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<Customer> item = Optional.empty();

        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            item = entityManager
                    .createQuery("select c from Customer c where c.surname = :surname", Customer.class)
                    .setParameter("surname", surname)
                    .getResultList()
                    .stream()
                    .findFirst();
            tx.commit();

        } catch (Exception e)
        {
            if (tx != null) {
                tx.rollback();
            }
            throw new MyException("Customer repository find one by email exception");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return item;
    }
}
