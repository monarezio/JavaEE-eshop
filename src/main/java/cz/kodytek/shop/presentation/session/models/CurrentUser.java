package cz.kodytek.shop.presentation.session.models;

import cz.kodytek.shop.presentation.session.models.interfaces.ICurrentUser;

public class CurrentUser implements ICurrentUser {

    private long id;
    private String email;
    private String name;

    public CurrentUser(String email, String name) {
        this.email = email;
        this.name = name;
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
}
