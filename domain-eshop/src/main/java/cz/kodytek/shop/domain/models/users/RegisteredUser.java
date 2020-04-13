package cz.kodytek.shop.domain.models.users;

import cz.kodytek.shop.domain.models.interfaces.users.IRegisteredUser;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Named
@RequestScoped
public class RegisteredUser implements IRegisteredUser {

    @Size(min = 6, message = "Password should contain at least 6 characters.")
    private String password;

    private String passwordConfirmation;

    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Email is not valid.")
    private String email;

    @NotNull(message = "Name cannot be empty.")
    @Size(min = 3, message = "Name must contain at least 3 characters.")
    private String name;

    public RegisteredUser() {
    }

    public RegisteredUser(String email, String name, String password) {
        this.password = password;
        this.passwordConfirmation = password;
        this.email = email;
        this.name = name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}
