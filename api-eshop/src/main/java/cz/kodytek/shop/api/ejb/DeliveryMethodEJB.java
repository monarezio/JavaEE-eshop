package cz.kodytek.shop.api.ejb;

import cz.kodytek.shop.domain.api.interfaces.IDeliveryMethodEJB;
import cz.kodytek.shop.domain.api.models.Method;
import cz.kodytek.shop.domain.services.interfaces.invoices.IDeliveryMethodService;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DeliveryMethodEJB implements IDeliveryMethodEJB, Serializable {

    @Inject
    private IDeliveryMethodService deliveryMethodService;

    @Override
    public List<Method> getAll() {
        return deliveryMethodService.getAll().stream().map(i -> new Method(i.getId(), i.getName(), i.getCost())).collect(Collectors.toList());
    }
}
