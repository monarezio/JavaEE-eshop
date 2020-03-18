package cz.kodytek.shop.domain.services.users;

import cz.kodytek.shop.data.connection.interfaces.IHibernateSessionFactory;
import cz.kodytek.shop.data.entities.User;
import cz.kodytek.shop.data.factories.interfaces.IUserFactory;
import cz.kodytek.shop.domain.models.interfaces.users.ILoggedInUser;
import cz.kodytek.shop.domain.models.interfaces.users.IRegisteredUser;
import cz.kodytek.shop.domain.services.interfaces.users.IPasswordService;
import cz.kodytek.shop.domain.services.interfaces.users.IUserAuthenticationService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

@ApplicationScoped
public class UserAuthenticationService implements IUserAuthenticationService {

    @Inject
    private IPasswordService passwordService;

    @Inject
    private IUserFactory userFactory;

    @Inject()
    private IHibernateSessionFactory sessionFactory;

    @Override
    public boolean authenticate(ILoggedInUser loggedInUser) {
        AtomicBoolean authenticationResult = new AtomicBoolean(false);

        try {
            sessionFactory.createSession(s -> {
                CriteriaBuilder cb = s.getCriteriaBuilder();
                CriteriaQuery<User> cq = cb.createQuery(User.class);
                Root<User> root = cq.from(User.class);

                cq = cq.where(cb.equal(root.get("email"), loggedInUser.getIdentityIdentifier()));

                User user = s.createQuery(cq).getSingleResult();

                if (passwordService.verify(loggedInUser.getPassword(), user.getHashedPassword())) {
                    user.setLastLogin(LocalDateTime.now());
                    authenticationResult.set(true);
                }
            });
        } catch(PersistenceException e) {
            return false; //Most probably did not find the user
        }

        return authenticationResult.get();
    }

    @Override
    public void register(IRegisteredUser registeredUser) {
        sessionFactory.createSession(s -> {
            final String hashedPassword = passwordService.hash(registeredUser.getPassword());
            s.save(userFactory.createUser(registeredUser, hashedPassword));
        });
    }

}
