package model.database;

import entities.main.Category;

import java.sql.*;
import java.util.ArrayList;

public class CategoryDataBase {
    private static String username = "root";
    private static String password = "1111";
    private static String url = "jdbc:mysql://localhost:3306/marketdb?useUnicode=true&serverTimezone=UTC";

    public static ArrayList<Category> select() {
        ArrayList<Category> categories = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            ;
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM category");
                while (resultSet.next()) {
                    int idCategory = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    Category category = new Category(idCategory, name);
                    categories.add(category);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return categories;
    }

    public static Category searchByName(String categoryName) {
        Category category = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "SELECT * FROM category WHERE name=?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, categoryName);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        category = new Category(resultSet.getInt(1), resultSet.getString(2));
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return category;
    }
    //
    // //

    //**меняем id категориям в соответствии с бызой и вставляем новые категории
    //
    // insert

    public static Category selectOne(int id) {
        Category category = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "SELECT * FROM category WHERE idCategory=?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        int idCategory = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        category = new Category(idCategory, name);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return category;
    }

    public static int insert(Category category) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "INSERT INTO category (name) Values (?)";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, category.getName());
                    return preparedStatement.executeUpdate();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public static int update(Category category) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "UPDATE category SET name = ?" +
                        " WHERE idCategory = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, category.getName());
                    preparedStatement.setInt(2, category.getIdCategory());
                    return preparedStatement.executeUpdate();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public static int delete(int id) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String sql = "DELETE FROM category WHERE idCategory = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, id);
                    return preparedStatement.executeUpdate();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }
}
