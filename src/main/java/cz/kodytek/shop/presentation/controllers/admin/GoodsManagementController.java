package cz.kodytek.shop.presentation.controllers.admin;

import cz.kodytek.shop.domain.exceptions.InvalidFileTypeException;
import cz.kodytek.shop.domain.models.goods.NewGoods;
import cz.kodytek.shop.domain.services.interfaces.goods.IGoodsService;
import cz.kodytek.shop.presentation.session.models.FlashMessage;
import cz.kodytek.shop.presentation.session.models.FlashMessageType;
import cz.kodytek.shop.presentation.session.models.interfaces.IFlashMessage;
import cz.kodytek.shop.presentation.session.services.interfaces.messages.IFlashMessagesService;
import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.Flash;
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

    @Inject
    private IGoodsService goodsService;

    public void create(NewGoods goods) {
        try {
            if(goodsService.create(goods, requestUtils.getAllParts(goods.getFiles()), goods.getCategoryId()))
                requestUtils.redirect("/pages/admin/index.xhtml", new FlashMessage("Good successfully added.", FlashMessageType.success));
            else
                flashMessagesService.add(new FlashMessage("Unknown error.", FlashMessageType.alert));
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        } catch (InvalidFileTypeException e) {
            flashMessagesService.add(new FlashMessage("File isn't a valid image.", FlashMessageType.alert));
        }
    }

}
