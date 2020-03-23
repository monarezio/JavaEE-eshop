package cz.kodytek.shop.data.entities;

import cz.kodytek.shop.data.entities.interfaces.IAddress;
import cz.kodytek.shop.data.entities.interfaces.company.ICompanyWithId;

import javax.persistence.*;

@Entity
public class Company implements ICompanyWithId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    //Is not unique since, it would be kind of stupid not allowing the user to not add his company, if someone miss typed the id
    private int identificationNumber;

    @Column(nullable = false)
    private String name;

    @Column
    private String taxIdentificationNumber;

    @ManyToOne
    private User user;

    @OneToOne
    private Address address;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getIdentificationNumber() {
        return identificationNumber;
    }

    @Override
    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    @Override
    public IAddress getAddress() {
        return address;
    }

    public void setIdentificationNumber(int identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public void setAddress(IAddress address) {
        this.address = (Address) address;
    }
}
