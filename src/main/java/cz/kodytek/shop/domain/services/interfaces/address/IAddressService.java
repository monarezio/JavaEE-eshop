package cz.kodytek.shop.domain.services.interfaces.address;

import cz.kodytek.shop.data.entities.interfaces.address.IAddress;
import cz.kodytek.shop.data.entities.interfaces.address.IAddressWithId;

public interface IAddressService {

    IAddressWithId get(long userId, long addressId);

    boolean edit(long userId, IAddressWithId address);

    void delete(long userId, IAddressWithId address);

    IAddressWithId create(long userId, IAddress company);

}
