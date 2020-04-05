package cz.kodytek.shop.domain.services.interfaces.goods;

import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.domain.exceptions.InvalidFileTypeException;

import javax.servlet.http.Part;
import java.util.Collection;

public interface IGoodsService {

    IGood get(long id);

    boolean create(IGood good, Collection<Part> files, long categoryId) throws InvalidFileTypeException;

}
