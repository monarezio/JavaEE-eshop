package cz.kodytek.shop.domain.services.interfaces.address;

import cz.kodytek.shop.data.entities.interfaces.address.IAddress;
import cz.kodytek.shop.data.entities.interfaces.address.IAddressWithId;

import java.util.List;

public interface IAddressService {

    IAddressWithId get(long userId, long addressId);

    List<IAddressWithId> getAllForUser(long userId);

    boolean edit(long userId, IAddressWithId address);

    boolean delete(long userId, long addressId);

    IAddressWithId create(long userId, IAddress company);

}
