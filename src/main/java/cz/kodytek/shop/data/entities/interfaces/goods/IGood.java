package cz.kodytek.shop.data.entities.interfaces.goods;

import cz.kodytek.shop.data.entities.interfaces.IEntityId;
import cz.kodytek.shop.data.entities.interfaces.goods.cateogry.ICategory;
import cz.kodytek.shop.data.entities.interfaces.reousrce.IResource;
import org.javamoney.moneta.Money;

import java.util.List;

public interface IGood extends IEntityId {

    String getTitle();

    List<? extends IResource> getImageNames();

    String getDescription();

    Integer getAmount();

    Money getCost();

    ICategory getCategory();

}
