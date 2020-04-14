package cz.kodytek.shop.domain.api.interfaces;

import cz.kodytek.shop.domain.api.models.Good;
import cz.kodytek.shop.domain.api.models.GoodsPage;

import javax.ejb.Remote;

@Remote
public interface IGoodsEJB {

    GoodsPage get(String search, int page);

    Good get(long id);

}
