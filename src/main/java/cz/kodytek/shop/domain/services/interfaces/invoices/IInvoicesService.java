package cz.kodytek.shop.domain.services.interfaces.invoices;

import cz.kodytek.shop.data.entities.interfaces.invoice.IInvoice;
import cz.kodytek.shop.data.entities.invoice.Invoice;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;
import cz.kodytek.shop.domain.models.invoices.NewInvoice;

public interface IInvoicesService {

    IInvoice addInvoice(NewInvoice invoice);

    IInvoice addInvoice(NewInvoice invoice, long userId);

    IEntityPage<IInvoice> getInvoices(int page);

    IInvoice getForInvoiceNumber(String invoiceNumber);

    void delete(String invoiceNumber);

}
