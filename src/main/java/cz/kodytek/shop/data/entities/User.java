package cz.kodytek.shop.data.entities;

import cz.kodytek.shop.data.entities.interfaces.IEntityId;
import cz.kodytek.shop.data.entities.interfaces.IUser;

import javax.enterprise.context.RequestScoped;
import javax.persistence.*;
import java.time.LocalDateTime;

@RequestScoped
@Entity
public class User implements IEntityId, IUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String hashedPassword;

    @Column
    private LocalDateTime lastLogin;

    public User() {
    }

    public User(String email, String name, String hashedPassword) {
        this.email = email;
        this.name = name;
        this.hashedPassword = hashedPassword;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}
