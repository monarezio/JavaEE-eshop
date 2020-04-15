package cz.kodytek.shop.presentation.controllers;

import cz.kodytek.shop.data.entities.interfaces.invoice.IInvoice;
import cz.kodytek.shop.data.entities.interfaces.invoice.method.IDeliveryMethod;
import cz.kodytek.shop.data.entities.interfaces.invoice.method.IPaymentMethod;
import cz.kodytek.shop.data.entities.interfaces.user.IFullUser;
import cz.kodytek.shop.domain.models.address.Address;
import cz.kodytek.shop.domain.models.company.Company;
import cz.kodytek.shop.domain.models.invoices.NewInvoice;
import cz.kodytek.shop.domain.services.interfaces.invoices.IDeliveryMethodService;
import cz.kodytek.shop.domain.services.interfaces.invoices.IInvoicesService;
import cz.kodytek.shop.domain.services.interfaces.invoices.IPaymentMethodService;
import cz.kodytek.shop.domain.services.interfaces.users.IUserService;
import cz.kodytek.shop.jms.JMSService;
import cz.kodytek.shop.presentation.session.models.FlashMessage;
import cz.kodytek.shop.presentation.session.models.FlashMessageType;
import cz.kodytek.shop.presentation.session.services.interfaces.ICartService;
import cz.kodytek.shop.presentation.session.services.interfaces.IUserSessionService;
import cz.kodytek.shop.presentation.session.services.interfaces.messages.IFlashMessagesService;
import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

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

    @Inject
    private IInvoicesService invoicesService;

    @Inject
    private IRequestUtils requestUtils;

    @Inject
    private ICartService cartService;

    @Inject
    private IFlashMessagesService flashMessagesService;

    @Inject
    private JMSService jmsService;

    private List<IPaymentMethod> paymentMethods;

    private List<IDeliveryMethod> deliveryMethods;

    private IFullUser user;

    private NewInvoice newInvoice;

    public void createInvoice() {
        jmsService.sendMessage(requestUtils.getIp() + " attempting to create invoice.");

        boolean valid = true;
        NewInvoice ni = getInvoice();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        if (ni.getCompanyId() == null && ni.isCompanyBuying()) {
            Set<ConstraintViolation<Company>> errors = validator.validate(ni.getCompany());
            if (errors.size() > 0) {
                valid = false;
                errors.forEach(i -> {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(i.getMessage()));
                });
            }
        }

        if (ni.getAddressId() == null) {
            Set<ConstraintViolation<Address>> errors = validator.validate(ni.getAddress());
            if (errors.size() > 0) {
                valid = false;
                errors.forEach(i -> {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(i.getMessage()));
                });
            }
        }

        if(!cartService.isValid()) {
            valid = false;
            flashMessagesService.add(new FlashMessage("We don't have enough of this product on stock, please remove few units.", FlashMessageType.success));
        }

        if (valid) {
            if (sessionService.isLoggedIn()) {
                IInvoice i = invoicesService.addInvoice(getInvoice(), sessionService.getCurrentUser().getId());
                requestUtils.redirect("/pages/user/account.xhtml", new FlashMessage("Your order, " + i.getInvoiceNumber() + ", was successfully created. Please check your invoices in your profile section.", FlashMessageType.success));
            } else {
                IInvoice i = invoicesService.addInvoice(getInvoice());
                requestUtils.redirect("/pages/invoice/detail.xhtml?number=" + i.getInvoiceNumber(), new FlashMessage("Your order, " + i.getInvoiceNumber() + ", was successfully created.", FlashMessageType.success));
            }

            cartService.getCart().clear();
        }
    }

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
        if (cartService.getCount() <= 0 || !cartService.isValid())
            requestUtils.redirect("/pages/cart/index.xhtml");
        else if (newInvoice == null) {
            newInvoice = new NewInvoice();
            newInvoice.setCart(cartService.getCart());

            IFullUser fu = getUser();
            if (fu != null) {
                newInvoice.setFullName(fu.getName());
                newInvoice.setEmail(fu.getEmail());
                newInvoice.setPhone(fu.getPhoneNumber());

                if (fu.getCompanies().size() > 0)
                    newInvoice.setCompanyId(fu.getCompanies().get(0).getId());
                if (fu.getAddresses().size() > 0)
                    newInvoice.setAddressId(fu.getAddresses().get(0).getId());
            }
        }

        return newInvoice;
    }

    public IFullUser getUser() {
        if (user == null && sessionService.isLoggedIn())
            user = userService.getUser(sessionService.getCurrentUser().getId());

        return user;
    }
}
