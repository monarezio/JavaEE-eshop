package cz.kodytek.shop.data.entities;

import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.data.entities.interfaces.reousrce.IResource;
import org.hibernate.annotations.Type;
import org.javamoney.moneta.Money;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Good implements IGood {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private long id;

    @Column
    private String title;

    @Column
    @Type(type = "text")
    private String description;

    @Column
    private int amount;

    @Column
    private BigDecimal cost;

    @OneToMany
    private Set<Resource> resources;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public List<? extends IResource> getImageNames() {
        return new ArrayList<>(resources);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Integer getAmount() {
        return amount;
    }

    @Override
    public Money getCost() {
        return Money.of(cost, "CZK");
    }

    @Override
    public long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCost(Money cost) {
        this.cost = cost.getNumberStripped();
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    public void addResource(Resource resource) {
        resources.add(resource);
    }
}
