package cz.kodytek.shop.domain.services.goods;

import cz.kodytek.shop.data.connection.HibernateSessionFactory;
import cz.kodytek.shop.data.entities.Category;
import cz.kodytek.shop.data.entities.Good;
import cz.kodytek.shop.data.entities.Good_;
import cz.kodytek.shop.data.entities.Resource;
import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.domain.common.utils.BufferedImageUtil;
import cz.kodytek.shop.domain.exceptions.InvalidFileTypeException;
import cz.kodytek.shop.domain.models.goods.GoodsPage;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;
import cz.kodytek.shop.domain.services.interfaces.goods.IGoodsService;
import net.coobird.thumbnailator.Thumbnails;
import org.hibernate.query.Query;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@ApplicationScoped
public class GoodsService implements IGoodsService {

    @Inject
    private HibernateSessionFactory sessionFactory;

    @Inject
    private BufferedImageUtil bufferedImageUtil;

    @Override
    public IGood get(long id) {
        AtomicReference<IGood> result = new AtomicReference<>();

        try {
            sessionFactory.createSession(s -> {

                CriteriaBuilder cb = s.getCriteriaBuilder();
                CriteriaQuery<Good> cq = cb.createQuery(Good.class);
                Root<Good> root = cq.from(Good.class);

                Fetch<Good, Resource> fetch = root.fetch(Good_.resources, JoinType.LEFT);

                cq.where(
                        cb.equal(root.get(Good_.id), id)
                );

                Query<Good> q = s.createQuery(cq);

                result.set(q.getSingleResult());
            });
        } catch (PersistenceException e) {
            return null;
        }

        return result.get();
    }

    @Override
    public boolean create(IGood good, Collection<InputStream> files, long categoryId) throws InvalidFileTypeException {

        AtomicBoolean invalidFile = new AtomicBoolean(false);

        sessionFactory.createSession(s -> {
            Good g = new Good();
            g.setAmount(good.getAmount());
            g.setTitle(good.getTitle());
            g.setCost(good.getCost());
            g.setDescription(good.getDescription());
            g.setCategory(new Category(categoryId));

            for (InputStream is : files) {
                Resource r = new Resource();
                new File("resources/photos/goods/").mkdirs();
                try {
                    String name = UUID.randomUUID().toString();

                    File f = new File("resources/photos/goods/" + name + ".jpg");
                    File fhd = new File("resources/photos/goods/" + name + "_hd.jpg");
                    File fm = new File("resources/photos/goods/" + name + "_miniature.jpg");

                    BufferedImage originalImage = ImageIO.read(is);
                    ImageIO.write(originalImage, "jpg", f);

                    Thumbnails.of(originalImage)
                            .outputFormat("jpg")
                            .size(1920, 1080)
                            .toFile(fhd);
                    Thumbnails.of(originalImage)
                            .outputFormat("jpg")
                            .size(640, 480)
                            .toFile(fm);

                    r.setPath(f.getPath());
                    s.save(r);
                    g.addResource(r);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    invalidFile.set(true);
                }
            }

            s.save(g);
        });

        if (invalidFile.get())
            throw new InvalidFileTypeException();

        return true;
    }

    @Override
    public IEntityPage<? extends IGood> getGoods(String search, int perPage, int page) {
        AtomicReference<IEntityPage<? extends IGood>> result = new AtomicReference<>();

        try {
            sessionFactory.createSession(s -> {
                CriteriaBuilder cb = s.getCriteriaBuilder();
                CriteriaQuery<Good> cq = cb.createQuery(Good.class);
                Root<Good> root = cq.from(Good.class);

                cq.where(
                        cb.like(root.get(Good_.title), "%" + search + "%")
                );

                Query<Good> q = s.createQuery(cq);

                q.setFirstResult(page * perPage);
                q.setMaxResults(perPage);

                result.set(new GoodsPage(page, (int) (Math.ceil(getGoodsCount(search) / (float) perPage) + 1), q.getResultList()));
            });
        } catch (PersistenceException e) {
            e.printStackTrace();
            return null;
        }

        return result.get();
    }

    @Override
    public long getGoodsCount(String searchFilter) {
        AtomicLong result = new AtomicLong();

        sessionFactory.createSession(s -> {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Good> root = cq.from(Good.class);

            cq.where(
                    cb.like(root.get(Good_.title), "%" + searchFilter + "%")
            );

            cq.select(cb.count(root));
            result.set(s.createQuery(cq).getSingleResult());
        });

        return result.get();
    }

    @Override
    public boolean edit(IGood good, Collection<InputStream> files, long categoryId) {
        AtomicBoolean invalidFile = new AtomicBoolean(false);

        System.out.println("FILES: " + files.size());

        sessionFactory.createSession(s -> {
            Good g = s.get(Good.class, good.getId());
            g.setTitle(good.getTitle());
            g.setDescription(good.getDescription());
            g.setCost(good.getCost());
            g.setAmount(good.getAmount());
            g.setCategory(new Category(categoryId));

            for (InputStream is : files) {
                Resource r = new Resource();
                new File("resources/photos/goods/").mkdirs();
                try {
                    String name = UUID.randomUUID().toString();

                    System.out.println("Creating file: " + name);

                    File f = new File("resources/photos/goods/" + name + ".jpg");
                    File fhd = new File("resources/photos/goods/" + name + "_hd.jpg");
                    File fm = new File("resources/photos/goods/" + name + "_miniature.jpg");

                    BufferedImage originalImage = ImageIO.read(is);
                    ImageIO.write(originalImage, "jpg", f);

                    Thumbnails.of(originalImage)
                            .outputFormat("jpg")
                            .size(1920, 1080)
                            .toFile(fhd);
                    Thumbnails.of(originalImage)
                            .outputFormat("jpg")
                            .size(640, 480)
                            .toFile(fm);

                    r.setPath(f.getPath());
                    s.save(r);
                    g.addResource(r);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    invalidFile.set(true);
                }
            }

            s.save(g);
        });

        return !invalidFile.get();
    }

    @Override
    public void delete(IGood good) {
        sessionFactory.createSession(s -> {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Good> cq = cb.createQuery(Good.class);
            Root<Good> root = cq.from(Good.class);

            Fetch<Good, Resource> fetch = root.fetch(Good_.resources, JoinType.LEFT);

            cq.where(
                    cb.equal(root.get(Good_.id), good.getId() + "")
            );

            Good g = s.createQuery(cq).getSingleResult();

            g.getResources().forEach(r -> {
                String path = r.getPath().replaceFirst("[.][^.]+$", "");

                new File(r.getPath()).delete();
                new File(path + "_hd.png").delete();
                new File(path + "_miniature.png").delete();

                s.delete(r);
            });

            s.delete(s.get(Good.class, good.getId()));
        });
    }

    @Override
    public void deleteImage(long resourceId) {
        sessionFactory.createSession(s -> {
            Resource r = s.get(Resource.class, resourceId);

            new File(r.getPath()).delete();

            s.delete(r);
        });
    }

    @Override
    public List<IGood> getGoodsForIds(Set<Long> ids) {
        List<IGood> result = new ArrayList<>();

        sessionFactory.createSession(s -> {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Good> cq = cb.createQuery(Good.class);
            Root<Good> root = cq.from(Good.class);

            cq.where(
                    cb.or(
                            ids.stream().map(i -> cb.equal(root.get(Good_.id), i)).toArray(Predicate[]::new)
                    )
            );

            Query<Good> q = s.createQuery(cq);

            result.addAll(q.getResultList());
        });

        return result;
    }
}
