package jan.stefan.hibernate.repository.implementation;

import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.MyOrder;
import jan.stefan.hibernate.repository.repositoryInterfaces.OrderRepository;
import jan.stefan.hibernate.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderRepositoryImpl extends AbstractGenericRepository<MyOrder, Long> implements OrderRepository
{
    public Optional<MyOrder> findOneByNumber(Integer number)
    {
        if ( number == null )
        {
            throw new MyException("CUSTOMER REPOSITORY IMPL: findOneByNumber() - NAME ARGUMENT IS NULL");
        }
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<MyOrder> item = Optional.empty();
        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();

            item = entityManager
                    .createQuery("select o from MyOrder o where o.orderNumber = :number", MyOrder.class)
                    .setParameter("number", number)
                    .getResultList()
                    .stream()
                    .findFirst();
            tx.commit();

        } catch (Exception e)
        {
            if (tx != null)
            {
                tx.rollback();
            }
            e.printStackTrace();
            throw new MyException("ORDER REPOSITORY:  findOneByNumber():  exception");
        } finally {
            if (entityManager != null)
            {
                entityManager.close();
            }
        }
        return item;
        
    }


    public Optional<Integer> findLastOrderNumber()
    {
        EntityManager entityManager = null;
        EntityTransaction tx = null;
        Optional<Integer> item = Optional.empty();

        try
        {
            entityManager = entityManagerFactory.createEntityManager();
            tx = entityManager.getTransaction();
            tx.begin();
            item = entityManager
                    .createQuery("select orderNumber from MyOrder order by orderNumber desc")
                    .getResultList()
                    .stream()
                    .findFirst();
                    //.orElseThrow(() -> new MyException("ORDER REPOSITORY: findLastOrderNumber() : exception"));
            tx.commit();

        } catch (Exception e)
        {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            throw new MyException("ORDER REPOSITORY: findLastOrderNumber() : exception");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return item;
    }






}
