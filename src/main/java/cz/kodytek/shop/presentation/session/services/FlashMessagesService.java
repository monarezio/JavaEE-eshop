package cz.kodytek.shop.presentation.session.services;

import cz.kodytek.shop.presentation.session.models.interfaces.IFlashMessage;
import cz.kodytek.shop.presentation.session.services.interfaces.IFlashMessagesServices;
import jdk.jfr.Name;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@Named
public class FlashMessagesService implements IFlashMessagesServices, Serializable {

    private List<IFlashMessage> flashMessages = new ArrayList<>();

    @Override
    public void add(IFlashMessage message) {
        flashMessages.add(message);
    }

    @Override
    public void clear() {
        flashMessages = new ArrayList<>();
    }

    @Override
    public List<IFlashMessage> getAllMessages() {
        List<IFlashMessage> messages = flashMessages;
        clear();
        return messages;
    }

    @Override
    public boolean isEmpty() {
        return flashMessages.isEmpty();
    }
}
