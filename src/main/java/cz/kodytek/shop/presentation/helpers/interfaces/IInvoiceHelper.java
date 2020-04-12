package cz.kodytek.shop.presentation.helpers.interfaces;

import cz.kodytek.shop.data.entities.interfaces.address.IAddress;
import cz.kodytek.shop.data.entities.interfaces.company.ICompany;
import cz.kodytek.shop.data.entities.interfaces.invoice.method.IDeliveryMethod;
import cz.kodytek.shop.data.entities.interfaces.invoice.method.IPaymentMethod;

public interface IInvoiceHelper {

    String parsePaymentMethod(IPaymentMethod pm);

    String parseDeliveryMethod(IDeliveryMethod dm);

    String parseCompany(ICompany c);

    String parseAddress(IAddress a);

}
