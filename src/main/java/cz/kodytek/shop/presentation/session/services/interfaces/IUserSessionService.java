package cz.kodytek.shop.presentation.session.services.interfaces;

import cz.kodytek.shop.data.entities.interfaces.IUserWithRights;
import cz.kodytek.shop.domain.models.interfaces.users.ILoggedInUser;

public interface IUserSessionService {

    void login(ILoggedInUser user);

    void logout();

    IUserWithRights getCurrentUser();

    boolean isLoggedIn();

}
