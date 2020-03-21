package cz.kodytek.shop.data.factories;

import cz.kodytek.shop.data.entities.Right;
import cz.kodytek.shop.data.entities.User;
import cz.kodytek.shop.data.entities.interfaces.user.IUser;
import cz.kodytek.shop.data.factories.interfaces.IUserFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class UserFactory implements IUserFactory {
    @Override
    public User createUser(IUser user, String hashedPassword, List<Right> rights) {
        return new User(user.getEmail(), user.getName(), hashedPassword, rights);
    }

    @Override
    public User createClient(IUser user, String hashedPassword) {
        return createUser(user, hashedPassword, Collections.singletonList(Right.CLIENT));
    }

    @Override
    public User createAdmin(IUser user, String hashedPassword) {
        return createUser(user, hashedPassword, Collections.singletonList(Right.ADMIN));
    }

    @Override
    public User createSuperAdmin(IUser user, String hashedPassword) {
        return createUser(user, hashedPassword, Arrays.asList(Right.values()));
    }
}
