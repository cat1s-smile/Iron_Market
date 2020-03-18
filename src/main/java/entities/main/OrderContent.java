package entities.main;

public class OrderContent {

    private int idOrder;
    private int idProduct;
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public OrderContent() {}

    public OrderContent(int idOrder) {
        this.idOrder = idOrder;
    }

    public OrderContent(int idOrder, int idProduct, int number) {
        this.idOrder = idOrder;
        this.idProduct = idProduct;
        this.number = number;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
}
