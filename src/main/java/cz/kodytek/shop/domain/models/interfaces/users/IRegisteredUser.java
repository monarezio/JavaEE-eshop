package cz.kodytek.shop.domain.models.interfaces.users;

import cz.kodytek.shop.data.entities.interfaces.IUser;

public interface IRegisteredUser extends IUser {

    String getPassword();

}
