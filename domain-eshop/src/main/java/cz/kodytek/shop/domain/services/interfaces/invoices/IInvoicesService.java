package cz.kodytek.shop.domain.services.interfaces.invoices;

import cz.kodytek.shop.data.entities.interfaces.invoice.IInvoice;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;
import cz.kodytek.shop.domain.models.invoices.NewInvoice;

public interface IInvoicesService {

    IInvoice addInvoice(NewInvoice invoice);

    IInvoice addInvoice(NewInvoice invoice, long userId);

    IEntityPage<IInvoice> getInvoices(String search, int page, int perPage);

    IInvoice getForInvoiceNumber(String invoiceNumber);

    IInvoice get(long id);

    boolean edit(long id, String fullName, String email, String phone, boolean paid);

    void deleteForInvoice(String invoiceNumber);

    void delete(Long id);

}
