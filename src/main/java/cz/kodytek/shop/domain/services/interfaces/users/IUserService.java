package cz.kodytek.shop.domain.services.interfaces.users;

import cz.kodytek.shop.data.entities.interfaces.user.IFullUser;
import cz.kodytek.shop.data.entities.interfaces.user.IUser;
import cz.kodytek.shop.data.entities.interfaces.user.IUserWithPhoneNumber;
import cz.kodytek.shop.domain.models.interfaces.users.IPassword;

public interface IUserService {

    boolean editUser(long userId, IUserWithPhoneNumber user);

    boolean changePassword(long userId, IPassword password);

    IFullUser getUser(long userId);

}
