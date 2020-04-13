package cz.kodytek.shop.domain.services.interfaces.users;

import cz.kodytek.shop.data.entities.Right;
import cz.kodytek.shop.data.entities.interfaces.user.IFullUser;
import cz.kodytek.shop.data.entities.interfaces.user.IUserWithPhoneNumber;
import cz.kodytek.shop.data.entities.interfaces.user.IUserWithRights;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;
import cz.kodytek.shop.domain.models.interfaces.users.IPassword;
import cz.kodytek.shop.domain.models.users.NewUser;

import java.util.List;

public interface IUserService {

    boolean editUser(long userId, IUserWithPhoneNumber user);

    boolean editUser(long userId, IFullUser user);

    void deleteUser(long userId);

    void addRights(long userId, Right... rights);

    boolean changePassword(long userId, IPassword password);

    IFullUser getUser(long userId);

    IEntityPage<? extends IUserWithRights> getUsers(String searchFilter, int page, int perPage, long currentUserId);

    long getUserCount(String searchFilter, long currentUserId);

    List<Right> getRights();

    boolean createUser(NewUser user);

}
