package cz.kodytek.shop.presentation.session.models.interfaces;

import cz.kodytek.shop.presentation.session.models.FlashMessageType;

public interface IFlashMessage {

    String getContent();

    FlashMessageType getType();

}
