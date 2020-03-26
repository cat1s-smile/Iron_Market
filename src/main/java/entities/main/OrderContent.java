package entities.main;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class OrderContent {
    private int id;
    private int order;
    private int product;
    private Integer amount;

    public OrderContent() {}

    public OrderContent(int order) {
        this.order = order;
    }

    public OrderContent(int order, int product, int amount) {
        this.order = order;
        this.product = product;
        this.amount = amount;
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
    @Column(name = "\"order\"", nullable = false)
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Basic
    @Column(name = "product", nullable = false)
    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    @Basic
    @Column(name = "amount", nullable = true, precision = 0)
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderContent that = (OrderContent) o;

        if (id != that.id) return false;
        if (order != that.order) return false;
        if (product != that.product) return false;
        return Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + order;
        result = 31 * result + product;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }
}
