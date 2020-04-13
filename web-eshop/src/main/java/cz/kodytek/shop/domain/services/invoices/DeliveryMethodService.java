package cz.kodytek.shop.domain.services.invoices;

import cz.kodytek.shop.data.connection.interfaces.IHibernateSessionFactory;
import cz.kodytek.shop.data.entities.interfaces.invoice.method.IDeliveryMethod;
import cz.kodytek.shop.data.entities.interfaces.invoice.method.IPaymentMethod;
import cz.kodytek.shop.data.entities.invoice.method.DeliveryMethod;
import cz.kodytek.shop.data.entities.invoice.method.PaymentMethod;
import cz.kodytek.shop.domain.services.interfaces.invoices.IDeliveryMethodService;
import cz.kodytek.shop.domain.services.interfaces.invoices.IPaymentMethodService;
import org.hibernate.query.Query;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DeliveryMethodService implements IDeliveryMethodService {

    @Inject
    private IHibernateSessionFactory hibernateSessionFactory;

    @Override
    public void add(IDeliveryMethod deliveryMethod) {
        hibernateSessionFactory.createSession(s -> {
            DeliveryMethod dm = new DeliveryMethod();
            dm.setCost(deliveryMethod.getCost());
            dm.setName(deliveryMethod.getName());
            s.save(dm);
        });
    }

    @Override
    public List<IDeliveryMethod> getAll() {
        List<IDeliveryMethod> dms = new ArrayList<>();

        hibernateSessionFactory.createSession(s -> {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<DeliveryMethod> cq = cb.createQuery(DeliveryMethod.class);

            Root<DeliveryMethod> root = cq.from(DeliveryMethod.class);

            Query<DeliveryMethod> q = s.createQuery(cq);

            dms.addAll(q.getResultList());
        });

        return dms;
    }
}
