package cz.kodytek.shop.domain.models.users;

import cz.kodytek.shop.domain.models.interfaces.users.IRegisteredUser;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class RegisteredUser implements IRegisteredUser {

    private String password;
    private String email;
    private String name;

    public RegisteredUser() {
    }

    public RegisteredUser(String password, String email, String name) {
        this.password = password;
        this.email = email;
        this.name = name;
    }

    @Override
    public String getPassword() {
        return password;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}
