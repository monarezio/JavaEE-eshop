package cz.kodytek.shop.presentation.controllers;

import cz.kodytek.shop.data.entities.interfaces.user.IFullUser;
import cz.kodytek.shop.data.entities.interfaces.user.IUserWithPhoneNumber;
import cz.kodytek.shop.domain.models.interfaces.users.IPassword;
import cz.kodytek.shop.domain.services.interfaces.users.IUserService;
import cz.kodytek.shop.presentation.session.models.FlashMessage;
import cz.kodytek.shop.presentation.session.models.FlashMessageType;
import cz.kodytek.shop.presentation.session.services.interfaces.IUserSessionService;
import cz.kodytek.shop.presentation.session.services.interfaces.messages.IFlashMessagesService;
import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class UserController {

    @Inject
    IUserService userService;

    @Inject
    IUserSessionService userSessionService;

    @Inject
    IFlashMessagesService flashMessagesService;

    @Inject
    IRequestUtils requestUtils;

    private IFullUser currentUser;

    public void edit(IUserWithPhoneNumber editedUser) {
        if (userService.editUser(userSessionService.getCurrentUser().getId(), editedUser))
            requestUtils.redirect("/pages/user/account.xhtml", new FlashMessage("Account information edited.", FlashMessageType.success));
        else
            flashMessagesService.add(new FlashMessage("Email is already in use.", FlashMessageType.alert));
    }

    public void changePassword(IPassword password) {
        if (!password.getPassword().equals(password.getPasswordConfirmation()))
            flashMessagesService.add(new FlashMessage("Password do not match", FlashMessageType.alert));
        else if (userService.changePassword(userSessionService.getCurrentUser().getId(), password))
            requestUtils.redirect("/pages/user/account.xhtml", new FlashMessage("Password changed.", FlashMessageType.success));
        else
            flashMessagesService.add(new FlashMessage("Old Password is invalid.", FlashMessageType.alert));
    }

    public IFullUser getCurrentUser() {
        if(currentUser == null)
            currentUser = userService.getUser(userSessionService.getCurrentUser().getId());

        return currentUser;
    }

}
