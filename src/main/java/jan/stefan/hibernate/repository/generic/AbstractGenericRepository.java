package jan.stefan.hibernate.repository.generic;

import jan.stefan.hibernate.connection.DbConnection;
import jan.stefan.hibernate.exceptions.MyException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractGenericRepository<T, ID extends Serializable> implements GenericRepository<T, ID>
{

    protected final EntityManagerFactory entityManagerFactory = DbConnection.getInstance().getEntityManagerFactory();
    private final Class<T> entityType = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private final Class<ID> idType = (Class<ID>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];



    @Override
    public Optional<T> saveOrUpdate(T t)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        Optional<T> inserted = Optional.empty();
        try {
            tx.begin();
            inserted = Optional.of((T)entityManager.merge(t));
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return inserted;
    }

    @Override
    public void delete(ID id)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();


        try {
            tx.begin();
            T item = entityManager.find(entityType, id);

            if (item == null)
            {
                throw new MyException("DELETE - NO ITEM WITH ID " + id);
            }

            entityManager.remove(item);
            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            throw new MyException("ABSTRACT REPOSITORY: delete() exception");


        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public Optional<T> findById(ID id)
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        Optional<T> item = Optional.empty();

        try
        {
            tx.begin();

            item = Optional.ofNullable(entityManager.find(entityType, id));

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
        return item;
    }

    @Override
    public List<T> findAll()
    {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        List<T> elements = new ArrayList<>();
        try
        {

            tx.begin();
            // SQL
            // select * from teams

            // HQL
            // select t from Team t
            elements = entityManager
                    // .createQuery("select t from Team t", Team.class)
                    .createQuery("select t from " + entityType.getCanonicalName() + " t", entityType)
                    .getResultList();
            tx.commit();
        } catch (Exception e) {
            if (entityManager != null) {
                tx.rollback();
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return elements;
    }
}
