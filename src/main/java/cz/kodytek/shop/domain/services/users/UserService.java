package cz.kodytek.shop.domain.services.users;

import cz.kodytek.shop.data.connection.interfaces.IHibernateSessionFactory;
import cz.kodytek.shop.data.entities.*;
import cz.kodytek.shop.data.entities.interfaces.user.IFullUser;
import cz.kodytek.shop.data.entities.interfaces.user.IUserWithPhoneNumber;
import cz.kodytek.shop.data.entities.interfaces.user.IUserWithRights;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;
import cz.kodytek.shop.domain.models.interfaces.users.IPassword;
import cz.kodytek.shop.domain.models.users.NewUser;
import cz.kodytek.shop.domain.models.users.UserPage;
import cz.kodytek.shop.domain.services.interfaces.users.IPasswordService;
import cz.kodytek.shop.domain.services.interfaces.users.IUserService;
import org.hibernate.query.Query;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
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
    public void addRights(long userId, Right... rights) {
        try {
            sessionFactory.createSession(s -> {
                User u = s.get(User.class, userId);
                for (Right r : rights)
                    u.addRight(r);

                s.save(u);
            });
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
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

    @Override
    public IEntityPage<? extends IUserWithRights> getUsers(String searchFilter, int page, int perPage, long currentUserId) {
        AtomicReference<IEntityPage<? extends IUserWithRights>> result = new AtomicReference<>();

        sessionFactory.createSession(s -> {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);

            Fetch<User, Right> userToRightsJoin = root.fetch(User_.rights, JoinType.LEFT);

            cq.where(
                    cb.and(
                            cb.notEqual(root.get(User_.id), currentUserId),
                            cb.like(root.get(User_.name), "%" + searchFilter + "%")
                    )
            );

            Query<User> q = s.createQuery(cq);

            q.setFirstResult(page * perPage);
            q.setMaxResults(perPage);

            result.set(new UserPage(q.getResultList(), page, (int) (Math.ceil(getUserCount(searchFilter, currentUserId) / (float) perPage) + 1)));
        });


        return result.get();
    }

    @Override
    public long getUserCount(String searchFilter, long currentUserId) {
        AtomicLong result = new AtomicLong();

        sessionFactory.createSession(s -> {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<User> root = cq.from(User.class);

            cq.where(
                    cb.and(
                            cb.notEqual(root.get(User_.id), currentUserId),
                            cb.like(root.get(User_.name), "%" + searchFilter + "%")
                    )
            );

            cq.select(cb.count(root));
            result.set(s.createQuery(cq).getSingleResult());
        });

        return result.get();
    }

    @Override
    public List<Right> getRights() {
        return Arrays.asList(Right.values());
    }

    @Override
    public boolean createUser(NewUser user) {
        AtomicBoolean result = new AtomicBoolean(false);

        try {
            sessionFactory.createSession(s -> {
                User u = new User();
                u.setName(user.getName());
                u.setEmail(user.getEmail());
                u.setPhoneNumber(user.getPhoneNumber());
                u.setHashedPassword(passwordService.hash(user.getPassword()));
                u.setRights(new HashSet<>(user.getRights()));

                s.save(u);
                result.set(true);
            });
        } catch (PersistenceException e) {
            return false;
        }

        return result.get();
    }
}
