package cz.kodytek.shop.domain.services.invoices;

import cz.kodytek.shop.data.connection.interfaces.IHibernateSessionFactory;
import cz.kodytek.shop.data.entities.interfaces.invoice.IInvoice;
import cz.kodytek.shop.data.entities.invoice.Invoice;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;
import cz.kodytek.shop.domain.services.interfaces.invoices.IInvoicesService;

import javax.inject.Inject;

public class InvoicesService implements IInvoicesService {

    @Inject
    private IHibernateSessionFactory sessionFactory;

    @Override
    public void addInvoice(IInvoice invoice) {
        sessionFactory.createSession(s -> {
            Invoice i = new Invoice();
        });
    }

    @Override
    public void addInvoice(IInvoice invoice, long userId) {

    }

    @Override
    public IEntityPage<IInvoice> getInvoices(int page) {
        return null;
    }
}
