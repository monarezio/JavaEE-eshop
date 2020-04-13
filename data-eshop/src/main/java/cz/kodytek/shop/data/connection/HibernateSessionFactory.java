package cz.kodytek.shop.data.connection;

import cz.kodytek.shop.data.connection.interfaces.IHibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

@ApplicationScoped
public class HibernateSessionFactory implements IHibernateSessionFactory {

    private EntityManagerFactory entityManagerFactory;

    private SessionFactory sessionFactory;

    public void createSession(OpenedSessionFunction fn) throws PersistenceException {
        Transaction transaction = null;

        try (Session session = getEntityManager().unwrap(Session.class)) {
            transaction = session.beginTransaction();
            fn.invoke(session);
            transaction.commit();
        } catch (Exception e) {
            try {
                if (transaction != null) transaction.rollback();
            } catch (IllegalStateException ignored) {
            }

            throw e;
        }
    }

    private SessionFactory getSessionFactory() {
        if (sessionFactory == null) {

            // configures settings from hibernate.cfg.xml
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure() // configures settings from hibernate.cfg.xml
                    .build();

            try {
                sessionFactory = new MetadataSources(registry)
                        .buildMetadata()
                        .buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
        return sessionFactory;
    }

    private EntityManager getEntityManager() {
        if(entityManagerFactory == null)
            entityManagerFactory = Persistence.createEntityManagerFactory("eshop");
        return entityManagerFactory.createEntityManager();
    }
}
