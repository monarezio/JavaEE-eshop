package cz.kodytek.shop.presentation.controllers.admin;

import cz.kodytek.shop.data.entities.interfaces.goods.cateogry.ICategory;
import cz.kodytek.shop.domain.models.interfaces.IEntityFilter;
import cz.kodytek.shop.domain.services.interfaces.categories.ICategoryService;
import cz.kodytek.shop.presentation.session.models.FlashMessage;
import cz.kodytek.shop.presentation.session.models.FlashMessageType;
import cz.kodytek.shop.presentation.session.services.interfaces.messages.IFlashMessagesService;
import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class CategoryManagementController {

    @Inject
    private IFlashMessagesService flashMessagesService;

    @Inject
    private IRequestUtils requestUtils;

    @Inject
    private ICategoryService categoryService;

    private List<ICategory> categories = null;

    private ICategory category = null;

    public List<ICategory> getAll() {
        if (categories == null)
            categories = categoryService.getAll();

        return categories;
    }

    public ICategory getCategory(long id) {
        if (category == null)
            category = categoryService.get(id);
        if (category == null)
            requestUtils.redirect("/pages/admin/categories/index.xhtml");
        
        return category;
    }

    public void delete(ICategory category) {
        categoryService.delete(category);
        requestUtils.redirect("/pages/admin/categories/index.xhtml", new FlashMessage("Category deleted successfully.", FlashMessageType.success));
    }

    public void create(ICategory category) {
        if (categoryService.create(category))
            requestUtils.redirect("/pages/admin/categories/index.xhtml", new FlashMessage("Category created successfully.", FlashMessageType.success));
        else
            flashMessagesService.add(new FlashMessage("Order is already in use.", FlashMessageType.alert));
    }

    public void edit(ICategory category) {
        if (categoryService.edit(category))
            requestUtils.redirect("/pages/admin/categories/index.xhtml", new FlashMessage("Category edited successfully.", FlashMessageType.success));
        else
            flashMessagesService.add(new FlashMessage("Order is already in use.", FlashMessageType.alert));
    }

}
