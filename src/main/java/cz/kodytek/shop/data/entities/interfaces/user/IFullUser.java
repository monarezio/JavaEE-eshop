package cz.kodytek.shop.data.entities.interfaces.user;

import cz.kodytek.shop.data.entities.interfaces.IEntityWithAddresses;
import cz.kodytek.shop.data.entities.interfaces.IEntityWithCompany;

public interface IFullUser extends IUserWithRights, IEntityWithAddresses, IEntityWithCompany {
}
