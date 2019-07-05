package jan.stefan.hibernate.connection;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DbConnection
{
    private static DbConnection ourInstance = new DbConnection();
    public static DbConnection getInstance()
    {
        return ourInstance;
    }
    private DbConnection() {}

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HBN");

    public EntityManagerFactory getEntityManagerFactory()
    {
        return entityManagerFactory;
    }

    public void close()
    {
        if (entityManagerFactory != null)
        {
            entityManagerFactory.close();
        }
    }

}
