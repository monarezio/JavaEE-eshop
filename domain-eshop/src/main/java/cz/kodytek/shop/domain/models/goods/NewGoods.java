package cz.kodytek.shop.domain.models.goods;

import cz.kodytek.shop.data.entities.interfaces.goods.IGood;
import cz.kodytek.shop.data.entities.interfaces.goods.cateogry.ICategory;
import cz.kodytek.shop.data.entities.interfaces.reousrce.IResource;
import org.hibernate.validator.constraints.Range;
import org.javamoney.moneta.Money;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

@Named
@RequestScoped
public class NewGoods implements IGood {

    private Part files;

    @NotNull(message = "Title cannot be empty.")
    private String title;

    private String description;

    @NotNull(message = "Amount cannot be empty.")
    @Range(message = "Amount cannot be negative.")
    private Integer amount;

    @NotNull(message = "Price cannot be empty.")
    @Pattern(regexp = "[1-9][0-9]*[,.]{1}[0-9]{2}", message = "Price is incorrect format (ie. 10,00).")
    private String costStr;

    private long id;

    private ICategory category;

    @NotNull(message = "Please choose a category.")
    private Long categoryId;

    private List<ImageResource> imagePaths;

    public NewGoods(long id) {
        this.id = id;
    }

    public NewGoods() {
    }

    public Part getFiles() {
        return files;
    }

    public void setFiles(Part files) {
        this.files = files;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public List<? extends IResource> getImageNames() {
        return null;
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
        return Money.of(BigDecimal.valueOf(Double.parseDouble(getCostStr().replace(",", "."))), "CZK");
    }

    public void setCost(Money cost) {
        costStr = String.format("%.2f", cost.getNumberStripped());
    }

    public String getCostStr() {
        return costStr;
    }

    public void setCostStr(String costStr) {
        this.costStr = costStr;
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

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    @Override
    public ICategory getCategory() {
        return category; //TODO: Implement
    }

    @Override
    public boolean hasImages() {
        return getImageNames().size() > 0;
    }

    public void setCategory(ICategory category) {
        this.category = category;
    }

    public List<ImageResource> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<ImageResource> imagePaths) {
        this.imagePaths = imagePaths;
    }
}
