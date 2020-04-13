package cz.kodytek.shop.presentation.api.models;

import java.util.List;

public class InvoicesPage {

    private int page;
    private int pageCount;
    private List<Invoice> invoices;

    public InvoicesPage() {
    }

    public InvoicesPage(int page, int pageCount, List<Invoice> goods) {
        this.page = page;
        this.pageCount = pageCount;
        this.invoices = goods;
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

    public List<Invoice> getGoods() {
        return invoices;
    }

    public void setGoods(List<Invoice> invoices) {
        this.invoices = invoices;
    }

}
