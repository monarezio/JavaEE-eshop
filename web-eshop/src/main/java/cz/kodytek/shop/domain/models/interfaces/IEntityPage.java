package cz.kodytek.shop.domain.models.interfaces;

import java.util.List;

public interface IEntityPage<E> {

    List<? extends E> getAll();

    int getCurrentPage();

    int getPagesCount();

}
