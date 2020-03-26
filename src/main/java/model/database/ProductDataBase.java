package model.database;

import entities.main.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class ProductDataBase {

    static List<Product> select() {
        EntityManager manager = DBFactory.getEntityManager();
        List result = manager.createQuery("FROM Product").getResultList();
        manager.close();
        return (List<Product>) result;
    }

    static List<Product> selectOnSale() {
        return getByStatus(1);
    }

    static List<Product> selectArchivedProducts() {
        return getByStatus(0);
    }

    private static List<Product> getByStatus(int status) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM Product WHERE status = :status");
        query.setParameter("status", status);
        List result = query.getResultList();
        manager.close();
        return (List<Product>) result;
    }

    static List<Product> selectByCategory(int categoryID) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM Product WHERE category = :category");
        query.setParameter("category", categoryID);
        List result = query.getResultList();
        manager.close();
        return (List<Product>) result;
    }

    static List<Product> selectByCategoryOnSale(int categoryID) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM Product WHERE category = :category and status = :status");
        query.setParameter("category", categoryID);
        query.setParameter("status", 1);
        List result = query.getResultList();
        manager.close();
        return (List<Product>) result;
    }

    static List<Product> selectBySearch(String search, int categoryID) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM Product " +
                "WHERE category = :category and (name like :search or description like :search)");
        query.setParameter("category", categoryID);
        query.setParameter("search", '%' + search + '%');
        List result = query.getResultList();
        manager.close();
        return (List<Product>) result;
    }

    static List<Product> selectBySearch(String search) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM Product " +
                "WHERE name like :search or description like :search");
        query.setParameter("search", '%' + search + '%');
        List result = query.getResultList();
        manager.close();
        return (List<Product>) result;
    }

    static Product selectOne(int id) {
        EntityManager manager = DBFactory.getEntityManager();
        Product result = manager.find(Product.class, id);
        manager.close();
        return result;
    }

    static boolean isContain(Product product) {
        EntityManager manager = DBFactory.getEntityManager();
        Query query = manager.createQuery("FROM Product " +
                "WHERE name like :name and description like :desc and category = :cat");
        query.setParameter("name", product.getName());
        query.setParameter("desc", product.getDescription());
        query.setParameter("cat", product.getCategory());
        boolean result = !query.getResultList().isEmpty();
        manager.close();
        return result;
    }

    static void insert(Product product) {
        EntityManager manager = DBFactory.getEntityManager();
        manager.getTransaction().begin();
        manager.persist(product);
        manager.getTransaction().commit();
        manager.close();
        }

    static void update(Product product) {
        EntityManager manager = DBFactory.getEntityManager();
        manager.getTransaction().begin();
        manager.merge(product);
        manager.getTransaction().commit();
        manager.close();
        }

    static void delete(int id) {
        EntityManager manager = DBFactory.getEntityManager();
        manager.getTransaction().begin();
        manager.remove(manager.find(Product.class, id));
        manager.getTransaction().commit();
        manager.close();
        }

    static void archive(Product product) {
        product.setStatus(0);
        EntityManager manager = DBFactory.getEntityManager();
        manager.getTransaction().begin();
        manager.merge(product);
        manager.getTransaction().commit();
        manager.close();
        }

    static void deArchive(Product product) {
        product.setStatus(1);
        EntityManager manager = DBFactory.getEntityManager();
        manager.getTransaction().begin();
        manager.merge(product);
        manager.getTransaction().commit();
        manager.close();
        }

    /*static List<Product> select() {
        List<Product> products = new ArrayList<Product>();
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();;
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())){
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM product");
                while(resultSet.next()){
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    int price = resultSet.getInt(3);
                    int amount = resultSet.getInt(4);
                    String description = resultSet.getString(5);
                    int idCategory = resultSet.getInt(6);
                    int status = resultSet.getInt(7);
                    Product product = new Product(id, idCategory, name, price, amount, description, status);
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
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();;
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())){
                Statement statement = conn.createStatement();
                String sql = "SELECT * FROM product WHERE status = ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, 1);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()){
                        int id = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        int price = resultSet.getInt(3);
                        int amount = resultSet.getInt(4);
                        String description = resultSet.getString(5);
                        int idCategory = resultSet.getInt(6);
                        int status = resultSet.getInt(7);
                        Product product = new Product(id, idCategory, name, price, amount, description, status);
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

    static List<Product> selectArchivedProducts() {
        List<Product> products = new ArrayList<Product>();
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();;
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())){
                Statement statement = conn.createStatement();
                String sql = "SELECT * FROM product WHERE status = ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, 0);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while(resultSet.next()){
                        int id = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        int price = resultSet.getInt(3);
                        int amount = resultSet.getInt(4);
                        String description = resultSet.getString(5);
                        int idCategory = resultSet.getInt(6);
                        int status = resultSet.getInt(7);
                        Product product = new Product(id, idCategory, name, price, amount, description, status);
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

    static List<Product> selectByCategory(int categoryID) {
        List<Product> products = new ArrayList<Product>();
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();;
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())){
                String sql = "SELECT * FROM product WHERE idCategory=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, categoryID);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        int price = resultSet.getInt(3);
                        int amount = resultSet.getInt(4);
                        String description = resultSet.getString(5);
                        int idCategory = resultSet.getInt(6);
                        int status = resultSet.getInt(7);
                        Product product = new Product(id, idCategory, name, price, amount, description, status);
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

    static List<Product> selectByCategoryOnSale(int categoryID) {
        List<Product> products = new ArrayList<>();
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();;
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())){
                String sql = "SELECT * FROM product WHERE idCategory=? AND status=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, categoryID);
                    preparedStatement.setInt(2, 1);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        int price = resultSet.getInt(3);
                        int amount = resultSet.getInt(4);
                        String description = resultSet.getString(5);
                        int idCategory = resultSet.getInt(6);
                        int status = resultSet.getInt(7);
                        Product product = new Product(id, idCategory, name, price, amount, description, status);
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
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();;
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())){
                String sql = "SELECT * FROM product WHERE idCategory=? AND (id LIKE ? OR name LIKE ? OR description LIKE ?)";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, categoryID);
                    preparedStatement.setString(2, '%' + search + '%');
                    preparedStatement.setString(3, '%' + search + '%');
                    preparedStatement.setString(4, '%' + search + '%');
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        int price = resultSet.getInt(3);
                        int amount = resultSet.getInt(4);
                        String description = resultSet.getString(5);
                        int idCategory = resultSet.getInt(6);
                        int status = resultSet.getInt(7);
                        Product product = new Product(id, idCategory, name, price, amount, description, status);
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
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();;
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())){
                String sql = "SELECT * FROM product WHERE id LIKE ? OR name LIKE ? OR description LIKE ?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, '%' + search + '%');
                    preparedStatement.setString(2, '%' + search + '%');
                    preparedStatement.setString(3, '%' + search + '%');
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        int price = resultSet.getInt(3);
                        int amount = resultSet.getInt(4);
                        String description = resultSet.getString(5);
                        int idCategory = resultSet.getInt(6);
                        int status = resultSet.getInt(7);
                        Product product = new Product(id, idCategory, name, price, amount, description, status);
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
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())){
                String sql = "SELECT * FROM product WHERE id=?";
                try(PreparedStatement preparedStatement = conn.prepareStatement(sql)){
                    preparedStatement.setInt(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
                        //int id = resultSet.getInt(1);
                        String name = resultSet.getString(2);
                        int price = resultSet.getInt(3);
                        int amount = resultSet.getInt(4);
                        String description = resultSet.getString(5);
                        int idCategory = resultSet.getInt(6);
                        int status = resultSet.getInt(7);
                        product = new Product(id, idCategory, name, price, amount, description, status);
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
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())) {
                String sql = "SELECT * FROM product WHERE name like ? and description like ? and idCategory=?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, product.getName());
                    preparedStatement.setString(2, product.getDescription());
                    preparedStatement.setInt(3, product.getCategory());
                    ResultSet resultSet = preparedStatement.executeQuery();
                    return resultSet.next();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return false;
    }

    static void insert(Product product) {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())) {
                String sql = "INSERT INTO product (name, price, amount, description, idCategory) Values (?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, product.getName());
                    preparedStatement.setInt(2, product.getPrice());
                    preparedStatement.setInt(3, product.getAmount());
                    preparedStatement.setString(4, product.getDescription());
                    preparedStatement.setInt(5, product.getCategory());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    static void update(Product product) {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())) {
                String sql = "UPDATE product SET name = ?, price = ?, amount = ?, description = ?, idCategory = ?" +
                        " WHERE id = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, product.getName());
                    preparedStatement.setInt(2, product.getPrice());
                    preparedStatement.setInt(3, product.getAmount());
                    preparedStatement.setString(4, product.getDescription());
                    preparedStatement.setInt(5, product.getCategory());
                    preparedStatement.setInt(6, product.getId());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    static int delete(int id) {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())) {
                String sql = "DELETE FROM product WHERE id = ?";
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

    static int archive(Product product) {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())) {
                String sql = "UPDATE product SET status = ? WHERE id = ?";

                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, 0);
                    preparedStatement.setInt(2, product.getId());
                    return preparedStatement.executeUpdate();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    static int deArchive(Product product) {
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DBFactory.getURL(), DBFactory.getProperties())) {
                String sql = "UPDATE product SET status = ? WHERE id = ?";

                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, 1);
                    preparedStatement.setInt(2, product.getId());
                    return preparedStatement.executeUpdate();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }*/
}
