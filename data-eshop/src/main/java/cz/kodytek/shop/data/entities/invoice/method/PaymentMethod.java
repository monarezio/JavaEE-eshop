package cz.kodytek.shop.data.entities.invoice.method;

import cz.kodytek.shop.data.entities.interfaces.invoice.method.IPaymentMethod;
import org.javamoney.moneta.Money;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class PaymentMethod implements IPaymentMethod, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal cost;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Money getCost() {
        return Money.of(cost, "CZK");
    }

    @Override
    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(Money cost) {
        this.cost = cost.getNumberStripped();
    }
}
