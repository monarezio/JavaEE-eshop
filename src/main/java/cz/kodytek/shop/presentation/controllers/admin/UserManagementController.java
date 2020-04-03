package cz.kodytek.shop.presentation.controllers.admin;

import cz.kodytek.shop.data.entities.Right;
import cz.kodytek.shop.data.entities.interfaces.user.IUserWithRights;
import cz.kodytek.shop.domain.models.EntityFilter;
import cz.kodytek.shop.domain.models.interfaces.IEntityFilter;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;
import cz.kodytek.shop.domain.models.users.NewUser;
import cz.kodytek.shop.domain.services.interfaces.users.IUserService;
import cz.kodytek.shop.presentation.session.models.FlashMessage;
import cz.kodytek.shop.presentation.session.models.FlashMessageType;
import cz.kodytek.shop.presentation.session.services.interfaces.IUserSessionService;
import cz.kodytek.shop.presentation.session.services.interfaces.messages.IFlashMessagesService;
import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class UserManagementController {

    @Inject
    private IUserService userService;

    @Inject
    private IUserSessionService userSessionService;

    @Inject
    private IFlashMessagesService flashMessagesService;

    @Inject
    private IRequestUtils requestUtils;

    private IEntityPage<? extends IUserWithRights> cachedPage;

    private IEntityFilter cachedFilter;

    public IEntityPage<? extends IUserWithRights> getUsers(String search, String page) {
        if (cachedPage == null)
            cachedPage = userService.getUsers(search, Integer.parseInt(page), 20, userSessionService.getCurrentUser().getId());
        return cachedPage;
    }

    public List<Right> getRightsList() {
        return userService.getRights();
    }

    public void createUser(NewUser user) {
        if (user.getPassword().equals(user.getPasswordConfirmation())) {
            if (userService.createUser(user))
                requestUtils.redirect("/pages/admin/users/index.xhtml", new FlashMessage("User added successfully.", FlashMessageType.success));
            else
                flashMessagesService.add(new FlashMessage("Email already in use.", FlashMessageType.alert));
        } else
            flashMessagesService.add(new FlashMessage("Password do not match", FlashMessageType.alert));
    }

    public void search(IEntityFilter filter) {
        System.out.println("Search filter: " + filter.getSearchFilter());
        requestUtils.redirect("/pages/admin/users/index.xhtml?search=" + filter.getSearchFilter());
    }

    public IEntityFilter getFilter() {
        if(cachedFilter == null)
            cachedFilter = new EntityFilter(requestUtils.getParam("search"));
        return cachedFilter;
    }
}
