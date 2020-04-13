package cz.kodytek.shop.domain.models.goods;

import cz.kodytek.shop.data.entities.Good;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;

import java.util.List;

public class GoodsPage implements IEntityPage<Good> {

    private int pageCount;
    private int currentPage;
    private List<Good> goods;

    public GoodsPage(int currentPage, int pageCount, List<Good> goods) {
        this.pageCount = pageCount;
        this.currentPage = currentPage;
        this.goods = goods;
    }

    @Override
    public List<Good> getAll() {
        return goods;
    }

    @Override
    public int getCurrentPage() {
        return 0;
    }

    @Override
    public int getPagesCount() {
        return 0;
    }

}
