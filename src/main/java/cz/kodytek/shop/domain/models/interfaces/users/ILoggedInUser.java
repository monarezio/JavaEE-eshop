package cz.kodytek.shop.domain.models.interfaces.users;

public interface ILoggedInUser {
    
    /**
     * Is email for now, but the in the future it might be even username, therefore this name
     * @return account identifier (currently only email)
     */
    String getIdentityIdentifier();

    String getPassword();

}
