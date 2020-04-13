package cz.kodytek.shop.presentation.api.models;

import org.javamoney.moneta.Money;

public class Good {

    private long id;
    private String name;
    private String description;
    private double cost;
    private int unitCount;

    public Good(long id, String name, String description, Money cost, int unitCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost.getNumber().doubleValue();
        this.unitCount = unitCount;
    }

    public Good() {
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

    public int getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(int unitCount) {
        this.unitCount = unitCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
