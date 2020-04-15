package cz.kodytek.shop.presentation.session.services;

import cz.kodytek.shop.jms.JMSService;
import cz.kodytek.shop.presentation.controllers.CartController;
import cz.kodytek.shop.presentation.session.services.interfaces.ICartService;
import cz.kodytek.shop.presentation.session.services.interfaces.IUserSessionService;
import cz.kodytek.shop.presentation.utils.request.interfaces.IRequestUtils;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;

@Named
@SessionScoped
public class CartService implements Serializable, ICartService {

    @Inject
    private CartController cartController;

    @Inject
    private JMSService jmsService;

    @Inject
    private IRequestUtils requestUtils;

    private HashMap<Long, Integer> cart = new HashMap<>();

    @Override
    public void add(long goodId) {
        cart.putIfAbsent(goodId, 0);
        cart.put(goodId, cart.get(goodId) + 1);
        jmsService.sendMessage(requestUtils.getIp() + " added good, with id" + goodId + ", to the cart.");
    }

    @Override
    public void remove(long goodId) {
        if(cart.containsKey(goodId)) {
            if(cart.get(goodId) == 1)
                cart.remove(goodId);
            else
                cart.put(goodId, cart.get(goodId) - 1);

            jmsService.sendMessage(requestUtils.getIp() + " removed good, with id" + goodId + ", from the cart.");
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
    public boolean isValid() {
        return cartController.getGoods().stream().allMatch(g -> g.getAmount() >= getUnitCount(g.getId()));
    }
}
