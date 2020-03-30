package cz.kodytek.shop.domain.services.address;

import cz.kodytek.shop.data.connection.interfaces.IHibernateSessionFactory;
import cz.kodytek.shop.data.entities.Address;
import cz.kodytek.shop.data.entities.User;
import cz.kodytek.shop.data.entities.interfaces.address.IAddress;
import cz.kodytek.shop.data.entities.interfaces.address.IAddressWithId;
import cz.kodytek.shop.domain.services.interfaces.address.IAddressService;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class AddressService implements IAddressService {

    @Inject
    private IHibernateSessionFactory hibernateSessionFactory;

    @Override
    public IAddressWithId get(long userId, long addressId) {
        AtomicReference<Address> result = new AtomicReference<>();

        hibernateSessionFactory.createSession(s -> {
            result.set(getAddress(s, userId, addressId));
        });

        return result.get();
    }

    @Override
    public List<IAddressWithId> getAllForUser(long userId) {
        List<IAddressWithId> result = new ArrayList<>();

        hibernateSessionFactory.createSession(s -> {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Address> cq = cb.createQuery(Address.class);
            Root<Address> root = cq.from(Address.class);

            Predicate predicate = cb.and(
                    cb.equal(root.get("user"), new User(userId))
            );
            cq.where(predicate);

            result.addAll(s.createQuery(cq).getResultList());
        });

        return result;
    }

    @Override
    public boolean edit(long userId, IAddressWithId address) {
        AtomicBoolean result = new AtomicBoolean(false);

        try {
            hibernateSessionFactory.createSession(s -> {
                Address a = getAddress(s, userId, address.getId());
                if (a != null) {
                    a.setCity(address.getCity());
                    a.setPostCode(address.getPostCode());
                    a.setStreet(address.getStreet());
                    s.save(a);
                    result.set(true);
                }
            });
        } catch (PersistenceException e) {
            e.printStackTrace();
            return false;
        }


        return result.get();
    }

    @Override
    public boolean delete(long userId, long addressId) {
        try {
        hibernateSessionFactory.createSession(s -> {
            s.delete(getAddress(s, userId, addressId));
        });
        } catch(RollbackException e) {
            return false;
        }
        return true;
    }

    @Override
    public IAddressWithId create(long userId, IAddress address) {
        AtomicReference<Address> result = new AtomicReference<>();

        hibernateSessionFactory.createSession(s -> {
            Address a = new Address();
            a.setStreet(address.getStreet());
            a.setCity(address.getCity());
            a.setPostCode(address.getPostCode());
            a.setUser(new User(userId));
            s.save(a);
            result.set(a);
        });

        return result.get();
    }

    private Address getAddress(Session s, long userId, long addressId) {
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Address> cq = cb.createQuery(Address.class);
        Root<Address> root = cq.from(Address.class);

        Predicate predicate = cb.and(
                cb.equal(root.get("user"), new User(userId)),
                cb.equal(root.get("id"), addressId + "")
        );
        cq.where(predicate);

        return s.createQuery(cq).getSingleResult();
    }
}
