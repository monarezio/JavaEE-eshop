package cz.kodytek.shop.api.ejb;

import cz.kodytek.shop.domain.api.interfaces.IPaymentMethodEJB;
import cz.kodytek.shop.domain.api.models.Method;
import cz.kodytek.shop.domain.services.interfaces.invoices.IPaymentMethodService;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@TransactionManagement(value= TransactionManagementType.BEAN)
public class PaymentMethodEJB implements IPaymentMethodEJB, Serializable {

    @Inject
    private IPaymentMethodService paymentMethodService;

    @Override
    public List<Method> getAll() {
        return paymentMethodService.getAll().stream().map(i -> new Method(i.getId(), i.getName(), i.getCost())).collect(Collectors.toList());
    }
}
