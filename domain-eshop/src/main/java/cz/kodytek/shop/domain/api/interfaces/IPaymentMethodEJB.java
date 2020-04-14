package cz.kodytek.shop.domain.api.interfaces;

import cz.kodytek.shop.domain.api.models.Method;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface IPaymentMethodEJB {

    List<Method> getAll();

}
