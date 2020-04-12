package cz.kodytek.shop.data.entities.invoice.method;

import cz.kodytek.shop.data.entities.interfaces.invoice.method.IDeliveryMethod;
import org.javamoney.moneta.Money;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class InvoiceDeliveryMethod implements IDeliveryMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private long id;

    @Column(nullable = false)
    private long originalId;

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

    public long getOriginalId() {
        return originalId;
    }

    public void setOriginalId(long originalId) {
        this.originalId = originalId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
