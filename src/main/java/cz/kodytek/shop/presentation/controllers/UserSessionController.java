package cz.kodytek.shop.presentation.controllers;

import cz.kodytek.shop.domain.models.interfaces.users.ILoggedInUser;
import cz.kodytek.shop.domain.models.interfaces.users.IRegisteredUser;
import cz.kodytek.shop.domain.services.interfaces.users.IUserAuthenticationService;
import cz.kodytek.shop.presentation.session.models.FlashMessage;
import cz.kodytek.shop.presentation.session.models.FlashMessageType;
import cz.kodytek.shop.presentation.session.services.FlashMessagesService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class UserSessionController {

    @Inject()
    private IUserAuthenticationService userAuthenticationService;

    @Inject()
    private FlashMessagesService flashMessagesService;

    public void login(ILoggedInUser loggedInUser) {
        if (userAuthenticationService.authenticate(loggedInUser)) {
        } else {
            flashMessagesService.add(new FlashMessage("Invalid login combination.", FlashMessageType.alert));
        }
    }

    public void register(IRegisteredUser registeredUser) {
        userAuthenticationService.register(registeredUser);
    }

}
