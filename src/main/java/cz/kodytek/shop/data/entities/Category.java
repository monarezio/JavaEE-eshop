package cz.kodytek.shop.data.entities;

import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.data.entities.interfaces.goods.cateogry.ICategory;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Category implements ICategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false, unique = true, name = "category_order")
    private int order;

    @Column
    private String title;

    @Column
    @Type(type = "text")
    private String description;

    @OneToMany
    @JoinColumn(name = "category_id")
    private Set<Good> goods;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Integer getOrder() {
        return order;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGoods(Set<Good> goods) {
        this.goods = goods;
    }

    @Override
    public List<IGood> getGoods() {
        return goods.stream().sorted(Comparator.comparingLong(Good::getId)).collect(Collectors.toList());
    }
}
