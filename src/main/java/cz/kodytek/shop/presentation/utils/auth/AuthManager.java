package cz.kodytek.shop.presentation.utils.auth;

import cz.kodytek.shop.presentation.session.models.FlashMessage;
import cz.kodytek.shop.presentation.session.models.FlashMessageType;
import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class AuthManager {

    @Inject
    private IRequestUtils requestUtils;

    private AuthConfig config = AuthConfig.getSharedInstance()
            .setDefaultPage("/pages/user/session/login.xhtml")
            .restrict("/pages/user/account.xhtml");

    public void authorize() {
        String accessingUrl = requestUtils.getCurrentJSFPageId();
        if (config.getResources().stream().anyMatch(i -> i.getResource().equals(accessingUrl))) // TODO: Add rights checking
            requestUtils.redirect(config.getDefaultPage(), new FlashMessage("Page is only for logged in users, please login.", FlashMessageType.alert));

        return;
    }

}
