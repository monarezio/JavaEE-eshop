package cz.kodytek.shop.domain.models.users;

import cz.kodytek.shop.data.entities.interfaces.user.IUserWithRights;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;

import java.util.List;

public class UserPage implements IEntityPage<IUserWithRights> {

    private List<? extends IUserWithRights> users;
    private int currentPage;
    private int pagesCount;

    public UserPage(List<? extends IUserWithRights> users, int currentPage, int pagesCount) {
        this.users = users;
        this.currentPage = currentPage;
        this.pagesCount = pagesCount;
    }

    public List<? extends IUserWithRights> getAll() {
        return users;
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }

    @Override
    public int getPagesCount() {
        return pagesCount;
    }
}
