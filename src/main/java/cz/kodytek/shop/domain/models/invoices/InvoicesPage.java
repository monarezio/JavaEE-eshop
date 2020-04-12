package cz.kodytek.shop.domain.models.invoices;

import cz.kodytek.shop.data.entities.interfaces.invoice.IInvoice;
import cz.kodytek.shop.data.entities.invoice.Invoice;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;

import java.util.List;

public class InvoicesPage implements IEntityPage<IInvoice> {

    private List<Invoice> invoices;
    private int page;
    private int pageCount;

    @Override
    public List<Invoice> getAll() {
        return invoices;
    }

    @Override
    public int getCurrentPage() {
        return page;
    }

    @Override
    public int getPagesCount() {
        return pageCount;
    }
}
