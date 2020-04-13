package cz.kodytek.shop.presentation.controllers;

import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.domain.services.interfaces.goods.IGoodsService;
import cz.kodytek.shop.presentation.session.services.interfaces.ICartService;
import org.javamoney.moneta.Money;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class CartController {

    @Inject
    private IGoodsService goodsService;

    @Inject
    private ICartService cartService;

    private List<IGood> goods = null;

    public List<IGood> getGoods() {
        if(goods == null)
            goods = goodsService.getGoodsForIds(cartService.getCart().keySet());
        return goods;
    }

    public Money getTotalPrice() {
        if(goods != null)
            return goods.stream().map(i -> i.getCost().multiply(cartService.getUnitCount(i.getId()))).reduce((i, j) -> i.add(j)).get();
        return Money.of(0, "CZK");
    }

}
