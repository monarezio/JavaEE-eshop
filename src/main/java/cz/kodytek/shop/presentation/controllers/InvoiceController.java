package cz.kodytek.shop.presentation.controllers;

import cz.kodytek.shop.data.entities.interfaces.company.ICompanyWithId;
import cz.kodytek.shop.data.entities.interfaces.invoice.method.IDeliveryMethod;
import cz.kodytek.shop.data.entities.interfaces.invoice.method.IPaymentMethod;
import cz.kodytek.shop.data.entities.interfaces.user.IFullUser;
import cz.kodytek.shop.data.entities.interfaces.user.IUserWithRights;
import cz.kodytek.shop.domain.models.company.Company;
import cz.kodytek.shop.domain.models.invoices.NewInvoice;
import cz.kodytek.shop.domain.services.interfaces.invoices.IDeliveryMethodService;
import cz.kodytek.shop.domain.services.interfaces.invoices.IPaymentMethodService;
import cz.kodytek.shop.domain.services.interfaces.users.IUserService;
import cz.kodytek.shop.presentation.session.services.interfaces.IUserSessionService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class InvoiceController {

    @Inject
    private IPaymentMethodService paymentMethodService;

    @Inject
    private IDeliveryMethodService deliveryMethodService;

    @Inject
    private IUserSessionService sessionService;

    @Inject
    private IUserService userService;

    private List<IPaymentMethod> paymentMethods;

    private List<IDeliveryMethod> deliveryMethods;

    private IFullUser user;

    private NewInvoice newInvoice;

    public List<IPaymentMethod> getPaymentMethods() {
        if (paymentMethods == null)
            paymentMethods = paymentMethodService.getAll();
        return paymentMethods;
    }

    public List<IDeliveryMethod> getDeliveryMethods() {
        if (deliveryMethods == null)
            deliveryMethods = deliveryMethodService.getAll();
        return deliveryMethods;
    }

    public NewInvoice getInvoice() {
        if (newInvoice == null) {
            newInvoice = new NewInvoice();

            IFullUser fu = getUser();
            if (fu != null) {
                newInvoice.setFullName(fu.getName());
                newInvoice.setEmail(fu.getEmail());
                newInvoice.setPhone(fu.getPhoneNumber());

                if(fu.getCompanies().size() > 0)
                    newInvoice.setCompanyId(fu.getCompanies().get(0).getId());
                if(fu.getAddresses().size() > 0)
                    newInvoice.setAddressId(fu.getAddresses().get(0).getId());
            }
        }

        return newInvoice;
    }

    public IFullUser getUser() {
        if(user == null && sessionService.isLoggedIn())
            user = userService.getUser(sessionService.getCurrentUser().getId());

        return user;
    }
}
