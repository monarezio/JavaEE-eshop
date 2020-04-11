package cz.kodytek.shop.presentation.session.services;

import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.presentation.session.services.interfaces.ICartService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Named
@SessionScoped
public class CartService implements Serializable, ICartService {

    private HashMap<Long, Integer> cart = new HashMap<>();

    @Override
    public void add(long goodId) {
        cart.putIfAbsent(goodId, 0);
        cart.put(goodId, cart.get(goodId) + 1);
    }

    @Override
    public void remove(long goodId) {
        if(cart.containsKey(goodId)) {
            if(cart.get(goodId) == 1)
                cart.remove(goodId);
            else
                cart.put(goodId, cart.get(goodId) - 1);
        }
    }

    @Override
    public int getCount() {
        return cart.values().stream().mapToInt(i -> i).sum();
    }

    @Override
    public int getUnitCount(Long goodId) {
        return cart.getOrDefault(goodId, 0);
    }

    @Override
    public HashMap<Long, Integer> getCart() {
        return cart;
    }

    @Override
    public boolean isValid(List<IGood> goods) {
        return goods.stream().allMatch(g -> g.getAmount() > getUnitCount(g.getId()));
    }
}
