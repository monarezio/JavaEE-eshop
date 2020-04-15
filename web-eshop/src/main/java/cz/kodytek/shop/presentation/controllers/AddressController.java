package cz.kodytek.shop.presentation.controllers;

import cz.kodytek.shop.data.entities.interfaces.address.IAddress;
import cz.kodytek.shop.data.entities.interfaces.address.IAddressWithId;
import cz.kodytek.shop.domain.services.interfaces.address.IAddressService;
import cz.kodytek.shop.jms.JMSService;
import cz.kodytek.shop.presentation.session.models.FlashMessage;
import cz.kodytek.shop.presentation.session.models.FlashMessageType;
import cz.kodytek.shop.presentation.session.services.interfaces.IUserSessionService;
import cz.kodytek.shop.presentation.session.services.interfaces.messages.IFlashMessagesService;
import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AddressController {

    @Inject
    private IFlashMessagesService flashMessagesService;

    @Inject
    private IRequestUtils requestUtils;

    @Inject
    private IUserSessionService userSessionService;

    @Inject
    private IAddressService addressService;

    @Inject
    private JMSService jmsService;

    private IAddressWithId address = null;

    public void create(IAddress address) {
        jmsService.sendMessage(requestUtils.getIp() + " attempting to create a address.");
        if(addressService.create(userSessionService.getCurrentUser().getId(), address) != null)
            requestUtils.redirect("/pages/user/account.xhtml", new FlashMessage("Address successfully added.", FlashMessageType.success));
        else
            flashMessagesService.add(new FlashMessage("There was a unknown error, sorry.", FlashMessageType.alert));
    }

    public void delete(IAddressWithId address) {
        jmsService.sendMessage(requestUtils.getIp() + " deleting an address, " + address.getId() + ".");

        if(addressService.delete(userSessionService.getCurrentUser().getId(), address.getId()))
            requestUtils.redirect("/pages/user/account.xhtml", new FlashMessage("Address deleted successfully.", FlashMessageType.success));
        else
            flashMessagesService.add(new FlashMessage("Deletion failed. Please delete all associated companies with this address before deleting it again.", FlashMessageType.alert));
    }

    public void edit(IAddressWithId address) {
        jmsService.sendMessage(requestUtils.getIp() + " attempting to edit a address, " + address.getId() + ".");

        if(addressService.edit(userSessionService.getCurrentUser().getId(), address))
            requestUtils.redirect("/pages/user/account.xhtml", new FlashMessage("Address was edited successfully.", FlashMessageType.success));
        else
            flashMessagesService.add(new FlashMessage("There was a unknown error, sorry.", FlashMessageType.alert));
    }

    public IAddressWithId getAddress(long id) {
        if(address == null)
            address = addressService.get(userSessionService.getCurrentUser().getId(), id);
        return address;
    }

}
