package cz.kodytek.shop.domain.api.interfaces;

import cz.kodytek.shop.domain.api.models.*;

import javax.ejb.Remote;

@Remote
public interface IInvoiceEJB {

    InvoicesPage get(String search, int page);

    CreatedInvoice get(long id);

    void destroy(long id);

    ResultDTO<Boolean> edit(long id, EditedInvoice invoice);

    ResultDTO<Invoice> create(Invoice invoice);

}
