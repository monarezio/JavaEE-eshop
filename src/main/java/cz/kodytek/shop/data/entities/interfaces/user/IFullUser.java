package cz.kodytek.shop.data.entities.interfaces.user;

import cz.kodytek.shop.data.entities.interfaces.IEntityWithAddresses;
import cz.kodytek.shop.data.entities.interfaces.IEntityWithCompany;
import cz.kodytek.shop.data.entities.interfaces.IEntityWithInvoices;

public interface IFullUser extends IUserWithRights, IEntityWithAddresses, IEntityWithCompany, IEntityWithInvoices {
}
