package cz.kodytek.shop.domain.api.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class Company implements Serializable {

    @NotNull(message = "Name cannot be null.")
    @Size(min = 1)
    private String name;

    @NotNull(message = "Identification ID cannot be empty.")
    @Min(value = 10000000, message = "Identification must be exactly 8 characters.")
    @Max(value = 99999999, message = "Identification must be exactly 8 characters.")
    private Integer identificationNumber;

    @Size(min = 10, max = 10, message = "Tax identification number must contain exactly 10 characters.")
    private String taxIdentificationNumber;

    @NotNull(message = "Address cannot be null.")
    private Address address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(Integer identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
