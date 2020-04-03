package cz.kodytek.shop.presentation.utils.request;

import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;
import cz.kodytek.shop.presentation.session.models.FlashMessage;
import cz.kodytek.shop.presentation.session.services.interfaces.messages.IFlashMessagesService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Syntax sugar
 */
@RequestScoped
public class RequestUtils implements IRequestUtils {

    private FacesContext facesContext =  FacesContext.getCurrentInstance();
    private ExternalContext externalContext = facesContext.getExternalContext();
    private HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

    @Inject()
    private IFlashMessagesService flashMessagesService;

    @Override
    public void redirect(String url) {
        redirect(url, null);
    }

    @Override
    public void redirect(String url, FlashMessage message) {
        try {
            if (message != null)
                flashMessagesService.add(message);
            externalContext.redirect(getAppRootUrl() + url);
        } catch (IOException e) {
            e.printStackTrace(); // Just crash, this should not happen
        }
        stopLifecycle();
    }

    @Override
    public String getCurrentJSFPageId() {
        return facesContext.getViewRoot().getViewId();
    }

    @Override
    public String getParam(String key) {
        return facesContext.getExternalContext().getRequestParameterMap().get(key);
    }

    @Override
    public void stopLifecycle() {
        facesContext.responseComplete();
    }

    private String getAppRootUrl() {
        String url = null;
        try {
            url = new URL(request.getScheme(),
                    request.getServerName(),
                    request.getServerPort(),
                    request.getContextPath()).toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
