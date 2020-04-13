package cz.kodytek.shop.presentation.api.models;

import java.util.List;

public class GoodsPage {

    private int page;
    private int pageCount;
    private List<Good> goods;

    public GoodsPage() {
    }

    public GoodsPage(int page, int pageCount, List<Good> goods) {
        this.page = page;
        this.pageCount = pageCount;
        this.goods = goods;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }
}
