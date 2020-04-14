package cz.kodytek.shop.api.ejb;

import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.domain.api.interfaces.IGoodsEJB;
import cz.kodytek.shop.domain.api.models.Good;
import cz.kodytek.shop.domain.api.models.GoodsPage;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;
import cz.kodytek.shop.domain.services.interfaces.goods.IGoodsService;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.stream.Collectors;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class GoodsEJB implements IGoodsEJB, Serializable {

    private static final int PER_PAGE = 20;

    @Inject
    private IGoodsService goodsService;

    @Override
    public GoodsPage get(String search, int page) {
        IEntityPage<? extends IGood> gp = goodsService.getGoods(search, PER_PAGE, page);
        return new GoodsPage(page, gp.getPagesCount(), gp.getAll().stream().map(i ->
                new Good(i.getId(), i.getTitle(), i.getDescription(), i.getCost(), i.getAmount())).collect(Collectors.toList()));
    }

    @Override
    public Good get(long id) {
        IGood g = goodsService.get(id);
        if (g == null)
            return null;
        return new Good(g.getId(), g.getTitle(), g.getDescription(), g.getCost(), g.getAmount());
    }
}
