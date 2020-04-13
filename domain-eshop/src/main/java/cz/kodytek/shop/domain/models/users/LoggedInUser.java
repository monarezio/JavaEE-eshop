package cz.kodytek.shop.domain.models.users;

import cz.kodytek.shop.domain.models.interfaces.users.ILoggedInUser;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Named
@RequestScoped
public class LoggedInUser implements ILoggedInUser {

    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Email is not valid.")
    private String identityIdentifier;

    @Size(min = 6, message = "Password should contain at least 6 characters.")
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
