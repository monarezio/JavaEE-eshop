package cz.kodytek.shop.domain.models.interfaces.company;

import cz.kodytek.shop.data.entities.interfaces.company.ICompany;
import cz.kodytek.shop.domain.models.company.AddressCreationType;

public interface ICreatedCompany extends ICompany {

    AddressCreationType getAddressCreationType();

    void setAddressCreationType(AddressCreationType addressCreationType);

    AddressCreationType[] getAddressCreationTypes();

}
