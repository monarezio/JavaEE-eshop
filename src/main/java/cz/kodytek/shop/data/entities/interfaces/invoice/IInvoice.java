package cz.kodytek.shop.data.entities.interfaces.invoice;

import cz.kodytek.shop.data.entities.interfaces.IEntityId;
import cz.kodytek.shop.data.entities.interfaces.invoice.method.IDeliveryMethod;
import cz.kodytek.shop.data.entities.interfaces.invoice.method.IPaymentMethod;

import java.time.LocalDate;

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

    LocalDate getDatePayed();

}
