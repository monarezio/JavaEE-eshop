package cz.kodytek.shop.domain.models.users;

import cz.kodytek.shop.domain.models.interfaces.users.IPassword;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Named
@RequestScoped
public class Password implements IPassword {

    @NotNull
    @Size(min = 6, message = "Old password is invalid.")
    private String oldPassword;

    @NotNull
    @Size(min = 6, message = "Password should contain at least 6 characters.")
    private String password;

    private String passwordConfirmation;

    @Override
    public String getOldPassword() {
        return oldPassword;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
