package cz.kodytek.shop.presentation.utils.request.interfaces;

import cz.kodytek.shop.presentation.session.models.FlashMessage;

/**
 * Syntax sugar
 */
public interface IRequestUtils {

    void redirect(String url);

    void redirect(String url, FlashMessage flashMessage);

    String getCurrentJSFPageId();

}
