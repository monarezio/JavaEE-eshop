package cz.kodytek.shop.ejb.client;

import cz.kodytek.shop.domain.api.interfaces.IDeliveryMethodEJB;
import cz.kodytek.shop.domain.api.interfaces.IGoodsEJB;
import cz.kodytek.shop.domain.api.interfaces.IInvoiceEJB;
import cz.kodytek.shop.domain.api.interfaces.IPaymentMethodEJB;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class EJBFactory {

    private Context ctx;

    private IPaymentMethodEJB paymentMethodEJB;
    private IDeliveryMethodEJB deliveryMethodEJB;
    private IGoodsEJB goodsEJB;
    private IInvoiceEJB invoiceEJB;

    public EJBFactory() {
        Properties jndiProperties = new Properties();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        try {
            ctx = new InitialContext(jndiProperties);
        } catch (NamingException e) {
            e.printStackTrace();
            System.exit(-1); //Just stop the app.
        }
    }

    public IPaymentMethodEJB getPaymentMethodEJB() throws NamingException {
        if (paymentMethodEJB == null)
            paymentMethodEJB = (IPaymentMethodEJB) ctx.lookup("ejb:/Shop/PaymentMethodEJB!cz.kodytek.shop.domain.api.interfaces.IPaymentMethodEJB");
        return paymentMethodEJB;
    }

    public IDeliveryMethodEJB getDeliveryMethodEJB() throws NamingException {
        if (deliveryMethodEJB == null)
            deliveryMethodEJB = (IDeliveryMethodEJB) ctx.lookup("ejb:/Shop/DeliveryMethodEJB!cz.kodytek.shop.domain.api.interfaces.IDeliveryMethodEJB");
        return deliveryMethodEJB;
    }

    public IGoodsEJB getGoodsEJB() throws NamingException {
        if (goodsEJB == null)
            goodsEJB = (IGoodsEJB) ctx.lookup("ejb:/Shop/GoodsEJB!cz.kodytek.shop.domain.api.interfaces.IGoodsEJB");
        return goodsEJB;
    }

    public IInvoiceEJB getInvoiceEJB() throws NamingException {
        if (invoiceEJB == null)
            invoiceEJB = (IInvoiceEJB) ctx.lookup("ejb:/Shop/InvoicesEJB!cz.kodytek.shop.domain.api.interfaces.IInvoiceEJB");
        return invoiceEJB;
    }
}
