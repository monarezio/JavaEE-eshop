package cz.kodytek.shop.presentation.helpers;

import com.github.rjeschke.txtmark.Processor;
import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.presentation.helpers.interfaces.IGoodsHelper;
import org.javamoney.moneta.Money;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class GoodsHelper implements IGoodsHelper {
    @Override
    public String parsePrice(IGood good) {
        return String.format("%.2f", good.getCost().getNumber().doubleValue());
    }

    @Override
    public String parseDescription(IGood good) {
        return Processor.process(good.getDescription());
    }

    @Override
    public String parsePrice(Money money) {
        return String.format("%.2f", money.getNumber().doubleValue());
    }

    @Override
    public String parseNullablePrice(Money money) {
        if (money == null)
            return "";
        return parsePrice(money);
    }
}
