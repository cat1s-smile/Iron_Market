public class OrderInfo {
    private double orderID;
    private String content;
    private double totalCost;

    public OrderInfo(double orderID, String content, double totalCost) {
        this.orderID = orderID;
        this.content = content;
        this.totalCost = totalCost;
    }

    public double getOrderID() {
        return orderID;
    }

    public void setOrderID(double orderID) {
        this.orderID = orderID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
