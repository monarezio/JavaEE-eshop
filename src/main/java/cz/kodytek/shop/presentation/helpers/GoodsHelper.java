package cz.kodytek.shop.presentation.helpers;

import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.presentation.helpers.interfaces.IGoodsHelper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class GoodsHelper implements IGoodsHelper {
    @Override
    public String parsePrice(IGood good) {
        return String.valueOf(good.getCost().getNumber().doubleValue());
    }
}
