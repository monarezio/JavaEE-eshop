package cz.kodytek.shop.data.factories.interfaces;

import cz.kodytek.shop.data.entities.Right;
import cz.kodytek.shop.data.entities.User;
import cz.kodytek.shop.data.entities.interfaces.IUser;

import java.util.List;

public interface IUserFactory {

    User createUser(IUser user, String hashedPassword, List<Right> rights);

    User createClient(IUser user, String hashedPassword);

    User createAdmin(IUser user, String hashedPassword);

    User createSuperAdmin(IUser user, String hashedPassword);

}
