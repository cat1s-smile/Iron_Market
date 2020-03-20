package model.database;

import entities.main.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class ProductDataBase {
    private static String username = "root";
    private static String password = "1111";
    private static String url = "jdbc:mysql://localhost:3306/marketdb?useUnicode=true&serverTimezone=UTC";

    static List<Product> select() {
        List<Product> products = new ArrayList<Product>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();;
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM product");
                while(resultSet.next()){
                    int idProduct = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    int price = resultSet.getInt(3);
                    int amount = resultSet.getInt(4);
                    String description = resultSet.getString(5);
                    int idCategory = resultSet.getInt(6);
                    int status = resultSet.getInt(7);
                    Product product = new Product(idProduct, idCategory, name, price, amount, description, status);
                    products.add(product);
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return products;
    }

    static List<Product> selectOnSale() {
        List<Product> products = new ArrayList<Product>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();;
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                Statement statement = conn.createStatement();
                String sql = "SELECT * FROM product WHERE status = ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, 1);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()){
                        int idProduct = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        int price = resultSet.getInt(3);
                        int amount = resultSet.getInt(4);
                        String description = resultSet.getString(5);
                        int idCategory = resultSet.getInt(6);
                        int status = resultSet.getInt(7);
                        Product product = new Product(idProduct, idCategory, name, price, amount, description, status);
                        products.add(product);
                    }
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return products;
    }

    static List<Product> selectByCategory(int id) {
        List<Product> products = new ArrayList<Product>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();;
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM product WHERE idCategory=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int idProduct = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        int price = resultSet.getInt(3);
                        int amount = resultSet.getInt(4);
                        String description = resultSet.getString(5);
                        int idCategory = resultSet.getInt(6);
                        int status = resultSet.getInt(7);
                        Product product = new Product(idProduct, idCategory, name, price, amount, description, status);
                        products.add(product);
                    }
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return products;
    }

    static List<Product> selectByCategoryOnSale(int id) {
        List<Product> products = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();;
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM product WHERE idCategory=? AND status=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, id);
                    preparedStatement.setInt(2, 1);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int idProduct = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        int price = resultSet.getInt(3);
                        int amount = resultSet.getInt(4);
                        String description = resultSet.getString(5);
                        int idCategory = resultSet.getInt(6);
                        int status = resultSet.getInt(7);
                        Product product = new Product(idProduct, idCategory, name, price, amount, description, status);
                        products.add(product);
                    }
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return products;
    }

    static List<Product> selectBySearch(String search, int categoryID) {
        List<Product> products = new ArrayList<Product>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();;
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM product WHERE idCategory=? AND (idProduct LIKE ? OR name LIKE ? OR description LIKE ?)";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, categoryID);
                    preparedStatement.setString(2, '%' + search + '%');
                    preparedStatement.setString(3, '%' + search + '%');
                    preparedStatement.setString(4, '%' + search + '%');
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int idProduct = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        int price = resultSet.getInt(3);
                        int amount = resultSet.getInt(4);
                        String description = resultSet.getString(5);
                        int idCategory = resultSet.getInt(6);
                        int status = resultSet.getInt(7);
                        Product product = new Product(idProduct, idCategory, name, price, amount, description, status);
                        products.add(product);
                    }
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return products;
    }

    static List<Product> selectBySearch(String search) {
        List<Product> products = new ArrayList<Product>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();;
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM product WHERE idProduct LIKE ? OR name LIKE ? OR description LIKE ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, '%' + search + '%');
                    preparedStatement.setString(2, '%' + search + '%');
                    preparedStatement.setString(3, '%' + search + '%');
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int idProduct = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        int price = resultSet.getInt(3);
                        int amount = resultSet.getInt(4);
                        String description = resultSet.getString(5);
                        int idCategory = resultSet.getInt(6);
                        int status = resultSet.getInt(7);
                        Product product = new Product(idProduct, idCategory, name, price, amount, description, status);
                        products.add(product);
                    }
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return products;
    }

    static Product selectOne(int id) {
        Product product = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM product WHERE idProduct=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
                        int idProduct = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        int price = resultSet.getInt(3);
                        int amount = resultSet.getInt(4);
                        String description = resultSet.getString(5);
                        int idCategory = resultSet.getInt(6);
                        int status = resultSet.getInt(7);
                        product = new Product(idProduct, idCategory, name, price, amount, description, status);
                    }
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return product;
    }

    static boolean isContain(Product product) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "SELECT * FROM product WHERE name like ? and description like ? and idCategory=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setString(1, product.getName());
                    preparedStatement.setString(2, product.getDescription());
                    preparedStatement.setInt(3, product.getIdCategory());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    return resultSet.next();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return false;
    }
    static void insert(Product product) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "INSERT INTO product (name, price, amount, description, idCategory) Values (?, ?, ?, ?, ?)";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setString(1, product.getName());
                    preparedStatement.setInt(2, product.getPrice());
                    preparedStatement.setInt(3,product.getAmount());
                    preparedStatement.setString(4, product.getDescription());
                    preparedStatement.setInt(5, product.getIdCategory());
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }

    static void update(Product product) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "UPDATE product SET name = ?, price = ?, amount = ?, description = ?, idCategory = ?" +
                        " WHERE idProduct = ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setString(1, product.getName());
                    preparedStatement.setInt(2, product.getPrice());
                    preparedStatement.setInt(3,product.getAmount());
                    preparedStatement.setString(4, product.getDescription());
                    preparedStatement.setInt(5, product.getIdCategory());
                    preparedStatement.setInt(6, product.getIdProduct());
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    static int delete(int id) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "DELETE FROM product WHERE idProduct = ?";
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
    }

    static int archive(Product product) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "UPDATE product SET status = ? WHERE idProduct = ?";

                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, 0);
                    preparedStatement.setInt(2, product.getIdProduct());
                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }

    static int deArchive(Product product) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
                String sql = "UPDATE product SET status = ? WHERE idProduct = ?";

                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, 1);
                    preparedStatement.setInt(2, product.getIdProduct());
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
