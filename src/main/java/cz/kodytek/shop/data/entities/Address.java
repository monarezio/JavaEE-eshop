package cz.kodytek.shop.data.entities;

import cz.kodytek.shop.data.entities.interfaces.IAddress;
import cz.kodytek.shop.data.entities.interfaces.IEntityId;

import javax.persistence.*;

@Entity
public class Address implements IAddress {

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

    @Override
    public String getStreet() {
        return null;
    }

    @Override
    public String getCity() {
        return null;
    }

    @Override
    public String getPostCode() {
        return null;
    }

    @Override
    public long getId() {
        return 0;
    }
}
