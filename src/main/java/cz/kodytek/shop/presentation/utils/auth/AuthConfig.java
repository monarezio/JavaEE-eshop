package cz.kodytek.shop.presentation.utils.auth;

import java.util.ArrayList;
import java.util.List;

public class AuthConfig {

    private static AuthConfig instance = new AuthConfig();

    private List<AuthResource> resources = new ArrayList<>();
    /**
     * Where will the user be redirected to, if he is'nt authenticated
     */
    private String defaultPage = "/";

    private AuthConfig() {
    }

    public static AuthConfig getSharedInstance() {
        return instance;
    }

    public AuthConfig restrict(String resource) {
        resources.add(
                AuthResource.create(resource).restrict()
        );
        return this;
    }

    public AuthConfig restrictLoggedInUsers(String resource) {
        resources.add(
                AuthResource.create(resource).restrictLoggedUsers()
        );
        return this;
    }

    public AuthConfig setDefaultPage(String defaultPage) {
        this.defaultPage = defaultPage;
        return this;
    }

    public List<AuthResource> getResources() {
        return resources;
    }

    public String getDefaultPage() {
        return defaultPage;
    }
}
