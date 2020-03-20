package entities.supporting;

import java.util.ArrayList;
import java.util.List;

public class OrderInfo {
    private int orderID;
    private List<String> content;
    private double totalCost;

    public OrderInfo(int orderID, List<String> content, double totalCost) {
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

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
