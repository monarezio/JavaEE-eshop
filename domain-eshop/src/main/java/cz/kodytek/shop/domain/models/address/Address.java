package cz.kodytek.shop.domain.models.address;

import cz.kodytek.shop.data.entities.interfaces.address.IAddress;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Named
@RequestScoped
public class Address implements IAddress {

    @NotNull(message = "Street cannot be empty.")
    @Size(min = 3, message = "Street must contain at least 3 characters.")
    private String street;

    @NotNull(message = "City cannot be empty.")
    @Size(min = 2, message = "City must contain at least 2 characters.")
    private String city;

    @NotNull(message = "Post code cannot be empty.")
    private String postCode;

    @Override
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
