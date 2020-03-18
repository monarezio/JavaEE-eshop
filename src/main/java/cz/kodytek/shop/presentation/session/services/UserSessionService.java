package cz.kodytek.shop.presentation.session.services;

import cz.kodytek.shop.presentation.session.models.interfaces.ICurrentUser;
import cz.kodytek.shop.presentation.session.services.interfaces.IUserSessionService;

import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

@SessionScoped
public class UserSessionService implements IUserSessionService, Serializable {

    private ICurrentUser currentUser = null;

    @Override
    public void login(ICurrentUser user) {
        currentUser = user;
    }

    @Override
    public void logout() {
        currentUser = null;
    }

    @Override
    public ICurrentUser getCurrentUser() {
        return currentUser;
    }

    @Override
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
