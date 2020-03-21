package cz.kodytek.shop.data.entities.interfaces;

import java.util.List;

public interface IEntityWithAddresses {

    List<? extends IAddress> getAddresses();

}
