package cz.kodytek.shop.data.connection.interfaces;

import cz.kodytek.shop.data.connection.OpenedSessionFunction;

import javax.persistence.PersistenceException;

public interface IHibernateSessionFactory {
    void createSession(OpenedSessionFunction fn) throws PersistenceException;
}
