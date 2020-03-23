package model.database;

import entities.main.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class OrderDataBase {
    private static String username = "root";
    private static String password = "кщще";
    private static String url = "jdbc:mysql://localhost:3306/marketdb?useUnicode=true&serverTimezone=UTC";

    static List<Order> select() {
        List<Order> orders = new ArrayList<Order>();
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            ;
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM `order`");
                while (resultSet.next()) {
                    int idOrder = resultSet.getInt(1);
                    String idUser = resultSet.getString(2);
                    String status = resultSet.getString(3);
                    Order order = new Order(idOrder, idUser, status);
                    orders.add(order);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return orders;
    }

    static List<Order> selectByID(String userID) {
        List<Order> orders = new ArrayList<Order>();
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())) {
                String sql = "SELECT * FROM `order` WHERE idUser=? and status=?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, userID);
                    preparedStatement.setString(2, "1");
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int idOrder = resultSet.getInt(1);
                        String idUser = resultSet.getString(2);
                        String status = resultSet.getString(3);
                        Order order = new Order(idOrder, idUser, status);
                        orders.add(order);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    static int update(Order order) {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())) {
                String sql = "UPDATE `order` SET idUser = ?, status = ?" +
                        " WHERE idOrder = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, order.getIdUser());
                    preparedStatement.setString(2, order.getStatus());
                    preparedStatement.setInt(3, order.getIdOrder());
                    return preparedStatement.executeUpdate();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }


    static Order selectActive(String userID) {
        Order order = null;
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())) {
                String sql = "SELECT * FROM `order` WHERE idUser=? AND status=?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, userID);
                    preparedStatement.setString(2, "0");
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        int idOrder = resultSet.getInt(1);
                        String idUser = resultSet.getString(2);
                        String status = resultSet.getString(3);
                        order = new Order(idOrder, idUser, status);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return order;
    }

    static int insert(Order order) {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())) {
                String sql = "INSERT INTO `order` (idUser, status) Values (?, ?)";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, order.getIdUser());
                    preparedStatement.setString(2, order.getStatus());
                    return preparedStatement.executeUpdate();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }
}
