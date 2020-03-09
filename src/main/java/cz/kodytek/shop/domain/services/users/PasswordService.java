package cz.kodytek.shop.domain.services.users;

import cz.kodytek.shop.domain.services.interfaces.users.IPasswordService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PasswordService implements IPasswordService {

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String hash(String userPassword) {
        return encoder.encode(userPassword);
    }

    @Override
    public boolean verify(String passwordToMatch, String hashedPassword) {
        return encoder.matches(passwordToMatch, hashedPassword);
    }
}
