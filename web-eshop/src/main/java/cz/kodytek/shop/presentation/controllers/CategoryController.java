package cz.kodytek.shop.presentation.controllers;

import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.data.entities.interfaces.goods.cateogry.ICategory;
import cz.kodytek.shop.domain.services.interfaces.categories.ICategoryService;
import cz.kodytek.shop.domain.services.interfaces.goods.IGoodsService;
import cz.kodytek.shop.presentation.session.services.interfaces.ICartService;
import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class CategoryController {

    @Inject
    private ICategoryService categoryService;

    @Inject
    private IGoodsService goodsService;

    @Inject
    private IRequestUtils requestUtils;

    @Inject
    private ICartService cartService;

    private ICategory category;

    private IGood good;

    public ICategory getCategory() {
        if (!requestUtils.hasParam("category"))
            requestUtils.redirect("/");
        else {
            if (category == null)
                category = categoryService.getWithGoods(Long.parseLong(requestUtils.getParam("category")));
            if(category == null)
                requestUtils.redirect("/");
            return category;
        }

        return null; //This should not happen
    }

    public IGood getGood() {
        if(!requestUtils.hasParam("id"))
            requestUtils.redirect("/");
        else {
            if(good == null)
                good = goodsService.get(Long.parseLong(requestUtils.getParam("id")));
            if(good == null)
                requestUtils.redirect("/");
            return good;
        }

        return null;
    }

    public void addGoodToCart(IGood good) {
        cartService.add(good.getId());
        requestUtils.redirect("/pages/cart/added.xhtml");
    }

}
