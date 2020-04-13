package cz.kodytek.shop.presentation.session.services;

import cz.kodytek.shop.data.entities.Right;
import cz.kodytek.shop.data.entities.interfaces.user.IUserWithRights;
import cz.kodytek.shop.domain.models.interfaces.users.ILoggedInUser;
import cz.kodytek.shop.domain.services.interfaces.users.IUserAuthenticationService;
import cz.kodytek.shop.presentation.session.services.interfaces.IUserSessionService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
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

    @Override
    public boolean isAdmin() {
        return isLoggedIn() && currentUser.getRights().contains(Right.ADMIN);
    }
}
