package cz.kodytek.shop.data.connection;

import org.hibernate.Session;

import javax.persistence.PersistenceException;

public interface OpenedSessionFunction {
    void invoke(Session session) throws PersistenceException;
}
