package cz.kodytek.shop.presentation.session.services;

import cz.kodytek.shop.data.entities.interfaces.IUserWithRights;
import cz.kodytek.shop.domain.models.interfaces.users.ILoggedInUser;
import cz.kodytek.shop.domain.services.interfaces.users.IUserAuthenticationService;
import cz.kodytek.shop.presentation.session.services.interfaces.IUserSessionService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;

@SessionScoped
public class UserSessionService implements IUserSessionService, Serializable {

    private IUserWithRights currentUser = null;

    @Inject
    private IUserAuthenticationService userAuthenticationService;

    @Override
    public void login(ILoggedInUser user) {
        currentUser = userAuthenticationService.authenticate(user);
    }

    @Override
    public void logout() {
        currentUser = null;
    }

    @Override
    public IUserWithRights getCurrentUser() {
        return currentUser;
    }

    @Override
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
