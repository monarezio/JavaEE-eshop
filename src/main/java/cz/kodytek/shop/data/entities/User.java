package cz.kodytek.shop.data.entities;

import cz.kodytek.shop.data.entities.interfaces.IAddress;
import cz.kodytek.shop.data.entities.interfaces.IEntityWithAddresses;
import cz.kodytek.shop.data.entities.interfaces.user.IUserWithRights;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class User implements IUserWithRights, IEntityWithAddresses {

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

    @OneToMany()
    @JoinColumn(name = "user_id")
    private List<Address> addresses;

    @OneToMany()
    @JoinColumn(name = "user_id")
    private List<Company> companies;

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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public List<? extends IAddress> getAddresses() {
        return null;
    }
}
