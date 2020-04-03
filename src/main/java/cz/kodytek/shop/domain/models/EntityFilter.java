package cz.kodytek.shop.domain.models;

import cz.kodytek.shop.domain.models.interfaces.IEntityFilter;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class EntityFilter implements IEntityFilter {

    private int page;
    private String search;

    public EntityFilter(String search) {
        this.search = search;
    }

    public EntityFilter() {
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public String getSearchFilter() {
        return search;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSearchFilter(String search) {
        this.search = search;
    }
}
