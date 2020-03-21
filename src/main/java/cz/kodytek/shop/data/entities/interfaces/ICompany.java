package cz.kodytek.shop.data.entities.interfaces;

public interface ICompany {

    String getName();

    long getIdentificationNumber();

    String getTaxIdentificationNumber();

    IAddress getAddress();

}
