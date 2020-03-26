package model.database;

import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.*;
import java.util.Properties;

class DBFactory {
    private static String URL;
    private static Properties properties;
    private static EntityManagerFactory factory = null;

    static void init() {
        DBFactory.factory = Persistence.createEntityManagerFactory("marketdb.jpa");
    }

    static EntityManagerFactory getFactory() {
        return Persistence.createEntityManagerFactory("marketdb.jpa");
    }

    static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    /*static void init() throws IOException {
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
    }*/
}
