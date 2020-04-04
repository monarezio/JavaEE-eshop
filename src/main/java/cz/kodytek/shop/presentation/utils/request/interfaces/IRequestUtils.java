package cz.kodytek.shop.presentation.utils.request.interfaces;

import cz.kodytek.shop.presentation.session.models.FlashMessage;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Syntax sugar
 */
public interface IRequestUtils {

    void redirect(String url);

    void redirect(String url, FlashMessage flashMessage);

    String getCurrentJSFPageId();

    String getParam(String key);

    boolean hasParam(String key);

    void stopLifecycle();

    Collection<Part> getAllParts(Part part) throws ServletException, IOException;

}
