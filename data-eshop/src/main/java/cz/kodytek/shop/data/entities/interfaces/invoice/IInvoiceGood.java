package cz.kodytek.shop.data.entities.interfaces.invoice;

import cz.kodytek.shop.data.entities.interfaces.IEntityId;
import org.javamoney.moneta.Money;

public interface IInvoiceGood extends IEntityId {

    long getOriginalId();
    String getTitle();
    Money getCost();
    int getAmount();

}
