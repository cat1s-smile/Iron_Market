import java.sql.*;
import java.util.ArrayList;

public class OrderContentDataBase {
    private static String username = "root";
    private static String password = "admin";
    private static String url = "jdbc:mysql://localhost:3306/MyShop?useUnicode=true&serverTimezone=UTC";

    public static ArrayList<OrderContent> select() {
        ArrayList<OrderContent> products = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();;
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM ordercontent");
                while(resultSet.next()){
                    int id = resultSet.getInt(1);
                    int idProduct = resultSet.getInt(2);
                    int amount = resultSet.getInt(3);
                    OrderContent orderContent = new OrderContent(id, idProduct, amount);
                    products.add(orderContent);
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return products;
    }

    public static ArrayList<OrderContent> selectByOrderID(int OrderID) {
        ArrayList<OrderContent> products = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM ordercontent WHERE idOrder=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, OrderID);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()){
                        int id = resultSet.getInt(1);
                        int idProduct = resultSet.getInt(2);
                        int amount = resultSet.getInt(3);
                        OrderContent orderContent = new OrderContent(id, idProduct, amount);
                        products.add(orderContent);
                    }
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return products;
    }


    public static OrderContent selectOne(int orderID, int productID) {
        OrderContent content = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM ordercontent WHERE idOrder=? and idProduct=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, orderID);
                    preparedStatement.setInt(2, productID);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()) {
                        int idOrder = resultSet.getInt(1);
                        int idProduct = resultSet.getInt(2);
                        int amount = resultSet.getInt(3);
                        content = new OrderContent(idOrder, idProduct, amount);
                    }
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return content;
    }

    public static int insert(OrderContent orderContent) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "INSERT INTO ordercontent (idOrder, idProduct, amount) Values (?, ?, ?)";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, orderContent.getIdOrder());
                    preparedStatement.setInt(2, orderContent.getIdProduct());
                    preparedStatement.setInt(3, orderContent.getNumber());
                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }

    public static int update(OrderContent content) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "UPDATE ordercontent SET idOrder = ?, idProduct = ?, amount = ?" +
                        " WHERE idOrder = ? and idProduct = ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, content.getIdOrder());
                    preparedStatement.setInt(2, content.getIdProduct());
                    preparedStatement.setInt(3, content.getNumber());
                    preparedStatement.setInt(4, content.getIdOrder());
                    preparedStatement.setInt(5, content.getIdProduct());
                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }
    public static int delete(int orderID, int productID) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "DELETE FROM ordercontent WHERE idOrder=? AND idProduct = ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, orderID);
                    preparedStatement.setInt(2, productID);
                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }
}
