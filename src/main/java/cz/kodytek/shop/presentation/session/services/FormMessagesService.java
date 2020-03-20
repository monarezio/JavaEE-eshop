package cz.kodytek.shop.presentation.session.services;

import cz.kodytek.shop.presentation.session.services.interfaces.messages.IFormMessagesService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class FormMessagesService implements IFormMessagesService {

    private final FacesContext facesContext = FacesContext.getCurrentInstance();

    @Override
    public void addError(String message) {
        facesContext.addMessage(null, new FacesMessage(message));
    }
}
