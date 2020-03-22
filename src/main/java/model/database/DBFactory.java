package model.database;

import java.io.*;
import java.util.Properties;

class DBFactory {
    private static String URL;
    private static Properties properties;

    static void init() throws IOException {
        InputStream inputStream = DBFactory.class.getClassLoader().getResourceAsStream("database.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        DBFactory.URL = (String)properties.get("URL");
        DBFactory.properties = properties;
    }

    static Properties getProperties() {
        return properties;
    }

    static String getURL() {
        return URL;
    }
}
