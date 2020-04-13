package cz.kodytek.shop.presentation.session.services.interfaces;

import cz.kodytek.shop.data.entities.interfaces.goods.IGood;

import java.util.HashMap;
import java.util.List;

public interface ICartService {

    void add(long goodId);

    void remove(long goodId);

    HashMap<Long, Integer> getCart();

    int getUnitCount(Long goodId);

    int getCount();

    boolean isValid();

}
