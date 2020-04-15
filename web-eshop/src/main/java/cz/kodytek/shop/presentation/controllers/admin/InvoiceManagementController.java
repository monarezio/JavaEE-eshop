package cz.kodytek.shop.presentation.controllers.admin;

import cz.kodytek.shop.data.entities.interfaces.invoice.IInvoice;
import cz.kodytek.shop.data.entities.invoice.InvoiceGood;
import cz.kodytek.shop.domain.models.EntityFilter;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;
import cz.kodytek.shop.domain.services.interfaces.invoices.IInvoicesService;
import cz.kodytek.shop.presentation.session.models.FlashMessage;
import cz.kodytek.shop.presentation.session.models.FlashMessageType;
import cz.kodytek.shop.presentation.session.services.interfaces.messages.IFlashMessagesService;
import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;
import org.javamoney.moneta.Money;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class InvoiceManagementController {

    private static final int PER_PAGE = 20;

    @Inject
    private IInvoicesService invoicesService;

    @Inject
    private IRequestUtils requestUtils;

    @Inject
    private IFlashMessagesService flashMessagesService;

    private IEntityPage<IInvoice> invoicesPage;

    private EntityFilter entityFilter;

    private IInvoice invoice;

    public void search(EntityFilter filter) {
        requestUtils.redirect("/pages/admin/invoices/index.xhtml?search=" + filter.getSearchFilter() + "&" + filter.getPage());
    }

    public void delete(IInvoice invoice) {
        invoicesService.delete(invoice.getId());
        flashMessagesService.add(new FlashMessage("Successfully deleted.", FlashMessageType.success));
        search(getEntityFilter());
    }

    public EntityFilter getEntityFilter() {
        if (entityFilter == null) {
            entityFilter = new EntityFilter(requestUtils.hasParam("search") ? requestUtils.getParam("search") : "");
            entityFilter.setPage(0);
            try {
                entityFilter.setPage(Integer.parseInt(requestUtils.getParam("page")));
            } catch (Exception ignore) {
            }
        }

        return entityFilter;
    }

    public IInvoice getInvoice() {
        try {
            if (requestUtils.hasParam("id") && invoice == null)
                invoice = invoicesService.get(Integer.parseInt(requestUtils.getParam("id")));
            if (invoice == null)
                requestUtils.redirect("/pages/admin/invoices/index.xhtml");
        } catch (Exception e) {
            requestUtils.redirect("/pages/admin/invoices/index.xhtml");
        }

        return invoice;
    }

    public IEntityPage<IInvoice> getInvoicesPage() {
        if (invoicesPage == null)
            invoicesPage = invoicesService.getInvoices(getEntityFilter().getSearchFilter(), entityFilter.getPage(), PER_PAGE);
        System.out.println(invoicesPage.getPagesCount());
        return invoicesPage;
    }

    public void pay(IInvoice invoice) {
        if (invoicesService.edit(invoice.getId(), invoice.getFullName(), invoice.getEmail(), invoice.getPhone(), true))
            flashMessagesService.add(new FlashMessage("Invoice set on paid", FlashMessageType.success));
        else
            flashMessagesService.add(new FlashMessage("Unknown error.", FlashMessageType.alert));
        search(entityFilter);
    }

    public int getUnitCount() {
        if (getInvoice() == null)
            return 0;

        return getInvoice().getGoods().stream().mapToInt(InvoiceGood::getAmount).sum();
    }

    public Money getPriceCount() {
        if (getInvoice() == null)
            return Money.of(0, "CZK");

        return getInvoice().getGoods().stream().map(i -> i.getCost().multiply(i.getAmount())).reduce(Money::add).get()
                .add(getInvoice().getDeliverMethod().getCost())
                .add(getInvoice().getPaymentMethod().getCost());
    }
}
