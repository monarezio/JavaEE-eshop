package cz.kodytek.shop.domain.models.company;

import cz.kodytek.shop.data.entities.Address;
import cz.kodytek.shop.data.entities.interfaces.address.IAddressWithId;
import cz.kodytek.shop.domain.models.interfaces.company.ICreatedCompany;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Named
@RequestScoped
public class Company implements ICreatedCompany {

    @NotNull(message = "Company name cannot be empty.")
    @Size(min = 1, message = "Company name must contain at least 2 characters.")
    private String name;

    @NotNull(message = "IČO cannot be empty.")
    @Min(value = 10000000, message = "IČO must contain exactly 8 numeric characters.")
    @Max(value = 99999999, message = "IČO must contain exactly 8 numeric characters.")
    private Integer identificationNumber;

    @Size(min = 10, max = 10, message = "DIČ must contain exactly 10 characters.")
    private String taxIdentificationNumber;

    private AddressCreationType addressCreationType = AddressCreationType.NEW;

    private IAddressWithId address = new Address();

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

    public void setAddress(IAddressWithId address) {
        this.address = address;
    }

    public AddressCreationType getAddressCreationType() {
        return addressCreationType;
    }

    public void setAddressCreationType(AddressCreationType addressCreationType) {
        this.addressCreationType = addressCreationType;
    }

    public AddressCreationType[] getAddressCreationTypes() {
        return AddressCreationType.values();
    }
}
