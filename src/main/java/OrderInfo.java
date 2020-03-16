import java.util.ArrayList;

public class OrderInfo {
    private int orderID;
    private ArrayList<String> content;
    private double totalCost;

    public OrderInfo(int orderID, ArrayList<String> content, double totalCost) {
        this.orderID = orderID;
        this.content = content;
        this.totalCost = totalCost;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public ArrayList<String> getContent() {
        return content;
    }

    public void setContent(ArrayList<String> content) {
        this.content = content;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
