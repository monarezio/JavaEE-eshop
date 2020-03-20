package cz.kodytek.shop.presentation.controllers;

import cz.kodytek.shop.domain.models.interfaces.users.ILoggedInUser;
import cz.kodytek.shop.domain.models.interfaces.users.IRegisteredUser;
import cz.kodytek.shop.domain.services.interfaces.users.IUserAuthenticationService;
import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;
import cz.kodytek.shop.presentation.session.models.FlashMessage;
import cz.kodytek.shop.presentation.session.models.FlashMessageType;
import cz.kodytek.shop.presentation.session.services.interfaces.IUserSessionService;
import cz.kodytek.shop.presentation.session.services.interfaces.messages.IFlashMessagesService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class UserSessionController {

    @Inject()
    private IUserAuthenticationService userAuthenticationService;

    @Inject()
    private IUserSessionService userSessionService;

    @Inject()
    private IFlashMessagesService flashMessagesService;

    @Inject()
    private IRequestUtils requestUtils;

    public void login(ILoggedInUser loggedInUser) {
        userSessionService.login(loggedInUser);

        if (userSessionService.getCurrentUser() != null) {
            flashMessagesService.add(new FlashMessage("Successfully logged in.", FlashMessageType.success));
            requestUtils.redirect("//");
        } else {
            flashMessagesService.add(new FlashMessage("Invalid login combination.", FlashMessageType.alert));
        }
    }

    public void register(IRegisteredUser registeredUser) {
        if (!registeredUser.getPassword().equals(registeredUser.getPasswordConfirmation()))
            flashMessagesService.add(new FlashMessage("Password do not match", FlashMessageType.alert));
        else if (userAuthenticationService.register(registeredUser))
            requestUtils.redirect("//", new FlashMessage("Successfully registered.", FlashMessageType.success));
        else
            flashMessagesService.add(new FlashMessage("Email is already in use.", FlashMessageType.alert));
    }

}
