package cz.kodytek.shop.data.entities;

import cz.kodytek.shop.data.entities.interfaces.address.IAddressWithId;

import javax.persistence.*;

@Entity
public class Address implements IAddressWithId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String postCode;

    @ManyToOne
    private User user;

    public Address() {
    }

    public Address(long id) {
        this.id = id;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getPostCode() {
        return postCode;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(long id) {
        this.id = id;
    }
}
