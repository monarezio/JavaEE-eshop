package cz.kodytek.shop.presentation.controllers.admin;

import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.data.entities.interfaces.goods.cateogry.ICategory;
import cz.kodytek.shop.data.entities.interfaces.reousrce.IResource;
import cz.kodytek.shop.domain.exceptions.InvalidFileTypeException;
import cz.kodytek.shop.domain.models.EntityFilter;
import cz.kodytek.shop.domain.models.goods.GoodsPage;
import cz.kodytek.shop.domain.models.goods.ImageResource;
import cz.kodytek.shop.domain.models.goods.NewGoods;
import cz.kodytek.shop.domain.models.interfaces.IEntityFilter;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;
import cz.kodytek.shop.domain.services.interfaces.goods.IGoodsService;
import cz.kodytek.shop.presentation.session.models.FlashMessage;
import cz.kodytek.shop.presentation.session.models.FlashMessageType;
import cz.kodytek.shop.presentation.session.models.interfaces.IFlashMessage;
import cz.kodytek.shop.presentation.session.services.interfaces.messages.IFlashMessagesService;
import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class GoodsManagementController {

    @Inject
    private IRequestUtils requestUtils;

    @Inject
    private IFlashMessagesService flashMessagesService;

    @Inject
    private IGoodsService goodsService;

    @Inject
    private CategoryManagementController categoryManagementController;

    private IEntityPage<? extends IGood> cachedPage;

    private IEntityFilter entityFilter;

    @Resource(lookup="java:app/AppName")
    private String applicationName;

    private NewGoods newGoods;

    public void create(NewGoods goods) {
        try {
            if (goodsService.create(goods, requestUtils.getAllPartsAsInputStream(goods.getFiles()), goods.getCategoryId()))
                requestUtils.redirect("/pages/admin/goods/index.xhtml", new FlashMessage("Good successfully added.", FlashMessageType.success));
            else
                flashMessagesService.add(new FlashMessage("Unknown error.", FlashMessageType.alert));
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        } catch (InvalidFileTypeException e) {
            flashMessagesService.add(new FlashMessage("File isn't a valid image.", FlashMessageType.alert));
        }
    }

    public IEntityPage<? extends IGood> getGoods(String search, String page) {
        if (cachedPage == null)
            cachedPage = goodsService.getGoods(search, 20, Integer.parseInt(page));
        return cachedPage;
    }


    public void search(IEntityFilter filter) {
        requestUtils.redirect("/pages/admin/goods/index.xhtml?search=" + filter.getSearchFilter());
    }

    public void delete(IGood good) {
        goodsService.delete(good);
        requestUtils.redirect("/pages/admin/goods/index.xhtml", new FlashMessage("Good deleted successfully.", FlashMessageType.success));
    }

    public void deleteResource(long goodsId, long id) {
        goodsService.deleteImage(id);
        requestUtils.redirect("/pages/admin/goods/edit.xhtml?id=" + goodsId, new FlashMessage("Image deleted.", FlashMessageType.success));
    }

    public void edit(NewGoods good) {
        try {
            if(goodsService.edit(good, requestUtils.getAllPartsAsInputStream(good.getFiles()), good.getCategoryId())) {
                requestUtils.redirect("/pages/admin/goods/index.xhtml", new FlashMessage("Good edited.", FlashMessageType.success));
            } else {
                flashMessagesService.add(new FlashMessage("File is not a image.", FlashMessageType.alert));
            }
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IEntityFilter getFilter() {
        if (entityFilter == null)
            entityFilter = new EntityFilter(requestUtils.hasParam("search") ? requestUtils.getParam("search") : "");
        return entityFilter;
    }

    public NewGoods getGood(long id) {
        if (newGoods == null) {
            newGoods = new NewGoods(id);
            IGood g = goodsService.get(id);
            newGoods.setCategoryId(g.getCategory().getId());
            newGoods.setCategory(g.getCategory());

            newGoods.setTitle(g.getTitle());
            newGoods.setAmount(g.getAmount());
            newGoods.setDescription(g.getDescription());
            newGoods.setCost(g.getCost());
            newGoods.setImagePaths(
                    g.getImageNames().stream().sorted((o1, o2) -> (int) (o1.getId() - o2.getId())).map(i -> new ImageResource(i.getId(), i.getPath())).collect(Collectors.toList())
            );
        }

        return newGoods;
    }

    public String getApplicationName() {
        return applicationName;
    }
}
