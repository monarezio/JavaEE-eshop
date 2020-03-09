package cz.kodytek.shop.domain.models.users;

import cz.kodytek.shop.domain.models.interfaces.users.ILoggedInUser;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class LoggedInUser implements ILoggedInUser {

    private String identityIdentifier;
    private String password;

    public LoggedInUser() {
    }

    public LoggedInUser(String identityIdentifier, String password) {
        this.identityIdentifier = identityIdentifier;
        this.password = password;
    }

    @Override
    public String getIdentityIdentifier() {
        return identityIdentifier;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setIdentityIdentifier(String identityIdentifier) {
        this.identityIdentifier = identityIdentifier;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
