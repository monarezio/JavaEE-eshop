package cz.kodytek.shop.data.entities.interfaces.goods.cateogry;

import cz.kodytek.shop.data.entities.interfaces.IEntityId;
import cz.kodytek.shop.data.entities.interfaces.goods.IGood;

import java.util.List;

public interface ICategory extends IEntityId {

    Integer getOrder();

    String getTitle();

    String getDescription();

    List<IGood> getGoods();

}
