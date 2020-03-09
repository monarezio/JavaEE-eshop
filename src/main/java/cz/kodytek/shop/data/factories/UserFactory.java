package cz.kodytek.shop.data.factories;

import cz.kodytek.shop.data.entities.User;
import cz.kodytek.shop.data.entities.interfaces.IUser;
import cz.kodytek.shop.data.factories.interfaces.IUserFactory;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserFactory implements IUserFactory {
    @Override
    public User createUser(IUser user, String hashedPassword) {
        return new User(user.getName(), user.getEmail(), hashedPassword);
    }
}
