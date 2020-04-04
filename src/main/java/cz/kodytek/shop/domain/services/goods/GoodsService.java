package cz.kodytek.shop.domain.services.goods;

import cz.kodytek.shop.data.connection.HibernateSessionFactory;
import cz.kodytek.shop.data.entities.Good;
import cz.kodytek.shop.data.entities.Resource;
import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.domain.common.utils.BufferedImageUtil;
import cz.kodytek.shop.domain.exceptions.InvalidFileTypeException;
import cz.kodytek.shop.domain.services.interfaces.goods.IGoodsService;

import javax.enterprise.context.ApplicationScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
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
                result.set(s.get(Good.class, id));
            });
        } catch(PersistenceException e) {
            return null;
        }

        return result.get();
    }

    @Override
    public boolean create(IGood good, Collection<Part> files) throws InvalidFileTypeException {

        AtomicBoolean invalidFile = new AtomicBoolean(false);

        sessionFactory.createSession(s -> {
            Good g = new Good();
            g.setAmount(good.getAmount());
            g.setTitle(good.getTitle());
            g.setCost(good.getCost());
            g.setDescription(good.getDescription());

            for(Part p : files) {
                Resource r = new Resource();
                new File("resources/photos/goods/").mkdirs();

                File f = new File("resources/photos/goods/" + UUID.randomUUID().toString() + ".png");
                File fm = new File("resources/photos/goods/" + UUID.randomUUID().toString() + "_miniature.png");

                try {
                    InputStream is = p.getInputStream();
                    OutputStream outStream = new FileOutputStream(f);

                    BufferedImage image = ImageIO.read(is);
                    BufferedImage resizedImage = bufferedImageUtil.resize(image, 500);
                    ImageIO.write(image, "png", f);
                    ImageIO.write(resizedImage, "png", fm);

                    r.setPath(f.getPath());
                    s.save(r);
                    g.addResource(r);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch(IllegalArgumentException e) {
                    invalidFile.set(true);
                }
            }

            s.save(g);
        });

        if(invalidFile.get())
            throw new InvalidFileTypeException();

        return true;
    }
}
