package cz.kodytek.shop.domain.models.users;

import cz.kodytek.shop.data.entities.Right;
import cz.kodytek.shop.data.entities.interfaces.user.IUserWithRights;
import cz.kodytek.shop.domain.models.interfaces.users.IPassword;
import cz.kodytek.shop.domain.models.interfaces.users.IRegisteredUser;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Named
@RequestScoped
public class NewUser implements IUserWithRights, IRegisteredUser {

    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Email is not valid.")
    private String email;

    @NotNull(message = "Name cannot be empty.")
    @Size(min = 3, message = "Name must contain at least 3 characters.")
    private String name;

    @Size(min = 6, message = "Password should contain at least 6 characters.")
    private String password;

    private String passwordConfirmation;

    private String phoneNumber;

    private List<Right> rights;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRights(List<Right> rights) {
        this.rights = rights;
    }

    @Override
    public List<Right> getRights() {
        return rights;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isAdmin() {
        return rights.contains(Right.ADMIN);
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
