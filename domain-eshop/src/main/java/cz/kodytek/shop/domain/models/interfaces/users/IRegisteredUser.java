package cz.kodytek.shop.domain.models.interfaces.users;

import cz.kodytek.shop.data.entities.interfaces.user.IUser;

public interface IRegisteredUser extends IUser {

    String getPassword();

    String getPasswordConfirmation();

}
