package model.database;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.IOException;

@Singleton
@Startup
public class Initializer {

    @PostConstruct
    public void init() throws ClassNotFoundException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        DBFactory.init();
    }
}
