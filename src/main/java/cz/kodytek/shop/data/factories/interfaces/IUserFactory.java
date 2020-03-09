package cz.kodytek.shop.data.factories.interfaces;

import cz.kodytek.shop.data.entities.User;
import cz.kodytek.shop.data.entities.interfaces.IUser;

public interface IUserFactory {

    User createUser(IUser user, String hashedPassword);

}
