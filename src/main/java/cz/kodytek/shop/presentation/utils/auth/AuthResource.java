package cz.kodytek.shop.presentation.utils.auth;

public class AuthResource {

    /**
     * JSF Page
     */
    private String resource;

    /**
     * Must user be logged in?
     */
    private boolean restrict = false;

    private boolean rescrictLoggedUsers = false;

    private AuthResource(String resource) {
        this.resource = resource;
    }

    public AuthResource restrict() {
        restrict = true;
        return this;
    }

    public AuthResource open() {
        restrict = false;
        return this;
    }

    public AuthResource restrictLoggedUsers() {
        rescrictLoggedUsers = true;
        return this;
    }

    public AuthResource openLoggedUsers() {
        rescrictLoggedUsers = false;
        return this;
    }

    public boolean isRestriced() {
        return restrict;
    }

    public boolean isRestrictedForLoggedUsers() {
        return rescrictLoggedUsers;
    }

    public String getResource() {
        return resource;
    }

    public static AuthResource create(String resource) {
        return new AuthResource(resource);
    }

}
