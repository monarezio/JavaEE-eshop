package cz.kodytek.shop.presentation.controllers;

import cz.kodytek.shop.data.entities.interfaces.invoice.IInvoice;
import cz.kodytek.shop.data.entities.invoice.InvoiceGood;
import cz.kodytek.shop.domain.services.interfaces.invoices.IInvoicesService;
import cz.kodytek.shop.jms.JMSService;
import cz.kodytek.shop.presentation.session.models.FlashMessage;
import cz.kodytek.shop.presentation.session.models.FlashMessageType;
import cz.kodytek.shop.presentation.session.services.interfaces.messages.IFlashMessagesService;
import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;
import org.javamoney.moneta.Money;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class InvoiceDetailController {

    @Inject
    private IInvoicesService invoicesService;

    @Inject
    private IRequestUtils requestUtils;

    @Inject
    private IFlashMessagesService flashMessagesService;

    @Inject
    private JMSService jmsService;

    private IInvoice invoice;

    public IInvoice getInvoice() {
        if (requestUtils.hasParam("number")) {
            if (invoice == null) {
                jmsService.sendMessage(requestUtils.getIp() + " attempting to load invoice, " + requestUtils.getParam("number") + ".");
                invoice = invoicesService.getForInvoiceNumber(requestUtils.getParam("number"));
            }
            if (invoice != null) {
                return invoice;
            } else
                requestUtils.redirect("/pages/index.xhtml");
        } else
            requestUtils.redirect("/pages/index.xhtml");

        return null;
    }

    public void delete() {
        if (requestUtils.hasParam("number")) {
            jmsService.sendMessage(requestUtils.getIp() + " deleting invoice, " + requestUtils.getParam("number") + ".");
            invoicesService.deleteForInvoice(requestUtils.getParam("number"));
        }
        requestUtils.redirect("/pages/index.xhtml", new FlashMessage("Invoice successfully canceled.", FlashMessageType.success));
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
