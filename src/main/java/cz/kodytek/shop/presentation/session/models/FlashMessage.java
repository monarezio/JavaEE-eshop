package cz.kodytek.shop.presentation.session.models;

import cz.kodytek.shop.presentation.session.models.interfaces.IFlashMessage;

public class FlashMessage implements IFlashMessage {

    private final String content;
    private final FlashMessageType type;

    public FlashMessage(String content, FlashMessageType type) {
        this.content = content;
        this.type = type;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public FlashMessageType getType() {
        return type;
    }
}
