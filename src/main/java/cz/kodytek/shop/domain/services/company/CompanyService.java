package cz.kodytek.shop.domain.services.company;

import cz.kodytek.shop.data.connection.interfaces.IHibernateSessionFactory;
import cz.kodytek.shop.data.entities.Address;
import cz.kodytek.shop.data.entities.Company;
import cz.kodytek.shop.data.entities.User;
import cz.kodytek.shop.data.entities.interfaces.company.ICompany;
import cz.kodytek.shop.data.entities.interfaces.company.ICompanyWithId;
import cz.kodytek.shop.domain.models.company.AddressCreationType;
import cz.kodytek.shop.domain.models.interfaces.company.ICreatedCompany;
import cz.kodytek.shop.domain.services.interfaces.company.ICompanyService;
import org.hibernate.Session;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class CompanyService implements ICompanyService {

    @Inject
    private IHibernateSessionFactory hibernateSessionFactory;

    @Override
    public ICompanyWithId get(long userId, long companyId) {
        AtomicReference<Company> result = new AtomicReference<>();

        hibernateSessionFactory.createSession(s -> {
            result.set(getCompany(s, userId, companyId));
        });

        return result.get();
    }

    @Override
    public boolean edit(long userId, ICompanyWithId company) {
        AtomicBoolean result = new AtomicBoolean(false);

        try {
            hibernateSessionFactory.createSession(s -> {
                Company c = getCompany(s, userId, company.getId());
                if (c != null) {
                    c.setName(company.getName());
                    c.setAddress(company.getAddress());
                    c.setIdentificationNumber(company.getIdentificationNumber());
                    c.setTaxIdentificationNumber(company.getTaxIdentificationNumber());
                    s.save(c);
                    result.set(true);
                }
            });
        } catch (PersistenceException e) {
            e.printStackTrace();
            return false;
        }

        return result.get();
    }

    @Override
    public void delete(long userId, long companyId) {
        hibernateSessionFactory.createSession(s -> {
            Company company = getCompany(s, userId, companyId);
            s.delete(company);
        });
    }

    @Override
    public ICompanyWithId create(long userId, ICreatedCompany company) {
        AtomicReference<ICompanyWithId> result = new AtomicReference<>();

        hibernateSessionFactory.createSession(s -> {
            User user = s.get(User.class, userId);

            Company c = new Company();
            c.setTaxIdentificationNumber(company.getTaxIdentificationNumber());
            c.setIdentificationNumber(company.getIdentificationNumber());
            c.setName(company.getName());

            if (company.getAddressCreationType().equals(AddressCreationType.NEW)) {
                Address a = new Address();
                a.setCity(company.getAddress().getCity());
                a.setStreet(company.getAddress().getStreet());
                a.setPostCode(company.getAddress().getPostCode());

                user.addAddress(a);
                s.save(a);
                c.setAddress(a);
            } else {
                c.setAddress(new Address(company.getAddress().getId()));
            }


            user.addCompany(c);
            s.save(c);
            result.set(c);
        });

        return result.get();
    }

    private Company getCompany(Session s, long userId, long companyId) {
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<Company> cq = cb.createQuery(Company.class);
        Root<Company> root = cq.from(Company.class);

        Predicate predicate = cb.and(
                cb.equal(root.get("user"), new User(userId)),
                cb.equal(root.get("id"), companyId + "")
        );
        cq.where(predicate);

        return s.createQuery(cq).getSingleResult();
    }
}
