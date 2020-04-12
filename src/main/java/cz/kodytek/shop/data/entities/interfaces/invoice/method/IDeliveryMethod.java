package cz.kodytek.shop.data.entities.interfaces.invoice.method;

import cz.kodytek.shop.data.entities.interfaces.IEntityId;
import org.javamoney.moneta.Money;

public interface IDeliveryMethod extends IEntityId {

    String getName();

    Money getCost();

}
