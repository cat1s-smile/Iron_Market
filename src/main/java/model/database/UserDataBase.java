package model.database;

import entities.main.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class UserDataBase {
    private static String username = "root";
    private static String password = "кщще";
    private static String url = "jdbc:mysql://localhost:3306/marketdb?useUnicode=true&serverTimezone=UTC";

    /*static List<User> select() {
        List<User> users = new ArrayList<>();
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();;
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())){
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
                while(resultSet.next()){
                    String id = resultSet.getString(1);
                    String login = resultSet.getString(2);
                    String password = resultSet.getString(3);
                    String type = resultSet.getString(4);
                    users.add(new User(id, login, password, type));
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return users;
    }

    static User selectOne(String id) {
        User user = null;
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())){
                String sql = "SELECT * FROM user WHERE idUser=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setString(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
                        String idUser = resultSet.getString(1);
                        String login = resultSet.getString(2);
                        String password = resultSet.getString(3);
                        String type = resultSet.getString(4);
                        user = new User(idUser, login, password, type);
                    }
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return user;
    }
    static int insert(User user) {
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())){
                String sql = "INSERT INTO user (idUser, login, password, type) Values (?, ?, ?, ?)";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setString(1, user.getId());
                    preparedStatement.setString(2, user.getLogin());
                    preparedStatement.setString(3, user.getPassword());
                    preparedStatement.setString(4, user.getType());
                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }

    /*static int update(RawProduct rawProduct) {
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())){
                String sql = "UPDATE rawProduct SET name = ?, price = ?, amount = ?, description = ?, idCategory = ?" +
                        " WHERE product = ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setString(1, rawProduct.getName());
                    preparedStatement.setInt(2, rawProduct.getPrice());
                    preparedStatement.setInt(3,rawProduct.getAmount());
                    preparedStatement.setString(4, rawProduct.getDescription());
                    preparedStatement.setInt(5, rawProduct.getIdCategory());
                    preparedStatement.setInt(6, rawProduct.getproduct());
                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }
    static int delete(int id) {
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())){
                String sql = "DELETE FROM rawProduct WHERE product = ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, id);
                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }*/
}
