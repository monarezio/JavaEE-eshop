package cz.kodytek.shop.data.entities.interfaces.invoice;

import cz.kodytek.shop.data.entities.interfaces.IEntityId;
import cz.kodytek.shop.data.entities.interfaces.invoice.method.IDeliveryMethod;
import cz.kodytek.shop.data.entities.interfaces.invoice.method.IPaymentMethod;
import cz.kodytek.shop.data.entities.invoice.InvoiceGood;

import java.time.LocalDate;
import java.util.List;

public interface IInvoice extends IEntityId {

    String getFullName();

    String getEmail();

    String getPhone();

    String getInvoiceNumber();

    String getVariableSymbol();

    IInvoiceAddress getDeliveryAddress();

    IInvoiceCompany getCompany();

    IPaymentMethod getPaymentMethod();

    IDeliveryMethod getDeliverMethod();

    LocalDate getDateIssued();

    LocalDate getDatePaid();

    List<InvoiceGood> getGoods();

}
