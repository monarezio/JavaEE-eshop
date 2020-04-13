package cz.kodytek.shop.presentation.helpers;

import cz.kodytek.shop.data.entities.interfaces.address.IAddress;
import cz.kodytek.shop.data.entities.interfaces.company.ICompany;
import cz.kodytek.shop.data.entities.interfaces.invoice.method.IDeliveryMethod;
import cz.kodytek.shop.data.entities.interfaces.invoice.method.IPaymentMethod;
import cz.kodytek.shop.presentation.helpers.interfaces.IGoodsHelper;
import cz.kodytek.shop.presentation.helpers.interfaces.IInvoiceHelper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Named
@ApplicationScoped
public class InvoiceHelper implements IInvoiceHelper {

    @Inject
    private IGoodsHelper goodsHelper;

    @Override
    public String parsePaymentMethod(IPaymentMethod pm) {
        return pm.getName() + " (+" + goodsHelper.parsePrice(pm.getCost()) + " Kč)";
    }

    @Override
    public String parseDeliveryMethod(IDeliveryMethod dm) {
        return dm.getName() + " (+" + goodsHelper.parsePrice(dm.getCost()) + " Kč)";
    }

    @Override
    public String parseCompany(ICompany c) {
        return c.getIdentificationNumber() + " | " + c.getName();
    }

    @Override
    public String parseAddress(IAddress a) {
        return a.getCity() + " " + a.getStreet() + " " + a.getPostCode();
    }

    @Override
    public String parseDate(LocalDate date) {
        return date != null ? date.format(DateTimeFormatter.ofPattern("MM/dd/YYYY")) : "";
    }
}
