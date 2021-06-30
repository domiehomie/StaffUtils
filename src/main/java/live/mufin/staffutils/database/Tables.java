package live.mufin.staffutils.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Tables {

    Connection connection = PostgreSQLConnect.getConnection();
    PreparedStatement ps;

    public void createNotesTable() {
        try {
            ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Notes(ID SERIAL PRIMARY KEY,UUID CHAR(50) ,NOTE VARCHAR(250))");
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createWarningsTable() {
        try {
            ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Warnings(ID SERIAL PRIMARY KEY,UUID CHAR(50),REASON VARCHAR(250))");
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createMutesTable() {
        try {
            ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Mutes(ID SERIAL PRIMARY KEY,UUID CHAR(50),MUTEDAT TIMESTAMP DEFAULT CURRENT_TIMESTAMP, MUTEDBY CHAR(50), MUTEDUNTIL TIMESTAMP, MUTEDFOR CHAR(100), IS_ACTIVE BOOLEAN DEFAULT TRUE)");
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createBansTable() {
        try {
            ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Bans(ID SERIAL PRIMARY KEY, UUID CHAR(50),BANNEDAT TIMESTAMP DEFAULT CURRENT_TIMESTAMP , BANNEDBY CHAR(50), BANNEDUNTIL TIMESTAMP, BANNEDFOR CHAR(100), PERMBAN BOOLEAN DEFAULT FALSE, IS_ACTIVE BOOLEAN DEFAULT TRUE)");
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createReportsTable() {
        try {
            ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Reports(ID SERIAL PRIMARY KEY,UUID CHAR(50) ,REPORT VARCHAR(250),REPORTEDBY CHAR(100), REPORTEDON TIMESTAMP DEFAULT CURRENT_TIMESTAMP, IS_OPEN BOOLEAN DEFAULT TRUE)");
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void createTicketsTable() {
        try {
            ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Tickets(ID SERIAL PRIMARY KEY, UUID CHAR(50),TYPE CHAR(50), STATUS CHAR(50))");
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTicketMessagesTable() {
        try {
            ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS TicketMessages(ID SERIAL PRIMARY KEY, UUID CHAR(50),TICKET INT, MESSAGE CHAR(250))");
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createPlayersTable() {
        try {
            ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Players(ID SERIAL PRIMARY KEY,UUID CHAR(50),TRUSTSCORE INT, LASTLOGGEDIN DATE, CLAIMEDSCORE INT DEFAULT 0)");
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
