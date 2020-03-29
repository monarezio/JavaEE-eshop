package cz.kodytek.shop.domain.services.users;

import cz.kodytek.shop.data.connection.interfaces.IHibernateSessionFactory;
import cz.kodytek.shop.data.entities.*;
import cz.kodytek.shop.data.entities.Company_;
import cz.kodytek.shop.data.entities.User_;
import cz.kodytek.shop.data.entities.interfaces.user.IFullUser;
import cz.kodytek.shop.data.entities.interfaces.user.IUser;
import cz.kodytek.shop.data.entities.interfaces.user.IUserWithPhoneNumber;
import cz.kodytek.shop.domain.models.interfaces.users.IPassword;
import cz.kodytek.shop.domain.services.interfaces.users.IPasswordService;
import cz.kodytek.shop.domain.services.interfaces.users.IUserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Named
@ApplicationScoped
public class UserService implements IUserService {

    @Inject
    IHibernateSessionFactory sessionFactory;

    @Inject
    private IPasswordService passwordService;

    @Override
    public boolean editUser(long userId, IUserWithPhoneNumber user) {

        try {
            sessionFactory.createSession(s -> {
                User u = s.get(User.class, userId);
                u.setEmail(user.getEmail());
                u.setName(user.getName());
                u.setPhoneNumber(user.getPhoneNumber());
                s.save(u);
            });
        } catch (PersistenceException e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean changePassword(long userId, IPassword password) {
        AtomicBoolean result = new AtomicBoolean(false);

        try {
            sessionFactory.createSession(s -> {
                User u = s.get(User.class, userId);

                if (passwordService.verify(password.getOldPassword(), u.getHashedPassword())) {
                    String newHashedPassword = passwordService.hash(password.getPassword());
                    u.setHashedPassword(newHashedPassword);
                    result.set(true);
                }
            });
        } catch (PersistenceException e) {
            return false;
        }

        return result.get();
    }

    @Override
    public IFullUser getUser(long userId) {
        System.out.println(userId);

        AtomicReference<User> result = new AtomicReference<>();

        try {
            sessionFactory.createSession(s -> {
                CriteriaBuilder cb = s.getCriteriaBuilder();
                CriteriaQuery<User> cq = cb.createQuery(User.class);
                Root<User> root = cq.from(User.class);

                Fetch<User, Company> userToCompanyJoin = root.fetch(User_.companies, JoinType.LEFT);
                Fetch<User, Address> userToAddressJoin = root.fetch(User_.addresses, JoinType.LEFT);

                cq.where(
                        cb.equal(root.get("id"), userId)
                );

                result.set(s.createQuery(cq).getSingleResult());
            });
        } catch (PersistenceException e) {
            e.printStackTrace();
            return null;
        }

        System.out.println(result.get().getId());

        return result.get();
    }
}
