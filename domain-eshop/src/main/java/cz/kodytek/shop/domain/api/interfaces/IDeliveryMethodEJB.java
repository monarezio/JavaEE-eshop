package cz.kodytek.shop.domain.api.interfaces;

import cz.kodytek.shop.domain.api.models.Method;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface IDeliveryMethodEJB {

    List<Method> getAll();

}
