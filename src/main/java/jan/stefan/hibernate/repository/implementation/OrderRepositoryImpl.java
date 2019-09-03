package jan.stefan.hibernate.repository.implementation;

import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.Order;
import jan.stefan.hibernate.repository.repositoryInterfaces.OrderRepository;
import jan.stefan.hibernate.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class OrderRepositoryImpl extends AbstractGenericRepository<Order, Long> implements OrderRepository
{
    public Optional<Order> findOneByNumber(Integer number)
    {

        if ( number == null )
        {
            throw new MyException("CUSTOMER REPOSITORY IMPL: findOneByEmail() - NAME ARGUMENT IS NULL");
        }

        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<Order> item = Optional.empty();

        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();

            item = entityManager
                    .createQuery("select o from Order o where o.orderNumber = :number", Order.class)
                    .setParameter("number", number)
                    .getResultList()
                    .stream()
                    .findFirst();
            tx.commit();

        } catch (Exception e)
        {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            throw new MyException("ORDER REPOSITORY:  findOneByNumber():  exception");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return item;


        /*
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        Optional<Order> item = Optional.empty();

        try
        {
            tx.begin();

            item = Optional.ofNullable(entityManager.find(Order.class, number));

            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return item; */
    }
}
