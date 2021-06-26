package live.mufin.staffutils.Database;

import live.mufin.staffutils.StaffUtils;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreSQLConnect {

    private String host = "plugintest-do-user-9025222-0.b.db.ondigitalocean.com";
    private String port = "25060";
    private String username = "doadmin";
    private String database = "defaultdb";
    private String password = "trl0fiqbuubokyks";
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
            StaffUtils.core.sendFormattedMessage(Bukkit.getConsoleSender(), "Database connection &aSUCCESS");
        } else {
            StaffUtils.core.sendFormattedMessage(Bukkit.getConsoleSender(), "Database connection &cFAILED");
        }
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
