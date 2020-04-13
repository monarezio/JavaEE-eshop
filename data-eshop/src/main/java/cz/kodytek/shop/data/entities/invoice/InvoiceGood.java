package cz.kodytek.shop.data.entities.invoice;

import cz.kodytek.shop.data.entities.interfaces.invoice.IInvoiceGood;
import org.javamoney.moneta.Money;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class InvoiceGood implements IInvoiceGood {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private long id;

    @Column(nullable = false)
    private long originalId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal cost;

    @Column(nullable = false)
    private int amount;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public long getOriginalId() {
        return originalId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Money getCost() {
        return Money.of(cost, "CZK");
    }

    @Override
    public int getAmount() {
        return amount;
    }

    public void setOriginalId(long originalId) {
        this.originalId = originalId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCost(Money cost) {
        this.cost = cost.getNumberStripped();
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
