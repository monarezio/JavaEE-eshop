package cz.kodytek.shop.presentation.helpers;

import cz.kodytek.shop.data.entities.interfaces.address.IAddress;
import cz.kodytek.shop.presentation.helpers.interfaces.IAddressHelper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class AddressHelper implements IAddressHelper {
    @Override
    public String parse(IAddress address) {
        return address.getCity() + " " + address.getStreet();
    }
}
