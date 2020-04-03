package cz.kodytek.shop.presentation.session.models;

import cz.kodytek.shop.data.entities.Right;
import cz.kodytek.shop.presentation.session.models.interfaces.ICurrentUser;

import java.util.List;

public class CurrentUser implements ICurrentUser {

    private long id;
    private String email;
    private String name;
    private List<Right> rights;
    private String phoneNumber;

    public CurrentUser(String email, String name, List<Right> rights) {
        this.email = email;
        this.name = name;
        this.rights = rights;
    }

    @Override
    public long getId() {
        return id;
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
    public List<Right> getRights() {
        return rights;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean isAdmin() {
        return rights.contains(Right.ADMIN);
    }
}
