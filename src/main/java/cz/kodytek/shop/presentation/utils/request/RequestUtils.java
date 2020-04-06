package cz.kodytek.shop.presentation.utils.request;

import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;
import cz.kodytek.shop.presentation.session.models.FlashMessage;
import cz.kodytek.shop.presentation.session.services.interfaces.messages.IFlashMessagesService;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

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
    public boolean hasParam(String key) {
        return facesContext.getExternalContext().getRequestParameterMap().containsKey(key);
    }

    @Override
    public void stopLifecycle() {
        facesContext.responseComplete();
    }

    @Override
    public Collection<Part> getAllParts(Part part) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(part == null)
            return Collections.emptyList();
        return request.getParts().stream().filter(p -> part.getName().equals(p.getName())).collect(Collectors.toList());
    }

    @Override
    public Collection<InputStream> getAllPartsAsInputStream(Part part) throws ServletException, IOException {
        ArrayList<InputStream> al = new ArrayList<>();

        for(Part p : getAllParts(part))
            al.add(p.getInputStream());

        return al;
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
