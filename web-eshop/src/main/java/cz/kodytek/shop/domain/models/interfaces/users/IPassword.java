package cz.kodytek.shop.domain.models.interfaces.users;

public interface IPassword {

    String getOldPassword();

    String getPassword();

    String getPasswordConfirmation();

}
