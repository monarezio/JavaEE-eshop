package cz.kodytek.shop.presentation.helpers.interfaces;

import cz.kodytek.shop.presentation.session.models.FlashMessageType;

import javax.faces.application.FacesMessage;

public interface IFlashMessageHelper {

    String getStyleClass(FlashMessageType type);

}
