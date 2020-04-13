package cz.kodytek.shop.presentation.helpers.interfaces;

import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import org.javamoney.moneta.Money;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

public interface IGoodsHelper {

    String parsePrice(IGood good);

    String parsePrice(Money money);

    String parseNullablePrice(Money money);

    String parseDescription(IGood good);

}
