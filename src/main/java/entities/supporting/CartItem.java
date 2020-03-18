package entities.supporting;

import entities.main.Product;

import java.util.ArrayList;

public class CartItem {
    private Product product;
    private int number;
    private int status;

    public CartItem(Product product, int number, int status) {
        this.product = product;
        this.number = number;
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
