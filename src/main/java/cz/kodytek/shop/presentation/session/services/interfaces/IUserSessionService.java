package cz.kodytek.shop.presentation.session.services.interfaces;

import cz.kodytek.shop.presentation.session.models.interfaces.ICurrentUser;

public interface IUserSessionService {

    void login(ICurrentUser user);

    void logout();

    ICurrentUser getCurrentUser();

    boolean isLoggedIn();

}
