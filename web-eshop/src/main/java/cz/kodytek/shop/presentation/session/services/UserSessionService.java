package cz.kodytek.shop.presentation.session.services;

import cz.kodytek.shop.data.entities.Right;
import cz.kodytek.shop.data.entities.interfaces.user.IUserWithRights;
import cz.kodytek.shop.domain.models.interfaces.users.ILoggedInUser;
import cz.kodytek.shop.domain.services.interfaces.users.IUserAuthenticationService;
import cz.kodytek.shop.jms.JMSService;
import cz.kodytek.shop.presentation.session.services.interfaces.IUserSessionService;
import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;

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

    @Inject
    private IRequestUtils requestUtils;

    @Inject
    private JMSService jmsService;

    @Override
    public void login(ILoggedInUser user) {
        jmsService.sendMessage(requestUtils.getIp() + " attempting to login, with email " + user.getIdentityIdentifier() + ".");
        currentUser = userAuthenticationService.authenticate(user);
    }

    @Override
    public void logout() {
        jmsService.sendMessage(requestUtils.getIp() + " logging out.");
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
