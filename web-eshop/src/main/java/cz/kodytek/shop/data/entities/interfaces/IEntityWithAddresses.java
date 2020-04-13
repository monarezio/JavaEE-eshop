package cz.kodytek.shop.data.entities.interfaces;

import cz.kodytek.shop.data.entities.interfaces.address.IAddressWithId;

import java.util.List;

public interface IEntityWithAddresses {

    List<? extends IAddressWithId> getAddresses();

}
