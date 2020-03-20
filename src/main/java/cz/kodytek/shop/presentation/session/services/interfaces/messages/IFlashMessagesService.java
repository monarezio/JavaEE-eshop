package cz.kodytek.shop.presentation.session.services.interfaces.messages;

import cz.kodytek.shop.presentation.session.models.interfaces.IFlashMessage;

import java.util.List;

public interface IFlashMessagesService {

    void add(IFlashMessage message);

    void clear();

    List<IFlashMessage> getAllMessages();

    boolean isEmpty();
}
