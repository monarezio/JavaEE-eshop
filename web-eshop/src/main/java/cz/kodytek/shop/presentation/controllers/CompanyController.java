package cz.kodytek.shop.presentation.controllers;

import cz.kodytek.shop.data.entities.interfaces.address.IAddressWithId;
import cz.kodytek.shop.data.entities.interfaces.company.ICompany;
import cz.kodytek.shop.data.entities.interfaces.company.ICompanyWithId;
import cz.kodytek.shop.domain.models.interfaces.company.ICreatedCompany;
import cz.kodytek.shop.domain.services.interfaces.address.IAddressService;
import cz.kodytek.shop.domain.services.interfaces.company.ICompanyService;
import cz.kodytek.shop.jms.JMSService;
import cz.kodytek.shop.presentation.session.models.FlashMessage;
import cz.kodytek.shop.presentation.session.models.FlashMessageType;
import cz.kodytek.shop.presentation.session.services.interfaces.IUserSessionService;
import cz.kodytek.shop.presentation.session.services.interfaces.messages.IFlashMessagesService;
import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class CompanyController {

    @Inject
    private ICompanyService companyService;

    @Inject
    private IAddressService addressService;

    @Inject
    private IUserSessionService userSessionService;

    @Inject
    private IRequestUtils requestUtils;

    @Inject
    private IFlashMessagesService flashMessagesService;

    @Inject
    private JMSService jmsService;


    private List<IAddressWithId> addressesForUser = null;

    private ICompanyWithId company;

    public void create(ICreatedCompany company) {
        jmsService.sendMessage(requestUtils.getIp() + " attempting to create a company.");

        if (companyService.create(userSessionService.getCurrentUser().getId(), company) != null)
            requestUtils.redirect("/pages/user/account.xhtml", new FlashMessage("Company added successfully.", FlashMessageType.success));
        else
            flashMessagesService.add(new FlashMessage("There was a unknown error, sorry.", FlashMessageType.alert));
    }

    public void edit(ICompanyWithId company) {
        jmsService.sendMessage(requestUtils.getIp() + " attempting to edit a company, " + company.getId() + ".");

        if(companyService.edit(userSessionService.getCurrentUser().getId(), company))
            requestUtils.redirect("/pages/user/account.xhtml", new FlashMessage("Company edited successfully.", FlashMessageType.success));
        else
            flashMessagesService.add(new FlashMessage("There was a unknown error, sorry.", FlashMessageType.alert));
    }

    public void delete(ICompanyWithId company) {
        jmsService.sendMessage(requestUtils.getIp() + " deleting a company, " + company.getId() + ".");
        companyService.delete(userSessionService.getCurrentUser().getId(), company.getId());
        requestUtils.redirect("/pages/user/account.xhtml", new FlashMessage("Company deleted successfully.", FlashMessageType.success));
    }

    public List<IAddressWithId> getAddresses() {
        if(addressesForUser == null)
            addressesForUser = addressService.getAllForUser(userSessionService.getCurrentUser().getId());
        return addressesForUser;
    }

    public ICompanyWithId getCompany(long id) {
        if(company == null)
            company = companyService.get(userSessionService.getCurrentUser().getId(), id);

        System.out.println("Company ID: " + company.getId());
        return company;
    }

}
