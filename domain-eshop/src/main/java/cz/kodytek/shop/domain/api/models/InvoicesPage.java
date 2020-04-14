package cz.kodytek.shop.domain.api.models;

import java.io.Serializable;
import java.util.List;

public class InvoicesPage implements Serializable {

    private int page;
    private int pageCount;
    private List<CreatedInvoice> invoices;

    public InvoicesPage() {
    }

    public InvoicesPage(int page, int pageCount, List<CreatedInvoice> goods) {
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

    public List<CreatedInvoice> getInvoices() {
        return invoices;
    }

    public void setGoods(List<CreatedInvoice> invoices) {
        this.invoices = invoices;
    }

}
