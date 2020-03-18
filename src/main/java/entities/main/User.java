package entities.main;

public class User {
    private String id;
    private String login;
    private String password;
    private String type;
    private static String defaultID = "1";
    private static String defaultLogin = "1";
    private static String defaultPassword = "1";
    private static String defaultType = "1";

    public User(String id, String login, String password, String type) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String getDefaultID() {
        return defaultID;
    }

    public static String getDefaultLogin() {
        return defaultLogin;
    }

    public static String getDefaultPassword() {
        return defaultPassword;
    }

    public static String getDefaultType() {
        return defaultType;
    }
}
