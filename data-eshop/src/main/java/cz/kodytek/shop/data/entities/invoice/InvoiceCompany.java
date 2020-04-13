package cz.kodytek.shop.data.entities.invoice;

import cz.kodytek.shop.data.entities.interfaces.address.IAddressWithId;
import cz.kodytek.shop.data.entities.interfaces.invoice.IInvoiceCompany;

import javax.persistence.*;

@Entity
public class InvoiceCompany implements IInvoiceCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer identificationNumber;

    @Column
    private String taxIdentificationNumber;

    @OneToOne
    @JoinColumn
    private InvoiceAddress address;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getIdentificationNumber() {
        return identificationNumber;
    }

    @Override
    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    @Override
    public IAddressWithId getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdentificationNumber(Integer identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public void setAddress(InvoiceAddress address) {
        this.address = address;
    }
}
