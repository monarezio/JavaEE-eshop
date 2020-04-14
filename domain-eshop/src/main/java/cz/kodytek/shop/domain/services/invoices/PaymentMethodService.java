package cz.kodytek.shop.domain.services.invoices;

import cz.kodytek.shop.data.connection.interfaces.IHibernateSessionFactory;
import cz.kodytek.shop.data.entities.interfaces.invoice.method.IPaymentMethod;
import cz.kodytek.shop.data.entities.invoice.method.PaymentMethod;
import cz.kodytek.shop.domain.services.interfaces.invoices.IPaymentMethodService;
import org.hibernate.query.Query;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PaymentMethodService implements IPaymentMethodService {

    @Inject
    private IHibernateSessionFactory hibernateSessionFactory;

    @Override
    public void add(IPaymentMethod paymentMethod) {
        hibernateSessionFactory.createSession(s -> {
            PaymentMethod pm = new PaymentMethod();
            pm.setCost(paymentMethod.getCost());
            pm.setName(paymentMethod.getName());
            s.save(pm);
        });
    }

    @Override
    public List<IPaymentMethod> getAll() {
        List<IPaymentMethod> pms = new ArrayList<>();

        hibernateSessionFactory.createSession(s -> {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<PaymentMethod> cq = cb.createQuery(PaymentMethod.class);

            Root<PaymentMethod> root = cq.from(PaymentMethod.class);

            Query<PaymentMethod> q = s.createQuery(cq);

            pms.addAll(q.getResultList());
        });

        return pms;
    }
}
