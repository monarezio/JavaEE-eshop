package cz.kodytek.shop.domain.models.categories;

import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.data.entities.interfaces.goods.cateogry.ICategory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class NewCategory implements ICategory {

    @NotNull(message = "Title cannot be empty.")
    @Size(min = 4, message = "Title must contain at least 4 characters.")
    private String title;

    private String description;

    @NotNull(message = "Order cannot be empty.")
    private Integer order;

    private long id;

    public NewCategory(long id) {
        this.id = id;
    }

    public NewCategory() {
    }

    @Override
    public Integer getOrder() {
        return order;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public List<IGood> getGoods() {
        return new ArrayList<>();
    }

    @Override
    public long getId() {
        return id;
    }
}
