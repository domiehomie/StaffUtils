package live.mufin.staffutils.database;

import live.mufin.staffutils.StaffUtils;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreSQLConnect {

    private final String host = StaffUtils.config.getString("database.host");
    private final String port = StaffUtils.config.getString("database.port");
    private final String username = StaffUtils.config.getString("database.username");
    private final String database = StaffUtils.config.getString("database.database");
    private final String password = StaffUtils.config.getString("database.password");
    private final boolean useSSL = StaffUtils.config.getBoolean("database.useSSL");


    private final String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return connection != null;
    }

    public void connect() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);
        props.setProperty("ssl", String.valueOf(useSSL));
        connection = DriverManager.getConnection(url, props);

        if (connection != null) {
            StaffUtils.core.sendFormattedMessage(Bukkit.getConsoleSender(), "Database connection &aSUCCESS");
        } else {
            StaffUtils.core.sendFormattedMessage(Bukkit.getConsoleSender(), "Database connection &cFAILED");
        }
    }

    public void disconnect() {
        try {
            if(connection != null) {
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
