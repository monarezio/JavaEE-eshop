package cz.kodytek.shop.domain.api.models;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

public class Invoice implements Serializable {

    @NotNull(message = "Full name cannot be null.")
    @Size(min = 3, message = "Full name must contain at least 3 characters.")
    private String fullName;

    @NotNull(message = "Email cannot be null.")
    @Email(message = "Email must be in correct format")
    private String email;

    @NotNull(message = "Phone number cannot be null.")
    private String phone;

    @NotNull(message = "Delivery method id cannot be null.")
    private Long deliveryMethodId;

    @NotNull(message = "Payment method id cannot be null.")
    private Long paymentMethodId;

    private Company company;

    @NotNull(message = "Delivery address cannot be null.")
    private Address deliveryAddress;

    @NotNull(message = "Cart cannot be null.")
    @NotEmpty(message = "Cart cannot be empty.")
    private List<Product> cart;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(Long deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<Product> getCart() {
        return cart;
    }

    public void setCart(List<Product> cart) {
        this.cart = cart;
    }
}
