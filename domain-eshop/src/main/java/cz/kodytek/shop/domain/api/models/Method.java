package cz.kodytek.shop.domain.api.models;

import org.javamoney.moneta.Money;

import java.io.Serializable;
import java.math.BigDecimal;

public class Method implements Serializable {

    private String name;
    private double cost;
    private long id;

    public Method(long id, String name, Money cost) {
        this.id = id;
        this.name = name;
        this.cost = cost.getNumber().doubleValue();
    }

    public Method() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
