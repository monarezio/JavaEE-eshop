package cz.kodytek.shop.domain.services.categories;

import cz.kodytek.shop.data.connection.HibernateSessionFactory;
import cz.kodytek.shop.data.entities.Category;
import cz.kodytek.shop.data.entities.Category_;
import cz.kodytek.shop.data.entities.interfaces.goods.cateogry.ICategory;
import cz.kodytek.shop.domain.models.categories.NewCategory;
import cz.kodytek.shop.domain.services.interfaces.categories.ICategoryService;
import org.hibernate.criterion.Order;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class CategoryService implements ICategoryService {

    @Inject
    private HibernateSessionFactory sessionFactory;

    @Override
    public List<ICategory> getAll() {
        ArrayList<ICategory> category = new ArrayList<>();

        sessionFactory.createSession(s -> {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Category> cq = cb.createQuery(Category.class);
            Root<Category> root = cq.from(Category.class);

            cq.orderBy(cb.asc(root.get(Category_.order)));

            category.addAll(s.createQuery(cq).list());
        });

        return category;
    }

    @Override
    public ICategory get(long id) {
        AtomicReference<ICategory> result = new AtomicReference<>();

        try {
            sessionFactory.createSession(s -> {
                Category c = s.get(Category.class, id);

                NewCategory nc = new NewCategory(id);
                nc.setTitle(c.getTitle());
                nc.setDescription(c.getDescription());
                nc.setOrder(c.getOrder());

                result.set(nc);
            });
        } catch (PersistenceException e) {
            return null;
        }

        return result.get();
    }

    @Override
    public boolean edit(ICategory category) {

        try {
            sessionFactory.createSession(s -> {

                System.out.println("CATEGORY: " + category.getId());

                Category c = s.get(Category.class, category.getId());

                c.setDescription(category.getDescription());
                c.setOrder(category.getOrder());
                c.setTitle(category.getTitle());

                s.save(c);
            });
        } catch (PersistenceException e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean create(ICategory category) {

        try {
        sessionFactory.createSession(s -> {
            Category c = new Category();

            c.setTitle(category.getTitle());
            c.setDescription(category.getDescription());
            c.setOrder(category.getOrder());

            s.save(c);
        });
        } catch (PersistenceException e) {
            return false;
        }

        return true;
    }

    @Override
    public void delete(ICategory category) {
        sessionFactory.createSession(s -> {
            s.delete(s.get(Category.class, category.getId()));
        });
    }
}
