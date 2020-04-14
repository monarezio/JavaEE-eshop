package cz.kodytek.shop.domain.api.models;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Product implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private Integer unitCount;

    public Product(@NotNull Long id, @NotNull Integer unitCount) {
        this.id = id;
        this.unitCount = unitCount;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(Integer unitCount) {
        this.unitCount = unitCount;
    }
}
