package cz.kodytek.shop.presentation.controllers;

import cz.kodytek.shop.data.entities.interfaces.goods.cateogry.ICategory;
import cz.kodytek.shop.domain.services.interfaces.categories.ICategoryService;
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
    private IRequestUtils requestUtils;

    private ICategory category;

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

    public void print(String str) {
        System.out.println("Size: " + str);
    }

}
