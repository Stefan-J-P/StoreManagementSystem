package jan.stefan.hibernate.repository.implementation;

import jan.stefan.hibernate.dto.modelDto.CountryDto;
import jan.stefan.hibernate.exceptions.MyException;
import jan.stefan.hibernate.model.MyOrder;
import jan.stefan.hibernate.repository.repositoryInterfaces.OrderRepository;
import jan.stefan.hibernate.repository.generic.AbstractGenericRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderRepositoryImpl extends AbstractGenericRepository<MyOrder, Long> implements OrderRepository
{
    // ------------------------------------ FIND ONE BY NUMBER ------------------------------------------
    public Optional<MyOrder> findOneByNumber(Integer number)
    {
        if ( number == null )
        {
            throw new MyException("ORDER REPOSITORY IMPL: findOneByNumber() - NAME ARGUMENT IS NULL");
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

    // ------------------------------------ FIND LAST ORDER NUMBER ---------------------------------------------
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
                    .createQuery("select orderNumber from MyOrder order by orderNumber desc", Integer.class)
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
            throw new MyException("ORDER REPOSITORY: findLastOrderNumber() : exception");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return item;
    }

    @Override
    public List<MyOrder> getProductsByCustomersCountry(CountryDto countryDto, int ageFrom, int ageTo)
    {
        EntityManager em = null;
        EntityTransaction et = null;
        List<MyOrder> items;

        try
        {
            em = entityManagerFactory.createEntityManager();
            et = em.getTransaction();
            et.begin();

            items = em
                    .createQuery("SELECT m FROM myorders m JOIN customers c ON m.customer_id = c.id JOIN countries k ON c.country_id = k.id WHERE k.name = ? AND c.age BETWEEN ? AND ?", MyOrder.class)
                    .getResultList();

            et.commit();

        } catch (Exception e)
        {
            if (et != null) {
                et.rollback();
            }
            e.printStackTrace();
            throw new MyException("ORDER REPOSITORY: getProductsByCustomersCountry() : exception");
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return items;
    }










}





















