package cz.kodytek.shop.data.entities.interfaces.company;

import cz.kodytek.shop.data.entities.interfaces.address.IAddressWithId;

public interface ICompany {

    String getName();

    Integer getIdentificationNumber();

    String getTaxIdentificationNumber();

    IAddressWithId getAddress();

}
