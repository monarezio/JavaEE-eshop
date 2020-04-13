package cz.kodytek.shop.domain.services.interfaces.invoices;

import cz.kodytek.shop.data.entities.interfaces.invoice.method.IDeliveryMethod;

import java.util.List;

public interface IDeliveryMethodService {

    void add(IDeliveryMethod paymentMethod);

    List<IDeliveryMethod> getAll();

}
