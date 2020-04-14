package cz.kodytek.shop.domain.services.invoices;

import cz.kodytek.shop.data.connection.interfaces.IHibernateSessionFactory;
import cz.kodytek.shop.data.entities.Address;
import cz.kodytek.shop.data.entities.Company;
import cz.kodytek.shop.data.entities.Good;
import cz.kodytek.shop.data.entities.User;
import cz.kodytek.shop.data.entities.interfaces.invoice.IInvoice;
import cz.kodytek.shop.data.entities.invoice.*;
import cz.kodytek.shop.data.entities.invoice.method.DeliveryMethod;
import cz.kodytek.shop.data.entities.invoice.method.InvoiceDeliveryMethod;
import cz.kodytek.shop.data.entities.invoice.method.InvoicePaymentMethod;
import cz.kodytek.shop.data.entities.invoice.method.PaymentMethod;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;
import cz.kodytek.shop.domain.models.invoices.InvoicesPage;
import cz.kodytek.shop.domain.models.invoices.NewInvoice;
import cz.kodytek.shop.domain.services.interfaces.goods.IGoodsService;
import cz.kodytek.shop.domain.services.interfaces.invoices.IInvoicesService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.query.Query;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@ApplicationScoped
public class InvoicesService implements IInvoicesService {

    @Inject
    private IHibernateSessionFactory sessionFactory;

    @Inject
    private IGoodsService goodsService;

    @Override
    public IInvoice addInvoice(NewInvoice invoice) {
        AtomicReference<Invoice> result = new AtomicReference<>();

        try {
            sessionFactory.createSession(s -> {
                Invoice i = new Invoice();

                List<Good> gs = goodsService.getGoodsForIds(invoice.getCart().keySet()).stream().map(j -> (Good) j).collect(Collectors.toList());
                ArrayList<InvoiceGood> igs = new ArrayList<>();

                gs.forEach(g -> {
                    InvoiceGood ig = new InvoiceGood();
                    ig.setOriginalId(g.getId());
                    ig.setAmount(invoice.getCart().get(g.getId()));
                    ig.setCost(g.getCost());
                    ig.setTitle(g.getTitle());

                    g.setAmount(g.getAmount() - ig.getAmount());
                    s.update(g);

                    s.save(ig);
                    igs.add(ig);
                });
                i.setGoods(new HashSet<>(igs));

                InvoiceDeliveryMethod idm = new InvoiceDeliveryMethod();
                DeliveryMethod dm = s.get(DeliveryMethod.class, invoice.getDeliveryMethodId());

                System.out.println(invoice.getDeliveryMethodId());
                System.out.println(invoice.getPaymentMethodId());

                idm.setCost(dm.getCost());
                idm.setName(dm.getName());
                idm.setOriginalId(dm.getId());
                s.save(idm);
                i.setDeliveryMethod(idm);

                InvoicePaymentMethod ipm = new InvoicePaymentMethod();
                PaymentMethod pm = s.get(PaymentMethod.class, invoice.getPaymentMethodId());
                ipm.setCost(pm.getCost());
                ipm.setName(pm.getName());
                ipm.setOriginalId(pm.getId());
                s.save(ipm);
                i.setPaymentMethod(ipm);

                if (invoice.isCompanyBuying()) {
                    InvoiceCompany ic = new InvoiceCompany();
                    InvoiceAddress ia = new InvoiceAddress();
                    if (invoice.getCompanyId() == null) {
                        ic.setName(invoice.getCompany().getName());
                        ic.setIdentificationNumber(invoice.getCompany().getIdentificationNumber());
                        ic.setTaxIdentificationNumber(invoice.getCompany().getTaxIdentificationNumber());

                        ia.setCity(invoice.getCompany().getAddress().getCity());
                        ia.setStreet(invoice.getCompany().getAddress().getStreet());
                        ia.setPostCode(invoice.getCompany().getAddress().getPostCode());
                        s.save(ia);

                        ic.setAddress(ia);
                    } else {
                        Company c = s.get(Company.class, invoice.getCompanyId());
                        ic.setName(c.getName());
                        ic.setTaxIdentificationNumber(c.getTaxIdentificationNumber());
                        ic.setIdentificationNumber(c.getIdentificationNumber());

                        Address a = (Address) c.getAddress();
                        ia.setPostCode(a.getPostCode());
                        ia.setCity(a.getCity());
                        ia.setStreet(a.getStreet());
                        s.save(ia);

                        ic.setAddress(ia);
                    }
                    s.save(ic);
                    i.setCompany(ic);
                }


                InvoiceAddress ia = new InvoiceAddress();
                if (invoice.getAddressId() == null) {
                    ia.setStreet(invoice.getAddress().getStreet());
                    ia.setCity(invoice.getAddress().getCity());
                    ia.setPostCode(invoice.getAddress().getPostCode());
                } else {
                    Address a = s.get(Address.class, invoice.getAddressId());
                    ia.setPostCode(a.getPostCode());
                    ia.setCity(a.getCity());
                    ia.setStreet(a.getStreet());
                }
                s.save(ia);
                i.setDeliveryAddress(ia);

                i.setFullName(invoice.getFullName());
                i.setEmail(invoice.getEmail());
                i.setPhone(invoice.getPhone());
                i.setIssued(LocalDate.now());

                LocalDate now = LocalDate.now();

                String invoiceNumber = (String.valueOf(now.getYear()).substring(2, 4) + now.format(DateTimeFormatter.ofPattern("MMdd")) + StringUtils.leftPad(getTodayInvoiceCount() + "", 4, "0"));
                i.setInvoiceNumber(invoiceNumber);
                i.setVariableSymbol(invoiceNumber); //TODO: Maybe change that?

                s.save(i);
                result.set(i);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result.get();
    }

    @Override
    public IInvoice addInvoice(NewInvoice invoice, long userId) {
        AtomicReference<Invoice> result = new AtomicReference<>();

        sessionFactory.createSession(s -> {
            User u = s.get(User.class, userId);
            Invoice i = (Invoice) addInvoice(invoice);
            u.addInvoice(i);
            result.set(i);
            s.save(u);
        });

        return result.get();
    }

    @Override
    public IInvoice get(long id) {
        AtomicReference<Invoice> result = new AtomicReference<>();

        try {
            sessionFactory.createSession(s -> {
                CriteriaBuilder cb = s.getCriteriaBuilder();
                CriteriaQuery<Invoice> cq = cb.createQuery(Invoice.class);
                Root<Invoice> root = cq.from(Invoice.class);

                Fetch<Invoice, InvoiceGood> invoiceInvoiceGoodFetch = root.fetch(Invoice_.goods, JoinType.LEFT);
                Fetch<Invoice, InvoicePaymentMethod> paymentMethodFetch = root.fetch(Invoice_.paymentMethod, JoinType.LEFT);
                Fetch<Invoice, InvoiceDeliveryMethod> deliveryMethodFetch = root.fetch(Invoice_.deliveryMethod, JoinType.LEFT);
                Fetch<Invoice, InvoiceAddress> invoiceAddressFetch = root.fetch(Invoice_.deliveryAddress, JoinType.LEFT);
                Fetch<Invoice, InvoiceCompany> invoiceCompanyFetch = root.fetch(Invoice_.company, JoinType.LEFT);
                Fetch<InvoiceCompany, InvoiceAddress> companyInvoiceAddressFetch = invoiceCompanyFetch.fetch(InvoiceCompany_.address, JoinType.LEFT);

                cq.where(
                        cb.equal(root.get(Invoice_.id), id)
                );

                result.set(s.createQuery(cq).getSingleResult());
            });
        } catch (PersistenceException e) {
            return null;
        }

        return result.get();
    }

    @Override
    public IEntityPage<IInvoice> getInvoices(String search, int page, int perPage) {
        AtomicReference<IEntityPage<IInvoice>> result = new AtomicReference<>();

        sessionFactory.createSession(s -> {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Invoice> cq = cb.createQuery(Invoice.class);
            Root<Invoice> root = cq.from(Invoice.class);

            Fetch<Invoice, InvoiceCompany> invoiceCompanyFetch = root.fetch(Invoice_.company, JoinType.LEFT);
            Fetch<InvoiceCompany, InvoiceAddress> invoiceCompanyInvoiceAddressFetch = invoiceCompanyFetch.fetch(InvoiceCompany_.address, JoinType.LEFT);
            Fetch<Invoice, InvoiceAddress> invoiceAddressFetch = root.fetch(Invoice_.deliveryAddress, JoinType.LEFT);
            Fetch<Invoice, InvoiceGood> invoiceInvoiceGoodFetch = root.fetch(Invoice_.goods, JoinType.LEFT);
            Fetch<Invoice, InvoicePaymentMethod> paymentMethodFetch = root.fetch(Invoice_.paymentMethod, JoinType.LEFT);
            Fetch<Invoice, InvoiceDeliveryMethod> deliveryMethodFetch = root.fetch(Invoice_.deliveryMethod, JoinType.LEFT);

            cq.where(
                    cb.and(
                            cb.like(root.get(Invoice_.invoiceNumber), "%" + search + "%")
                    )
            );

            Query<Invoice> q = s.createQuery(cq);

            q.setFirstResult(page * perPage);
            q.setMaxResults(perPage);

            result.set(new InvoicesPage(q.getResultList(), page, (int) (Math.ceil(getInvoiceCount(search) / (float) perPage) - 1)));
        });


        return result.get();
    }

    private long getTodayInvoiceCount() {
        AtomicLong result = new AtomicLong();

        sessionFactory.createSession(s -> {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Invoice> root = cq.from(Invoice.class);

            cq.where(
                    cb.equal(root.get(Invoice_.issued), LocalDate.now())
            );

            cq.select(cb.count(root));
            result.set(s.createQuery(cq).getSingleResult());
        });

        return result.get();
    }

    @Override
    public IInvoice getForInvoiceNumber(String invoiceNumber) {
        AtomicReference<Invoice> result = new AtomicReference<>();

        try {
            sessionFactory.createSession(s -> {
                CriteriaBuilder cb = s.getCriteriaBuilder();
                CriteriaQuery<Invoice> cq = cb.createQuery(Invoice.class);
                Root<Invoice> root = cq.from(Invoice.class);

                Fetch<Invoice, InvoiceGood> invoiceInvoiceGoodFetch = root.fetch(Invoice_.goods, JoinType.LEFT);
                Fetch<Invoice, InvoicePaymentMethod> paymentMethodFetch = root.fetch(Invoice_.paymentMethod, JoinType.LEFT);
                Fetch<Invoice, InvoiceDeliveryMethod> deliveryMethodFetch = root.fetch(Invoice_.deliveryMethod, JoinType.LEFT);
                Fetch<Invoice, InvoiceAddress> invoiceAddressFetch = root.fetch(Invoice_.deliveryAddress, JoinType.LEFT);
                Fetch<Invoice, InvoiceCompany> invoiceCompanyFetch = root.fetch(Invoice_.company, JoinType.LEFT);
                Fetch<InvoiceCompany, InvoiceAddress> companyInvoiceAddressFetch = invoiceCompanyFetch.fetch(InvoiceCompany_.address, JoinType.LEFT);

                cq.where(
                        cb.equal(root.get(Invoice_.invoiceNumber), invoiceNumber)
                );

                result.set(s.createQuery(cq).getSingleResult());
            });
        } catch (PersistenceException e) {
            return null;
        }

        return result.get();
    }

    @Override
    public void deleteForInvoice(String invoiceNumber) {
        sessionFactory.createSession(s -> {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Invoice> cq = cb.createQuery(Invoice.class);
            Root<Invoice> root = cq.from(Invoice.class);

            Fetch<Invoice, InvoiceGood> invoiceInvoiceGoodFetch = root.fetch(Invoice_.goods, JoinType.LEFT);

            cq.where(
                    cb.equal(root.get(Invoice_.invoiceNumber), invoiceNumber)
            );

            Invoice i = s.createQuery(cq).getSingleResult();

            goodsService.getGoodsForIds(i.getGoods().stream().map(InvoiceGood::getOriginalId).collect(Collectors.toSet())).forEach(g -> {
                ((Good) g).setAmount(g.getAmount() + i.getGoods().stream().filter(j -> j.getOriginalId() == g.getId()).findFirst().get().getAmount());
                s.update(g);
            });

            s.delete(i);
        });
    }

    private long getInvoiceCount(String searchFilter) {
        AtomicLong result = new AtomicLong();

        sessionFactory.createSession(s -> {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Invoice> root = cq.from(Invoice.class);

            cq.where(
                    cb.and(
                            cb.like(root.get(Invoice_.invoiceNumber), "%" + searchFilter + "%")
                    )
            );

            cq.select(cb.count(root));
            result.set(s.createQuery(cq).getSingleResult());
        });

        return result.get();
    }

    @Override
    public void delete(Long id) {
        try {
            sessionFactory.createSession(s -> {
                s.delete(s.get(Invoice.class, id));
            });
        } catch (Exception ignore) {
            //Fade silently like a ninja...
        }
    }

    @Override
    public boolean edit(long id, String fullName, String email, String phone, boolean paid) {

        try {
            sessionFactory.createSession(s -> {
                Invoice i = s.get(Invoice.class, id);
                if(i == null)
                    throw new PersistenceException();

                i.setFullName(fullName);
                i.setEmail(email);
                i.setPhone(phone);
                if(paid && i.getDatePaid() == null)
                    i.setPaid(LocalDate.now());

                s.save(i);
            });
        } catch (PersistenceException e) {
            return false;
        }

        return true;
    }
}
