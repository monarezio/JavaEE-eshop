package cz.kodytek.shop.data.entities.interfaces.company;

import cz.kodytek.shop.data.entities.interfaces.IAddress;

public interface ICompany {

    String getName();

    int getIdentificationNumber();

    String getTaxIdentificationNumber();

    IAddress getAddress();

}
