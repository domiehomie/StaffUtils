package live.mufin.staffutils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateTable {

    Connection connection = PostgreSQLConnect.getConnection();
    PreparedStatement ps;

    public void createTable() {
        try {
            ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS players(UUID CHAR(50) PRIMARY KEY ,)");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
