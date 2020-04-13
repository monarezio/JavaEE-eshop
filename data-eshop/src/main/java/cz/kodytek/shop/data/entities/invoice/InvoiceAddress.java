package cz.kodytek.shop.data.entities.invoice;

import cz.kodytek.shop.data.entities.interfaces.invoice.IInvoiceAddress;

import javax.persistence.*;

@Entity
public class InvoiceAddress implements IInvoiceAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private long id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String postCode;

    @Override
    public long getId() {
        return id;
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

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
