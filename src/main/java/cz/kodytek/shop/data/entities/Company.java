package cz.kodytek.shop.data.entities;

import cz.kodytek.shop.data.entities.interfaces.IAddress;
import cz.kodytek.shop.data.entities.interfaces.ICompany;

import javax.persistence.*;

@Entity
public class Company implements ICompany {

    @Id
    @Column(nullable = false)
    //Is not unique since, it would be kind of stupid not allowing the user to not add his company, if someone miss typed the id
    private long identificationNumber;

    @Column(nullable = false)
    private String name;

    @Column
    private String taxIdentificationNumber;

    @ManyToOne
    private User user;

    @OneToOne
    private Address address;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public long getIdentificationNumber() {
        return 0;
    }

    @Override
    public String getTaxIdentificationNumber() {
        return null;
    }

    @Override
    public IAddress getAddress() {
        return null;
    }
}
