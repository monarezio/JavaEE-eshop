package cz.kodytek.shop.data.entities.invoice;

import cz.kodytek.shop.data.entities.interfaces.invoice.IInvoice;
import cz.kodytek.shop.data.entities.interfaces.invoice.IInvoiceAddress;
import cz.kodytek.shop.data.entities.interfaces.invoice.IInvoiceCompany;
import cz.kodytek.shop.data.entities.interfaces.invoice.method.IDeliveryMethod;
import cz.kodytek.shop.data.entities.interfaces.invoice.method.IPaymentMethod;
import cz.kodytek.shop.data.entities.invoice.method.InvoiceDeliveryMethod;
import cz.kodytek.shop.data.entities.invoice.method.InvoicePaymentMethod;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Invoice implements IInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String contactFullName;

    @Column(nullable = false)
    private String contactEmail;

    @Column(nullable = false)
    private String contactPhone;

    @Column(unique = true, nullable = false)
    private String invoiceNumber;

    @Column(unique = true, nullable = false)
    private String variableSymbol;

    @OneToOne
    @JoinColumn
    private InvoiceDeliveryMethod deliveryMethod;

    @OneToOne
    @JoinColumn
    private InvoiceAddress deliveryAddress;

    @OneToOne
    @JoinColumn
    private InvoicePaymentMethod paymentMethod;

    @OneToOne
    @JoinColumn
    private InvoiceCompany company;

    @OneToMany
    @JoinColumn
    private Set<InvoiceGood> goods;

    @Column(nullable = false)
    private LocalDate issued;

    @Column
    private LocalDate payed;

    @Override
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    @Override
    public String getVariableSymbol() {
        return variableSymbol;
    }

    @Override
    public IInvoiceAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    @Override
    public IInvoiceCompany getCompany() {
        return company;
    }

    @Override
    public IPaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public IDeliveryMethod getDeliverMethod() {
        return deliveryMethod;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public LocalDate getDateIssued() {
        return issued;
    }

    @Override
    public LocalDate getDatePayed() {
        return payed;
    }

    @Override
    public String getFullName() {
        return contactFullName;
    }

    @Override
    public String getEmail() {
        return contactEmail;
    }

    @Override
    public String getPhone() {
        return contactPhone;
    }

    public void setFullName(String fullName) {
        contactFullName = fullName;
    }

    public void setEmail(String email) {
        contactEmail = email;
    }

    public void setPhone(String phone) {
        contactPhone = phone;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setVariableSymbol(String variableSymbol) {
        this.variableSymbol = variableSymbol;
    }

    public void setDeliveryMethod(InvoiceDeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public void setDeliveryAddress(InvoiceAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setPaymentMethod(InvoicePaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setCompany(InvoiceCompany company) {
        this.company = company;
    }

    public void setIssued(LocalDate issued) {
        this.issued = issued;
    }

    public void setPayed(LocalDate payed) {
        this.payed = payed;
    }

    public List<InvoiceGood> getGoods() {
        return goods.stream().sorted(Comparator.comparing(InvoiceGood::getTitle)).collect(Collectors.toList());
    }

    public void setGoods(Set<InvoiceGood> goods) {
        this.goods = goods;
    }
}
