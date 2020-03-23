package cz.kodytek.shop.domain.models.company;

import cz.kodytek.shop.data.entities.Address;
import cz.kodytek.shop.data.entities.interfaces.IAddress;
import cz.kodytek.shop.data.entities.interfaces.company.ICompany;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class Company implements ICompany {

    private String name;
    private int identificationNumber;
    private String taxIdentificationNumber;
    private Address address;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setIdentificationNumber(int identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
