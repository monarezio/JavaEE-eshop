package cz.kodytek.shop.domain.services.interfaces.invoices;

import cz.kodytek.shop.data.entities.interfaces.invoice.IInvoice;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;

public interface IInvoicesService {

    void addInvoice(IInvoice invoice);

    void addInvoice(IInvoice invoice, long userId);

    IEntityPage<IInvoice> getInvoices(int page);

}
