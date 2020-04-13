package cz.kodytek.shop.presentation.helpers;

import cz.kodytek.shop.presentation.helpers.interfaces.IFlashMessageHelper;
import cz.kodytek.shop.presentation.session.models.FlashMessageType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class FlashMessagesHelper implements IFlashMessageHelper {
    @Override
    public String getStyleClass(FlashMessageType type) {
        if (FlashMessageType.alert.equals(type)) {
            return "alert alert-danger";
        } else if (FlashMessageType.success.equals(type)) {
            return "alert alert-success";
        }
        return "alert";
    }
}
