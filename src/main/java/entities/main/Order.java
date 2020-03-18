package entities.main;

public class Order {

    private int idOrder;
    private String idUser;
    private String status;

    public Order() {}

    public Order(String idUser, String status) {
        this.idUser = idUser;
        this.status = status;
    }

    public Order(int idOrder, String  idUser, String status) {
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.status = status;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
