package cz.kodytek.shop.presentation.controllers.admin;

import cz.kodytek.shop.domain.models.goods.NewGoods;
import cz.kodytek.shop.presentation.session.models.FlashMessage;
import cz.kodytek.shop.presentation.session.models.FlashMessageType;
import cz.kodytek.shop.presentation.session.models.interfaces.IFlashMessage;
import cz.kodytek.shop.presentation.session.services.interfaces.messages.IFlashMessagesService;
import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

@Named
@RequestScoped
public class GoodsManagementController {

    @Inject
    private IRequestUtils requestUtils;

    @Inject
    private IFlashMessagesService flashMessagesService;

    public void create(NewGoods goods) {
        try {
            for(Part p : requestUtils.getAllParts(goods.getFiles())) {
                String fileName = p.getSubmittedFileName();
                InputStream fileContent = p.getInputStream();

                flashMessagesService.add(new FlashMessage(fileName, FlashMessageType.success));

                System.out.println(fileName);
            }

            requestUtils.redirect("/pages/admin/index.xhtml");
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
