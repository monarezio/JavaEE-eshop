package cz.kodytek.shop.domain.services.interfaces.users;

/**
 * Manages password hashing and password authentication
 */
public interface IPasswordService {

    String hash(String userPassword);

    boolean verify(String passwordToMatch, String hashedPassword);

}
