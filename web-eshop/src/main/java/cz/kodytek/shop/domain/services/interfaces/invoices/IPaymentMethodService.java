package cz.kodytek.shop.domain.services.interfaces.invoices;

import cz.kodytek.shop.data.entities.interfaces.invoice.method.IPaymentMethod;
import cz.kodytek.shop.data.entities.invoice.method.PaymentMethod;

import java.util.List;

public interface IPaymentMethodService {

    void add(IPaymentMethod paymentMethod);

    List<IPaymentMethod> getAll();

}
