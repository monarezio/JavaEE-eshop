package cz.kodytek.shop.domain.models.invoices;

import cz.kodytek.shop.domain.models.address.Address;
import cz.kodytek.shop.domain.models.company.Company;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewInvoice {

    @NotNull(message = "Name cannot be empty.")
    @Size(min = 3, message = "Name must contain at least 3 characters.")
    private String fullName;

    @NotNull(message = "Email cannot be empty.")
    @Email(message = "Email has to be in correct format.")
    private String email;

    @NotNull(message = "Phone cannot be empty.")
    private String phone;

    @NotNull
    private Long deliveryMethodId;

    @NotNull
    private Long paymentMethodId;

    @NotNull
    private Long companyId;

    @NotNull
    private Long addressId;

    private Company company = new Company();

    private boolean companyBuying;

    @NotNull
    private Address address = new Address();

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Company getCompany() {
        return company;
    }

    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDeliveryMethodId(Long deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public Address getAddress() {
        return address;
    }

    public boolean isCompanyBuying() {
        return companyBuying;
    }

    public void setCompanyBuying(boolean companyBuying) {
        this.companyBuying = companyBuying;
    }
}
