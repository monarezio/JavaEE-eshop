package cz.kodytek.shop.presentation.utils.auth;

import cz.kodytek.shop.presentation.session.models.FlashMessage;
import cz.kodytek.shop.presentation.session.models.FlashMessageType;
import cz.kodytek.shop.presentation.session.services.interfaces.IUserSessionService;
import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.stream.Stream;

@Named
@ApplicationScoped
public class AuthManager {

    @Inject
    private IRequestUtils requestUtils;

    @Inject
    private IUserSessionService userSessionService;

    private AuthConfig config = AuthConfig.getSharedInstance()
            .setDefaultPage("/pages/user/session/login.xhtml")
            .restrict("/pages/user/account.xhtml")
            .restrict("/pages/user/edit.xhtml")
            .restrictLoggedInUsers("/pages/user/session/login.xhtml")
            .restrictLoggedInUsers("/pages/user/session/register.xhtml");

    public void authorize() {
        String accessingUrl = requestUtils.getCurrentJSFPageId();
        Stream<AuthResource> resources = config.getResources().stream();

        if (!userSessionService.isLoggedIn() && resources.filter(AuthResource::isRestriced).anyMatch(i -> i.getResource().equals(accessingUrl)))
            requestUtils.redirect(config.getDefaultPage(), new FlashMessage("Page is only for logged in users, please login.", FlashMessageType.alert));
        else if (userSessionService.isLoggedIn() && resources.filter(AuthResource::isRestrictedForLoggedUsers).anyMatch(i -> i.getResource().equals(accessingUrl)))
            requestUtils.redirect("");
    }

}
