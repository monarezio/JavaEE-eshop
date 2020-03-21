package cz.kodytek.shop.data.entities.interfaces;

public interface IAddress extends IEntityId {
    String getStreet();

    String getCity();

    String getPostCode();
}
