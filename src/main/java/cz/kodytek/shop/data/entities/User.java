package cz.kodytek.shop.data.entities;

import cz.kodytek.shop.data.entities.interfaces.user.IFullUser;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class User implements IFullUser {

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
    private String phoneNumber;

    @Column
    private LocalDateTime lastLogin;

    @Column
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Right.class, fetch = FetchType.EAGER)
    private Set<Right> rights;

    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<Address> addresses;

    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<Company> companies;

    public User() {
    }

    public User(long id) {
        this.id = id;
    }

    public User(String email, String name, String hashedPassword, Set<Right> rights) {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public List<Right> getRights() {
        return new ArrayList<>(rights);
    }

    public void addRight(Right right) {
        this.rights.add(right);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public List<Address> getAddresses() {
        return addresses.stream().sorted(Comparator.comparingLong(Address::getId)).collect(Collectors.toList());
    }

    @Override
    public List<Company> getCompanies() {
        return companies.stream().sorted(Comparator.comparingLong(Company::getId)).collect(Collectors.toList());
    }

    public void addAddress(Address address) {
        addresses.add(address);
    }

    public void addCompany(Company company) {
        companies.add(company);
    }
}
