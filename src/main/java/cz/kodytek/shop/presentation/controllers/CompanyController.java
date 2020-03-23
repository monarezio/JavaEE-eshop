package cz.kodytek.shop.presentation.controllers;

import cz.kodytek.shop.data.entities.interfaces.IAddress;
import cz.kodytek.shop.data.entities.interfaces.address.IAddressWithId;
import cz.kodytek.shop.data.entities.interfaces.company.ICompany;
import cz.kodytek.shop.data.entities.interfaces.company.ICompanyWithId;
import cz.kodytek.shop.domain.models.company.Company;
import cz.kodytek.shop.domain.services.interfaces.company.ICompanyService;
import cz.kodytek.shop.presentation.session.models.FlashMessage;
import cz.kodytek.shop.presentation.session.models.FlashMessageType;
import cz.kodytek.shop.presentation.session.services.interfaces.IUserSessionService;
import cz.kodytek.shop.presentation.session.services.interfaces.messages.IFlashMessagesService;
import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class CompanyController {

    @Inject
    ICompanyService companyService;

    @Inject
    IUserSessionService userSessionService;

    @Inject
    IRequestUtils requestUtils;

    @Inject
    IFlashMessagesService flashMessagesService;

    public void create(Company company) {
        if (companyService.create(userSessionService.getCurrentUser().getId(), company) != null)
            requestUtils.redirect("/pages/user/account.xhtml", new FlashMessage("Company added successfully.", FlashMessageType.success));
        else
            flashMessagesService.add(new FlashMessage("There was a unknown error, sorry.", FlashMessageType.alert));
    }

    public void delete(ICompanyWithId company) {

        System.out.println("COMPANY ID: " + company.getName());
        System.out.println("COMPANY ID: " + company.getId());

        companyService.delete(userSessionService.getCurrentUser().getId(), company.getId());
        requestUtils.redirect("/pages/user/account.xhtml", new FlashMessage("Company deleted successfully.", FlashMessageType.success));
    }

    public void print(String e) {
        System.out.println(e);
    }

    public List<IAddressWithId> getAddresses() {
        return new ArrayList<>();
    }

}
