package model.database;

import entities.main.OrderContent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

class OrderContentDataBase {
    private static String username = "root";
    private static String password = "кщще";
    private static String url = "jdbc:mysql://localhost:3306/marketdb?useUnicode=true&serverTimezone=UTC";

    static List<OrderContent> select() {
        List<OrderContent> products = new ArrayList<>();
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            ;
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM ordercontent");
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    int idProduct = resultSet.getInt(2);
                    int amount = resultSet.getInt(3);
                    OrderContent orderContent = new OrderContent(id, idProduct, amount);
                    products.add(orderContent);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return products;
    }

    static List<OrderContent> selectByOrderID(int orderID) {
        List<OrderContent> products = new ArrayList<>();
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties()))
            {
                String sql = "SELECT * FROM ordercontent WHERE idOrder=?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, orderID);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        int idProduct = resultSet.getInt(2);
                        int amount = resultSet.getInt(3);
                        OrderContent orderContent = new OrderContent(id, idProduct, amount);
                        products.add(orderContent);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return products;
    }

    static boolean doOrdersContain(int productID) {
        List<OrderContent> products = new ArrayList<>();
        boolean result = false;
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())) {
                String sql = "SELECT * FROM ordercontent WHERE idProduct=?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, productID);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next())
                        result = true;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return result;
    }

    static OrderContent selectOne(int orderID, int productID) {
        OrderContent content = null;
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())) {
                String sql = "SELECT * FROM ordercontent WHERE idOrder=? and idProduct=?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, orderID);
                    preparedStatement.setInt(2, productID);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        int idOrder = resultSet.getInt(1);
                        int idProduct = resultSet.getInt(2);
                        int amount = resultSet.getInt(3);
                        content = new OrderContent(idOrder, idProduct, amount);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return content;
    }

    static int insert(OrderContent orderContent) {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())) {
                String sql = "INSERT INTO ordercontent (idOrder, idProduct, amount) Values (?, ?, ?)";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, orderContent.getIdOrder());
                    preparedStatement.setInt(2, orderContent.getIdProduct());
                    preparedStatement.setInt(3, orderContent.getNumber());
                    return preparedStatement.executeUpdate();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    static int update(OrderContent content) {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())) {
                String sql = "UPDATE ordercontent SET idOrder = ?, idProduct = ?, amount = ?" +
                        " WHERE idOrder = ? and idProduct = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, content.getIdOrder());
                    preparedStatement.setInt(2, content.getIdProduct());
                    preparedStatement.setInt(3, content.getNumber());
                    preparedStatement.setInt(4, content.getIdOrder());
                    preparedStatement.setInt(5, content.getIdProduct());
                    return preparedStatement.executeUpdate();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    static int delete(int orderID, int productID) {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())) {
                String sql = "DELETE FROM ordercontent WHERE idOrder=? AND idProduct = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, orderID);
                    preparedStatement.setInt(2, productID);
                    return preparedStatement.executeUpdate();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }
}
