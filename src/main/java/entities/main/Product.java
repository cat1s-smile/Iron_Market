package entities.main;

import com.fasterxml.jackson.annotation.*;
import org.json.JSONObject;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;

@Entity
@JsonAutoDetect
@JsonRootName("Product")
@JsonPropertyOrder({"id, category, name, price, amount, description"})
public class Product {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private int price;
    @JsonProperty("amount")
    private int amount;
    @JsonProperty("description")
    private String description;
    @JsonProperty("category")
    private int category;
    @JsonIgnore
    private Integer status = 1;
    @JsonIgnore
    boolean availableToDelete;
    @JsonIgnore
    String categoryName;


    public Product() {
    }

    public Product(int id, int category, String name, int price, int amount, String description, int status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.description = description;
        this.status = status;
    }

    public Product(int id, int category, String name, int price, int amount, String description) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.description = description;
    }

    public Product(int category, String name, int price, int amount, String description) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.description = description;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "price", nullable = false)
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Basic
    @Column(name = "amount", nullable = false)
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "category", nullable = false)
    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Transient
    public boolean isAvailableToDelete() {
        return availableToDelete;
    }

    public void setAvailableToDelete(boolean availableToDelete) {
        this.availableToDelete = availableToDelete;
    }

    @Transient
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (price != product.price) return false;
        if (amount != product.amount) return false;
        if (category != product.category) return false;
        if (!Objects.equals(name, product.name)) return false;
        if (!Objects.equals(description, product.description)) return false;
        return Objects.equals(status, product.status);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + amount;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + category;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
