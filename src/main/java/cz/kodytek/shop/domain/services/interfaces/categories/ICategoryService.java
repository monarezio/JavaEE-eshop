package cz.kodytek.shop.domain.services.interfaces.categories;

import cz.kodytek.shop.data.entities.interfaces.goods.cateogry.ICategory;

import java.util.List;

public interface ICategoryService {

    List<ICategory> getAll();

    ICategory get(long id);

    boolean edit(ICategory category);

    boolean create(ICategory category);

    void delete(ICategory category);

}
