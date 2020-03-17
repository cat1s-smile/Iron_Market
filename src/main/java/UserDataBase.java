import java.sql.*;
import java.util.ArrayList;

public class UserDataBase {
    private static String username = "root";
    private static String password = "1111";
    private static String url = "jdbc:mysql://localhost:3306/marketdb?useUnicode=true&serverTimezone=UTC";

    public static ArrayList<User> select() {
        ArrayList<User> users = new ArrayList<>();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();;
            try (Connection conn = DriverManager.getConnection(url, username, password)){
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

    public static User selectOne(String id) {
        User user = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
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
    public static int insert(User user) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)){
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

    /*public static int update(Product product) {
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
                    return  preparedStatement.executeUpdate();
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return 0;
    }
    public static int delete(int id) {
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
    }*/
}
