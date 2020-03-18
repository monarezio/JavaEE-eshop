package cz.kodytek.shop.domain.services.interfaces.users;

import cz.kodytek.shop.data.entities.interfaces.IUser;
import cz.kodytek.shop.domain.models.interfaces.users.ILoggedInUser;
import cz.kodytek.shop.domain.models.interfaces.users.IRegisteredUser;

public interface IUserAuthenticationService {

    boolean authenticate(ILoggedInUser loggedInUser);

    void register(IRegisteredUser registeredUser);

}
