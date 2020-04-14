package cz.kodytek.shop.domain.api.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class CreatedInvoice extends Invoice implements Serializable {

    private long id;
    private String invoiceNumber;
    private String variableSymbol;
    private LocalDate issued;
    private LocalDate paid;

    public CreatedInvoice() {
    }


    public CreatedInvoice(cz.kodytek.shop.data.entities.invoice.Invoice i) {
        setFullName(i.getFullName());
        setEmail(i.getEmail());
        setPhone(i.getPhone());
        setInvoiceNumber(i.getInvoiceNumber());
        setVariableSymbol(i.getVariableSymbol());
        setIssued(i.getDateIssued());
        setPaid(i.getDatePaid());
        setId(i.getId());
        setPaymentMethodId(i.getPaymentMethod().getId());
        setDeliveryMethodId(i.getDeliverMethod().getId());

        if (i.getCompany() != null) {
            Company c = new Company();
            c.setName(i.getCompany().getName());
            c.setIdentificationNumber(i.getCompany().getIdentificationNumber());
            c.setTaxIdentificationNumber(i.getCompany().getTaxIdentificationNumber());

            Address ca = new Address();
            ca.setCity(i.getCompany().getAddress().getCity());
            ca.setPostCode(i.getCompany().getAddress().getPostCode());
            ca.setStreet(i.getCompany().getAddress().getStreet());
            c.setAddress(ca);
            setCompany(c);
        }

        Address a = new Address();
        a.setCity(i.getDeliveryAddress().getCity());
        a.setStreet(i.getDeliveryAddress().getStreet());
        a.setPostCode(i.getDeliveryAddress().getPostCode());
        setDeliveryAddress(a);

        setCart(i.getGoods().stream().map(j -> new Product(j.getOriginalId(), j.getAmount())).collect(Collectors.toList()));
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getVariableSymbol() {
        return variableSymbol;
    }

    public void setVariableSymbol(String variableSymbol) {
        this.variableSymbol = variableSymbol;
    }

    public LocalDate getIssued() {
        return issued;
    }

    public void setIssued(LocalDate issued) {
        this.issued = issued;
    }

    public LocalDate getPaid() {
        return paid;
    }

    public void setPaid(LocalDate paid) {
        this.paid = paid;
    }
}
