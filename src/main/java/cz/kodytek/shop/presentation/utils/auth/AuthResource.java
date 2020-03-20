package cz.kodytek.shop.presentation.utils.auth;

public class AuthResource {

    /**
     * JSF Page
     */
    private String resource;

    /**
     * Must user be logged in?
     */
    private boolean isRestricted = false;

    private AuthResource(String resource) {
        this.resource = resource;
    }

    public AuthResource restrict() {
        isRestricted = true;
        return this;
    }

    public AuthResource open() {
        isRestricted = false;
        return this;
    }

    public String getResource() {
        return resource;
    }

    public static AuthResource create(String resource) {
        return new AuthResource(resource);
    }

}
