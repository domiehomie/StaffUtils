package live.mufin.staffutils.Database;

import live.mufin.staffutils.Staffutils;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreSQLConnect {

    private String host = "plugin-test-do-user-9025222-0.b.db.ondigitalocean.com";
    private String port = "25060";
    private String username = "plugin";
    private String database = "defaultdb";
    private String password = "ya6hqht7sz4dzvyr";
    private boolean useSSL = false;


    private String url = "jdbc:postgresql://" + host + ":" + port  + "/" + database;
    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            Properties props = new Properties();
            props.setProperty("user",username);
            props.setProperty("password",password);
            props.setProperty("ssl", String.valueOf(useSSL));
            connection = DriverManager.getConnection(url, props);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            return;
        }
        if(connection != null) {
            Staffutils.core.sendFormattedMessage(Bukkit.getConsoleSender(), "Database connection &aSUCCESS");
        } else {
            Staffutils.core.sendFormattedMessage(Bukkit.getConsoleSender(), "Database connection &cFAILED");
        }
    }
}
