package cz.kodytek.shop.domain.api.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class Address implements Serializable {

    @NotNull(message = "Street cannot be null.")
    @Size(min = 3, message = "Street must contain at least 3 characters")
    private String street;

    @NotNull(message = "City cannot be null.")
    @Size(min = 2, message = "City must contain at least 2 characters")
    private String city;

    @NotNull(message = "Post code cannot be null")
    private String postCode;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
