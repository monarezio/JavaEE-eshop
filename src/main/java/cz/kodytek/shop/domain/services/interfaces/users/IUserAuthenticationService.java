package cz.kodytek.shop.domain.services.interfaces.users;

import cz.kodytek.shop.data.entities.interfaces.IUserWithRights;
import cz.kodytek.shop.domain.models.interfaces.users.ILoggedInUser;
import cz.kodytek.shop.domain.models.interfaces.users.IRegisteredUser;

public interface IUserAuthenticationService {

    IUserWithRights authenticate(ILoggedInUser loggedInUser);

    boolean register(IRegisteredUser registeredUser);

}
