package cz.kodytek.shop.domain.services.interfaces.goods;

import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.domain.exceptions.InvalidFileTypeException;
import cz.kodytek.shop.domain.models.interfaces.IEntityPage;

import javax.servlet.http.Part;
import java.io.InputStream;
import java.util.Collection;

public interface IGoodsService {

    IGood get(long id);

    boolean create(IGood good, Collection<InputStream> files, long categoryId) throws InvalidFileTypeException;

    IEntityPage<? extends IGood> getGoods(String search, int perPage, int page);

    long getGoodsCount(String searchFilter);

    boolean edit(IGood good);

    void delete(IGood good);

}
