package cz.kodytek.shop.data.entities;

import cz.kodytek.shop.data.entities.interfaces.IUserWithRights;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class User implements IUserWithRights {

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

    @Column
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Right.class)
    private List<Right> rights;

    public User() {
    }

    public User(String email, String name, String hashedPassword, List<Right> rights) {
        this.email = email;
        this.name = name;
        this.hashedPassword = hashedPassword;
        this.rights = rights;
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

    public List<Right> getRights() {
        return rights;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}
