package cz.kodytek.shop.data.entities.interfaces.invoice.method;

import cz.kodytek.shop.data.entities.interfaces.IEntityId;
import org.javamoney.moneta.Money;

import java.io.Serializable;

public interface IPaymentMethod extends IEntityId {

    String getName();

    Money getCost();

}
