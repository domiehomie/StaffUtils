package live.mufin.staffutils.utils;

import live.mufin.staffutils.database.PostgreSQLConnect;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tickets {

    public enum Status {
        OPEN, IN_PROGRESS, CLOSED
    }

    public static int createTicket(String type, String message, Player sender) {
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("INSERT INTO tickets (uuid, type, status) VALUES (?, ?, ?)");
            ps.setString(1, sender.getUniqueId().toString());
            ps.setString(2, type);
            ps.setString(3, Status.OPEN.toString());
            ps.executeUpdate();
            PreparedStatement ps1 = PostgreSQLConnect.getConnection().prepareStatement("SELECT * FROM tickets ORDER BY id DESC LIMIT 1");
            ResultSet rs = ps1.executeQuery();
            rs.next();
            int row = rs.getInt(1);
            addTicketMessage(row, sender, message);
            return row;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<Ticket> getAllTickets(Status status){
        List<Ticket> tickets = new ArrayList<>();
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT * FROM tickets WHERE status=?");
            ps.setString(1, status.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tickets.add(new Ticket(rs.getInt(1), rs.getString(2), Status.valueOf(rs.getString(4).replaceAll(" ", "")), rs.getString(3).replaceAll(" ", "")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public static List<Ticket> getAllTickets(){
        List<Ticket> tickets = new ArrayList<>();
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT * FROM tickets");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tickets.add(new Ticket(rs.getInt(1), rs.getString(2), Status.valueOf(rs.getString(4).replaceAll(" ", "")), rs.getString(3).replaceAll(" ", "")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public static Status getTicketStatus(int ticket) {
        if(!exists(ticket)) return null;
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT * FROM tickets WHERE id=?");
            ps.setInt(1, ticket);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return Status.valueOf(rs.getString(4).replaceAll(" ", ""));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean addTicketMessage(int ticket, Player sender, String message) {
        if(!exists(ticket)) return false;
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("INSERT INTO ticketmessages (uuid, ticket, message) VALUES (?, ?, ?)");
            ps.setString(1, sender.getUniqueId().toString());
            ps.setInt(2, ticket);
            ps.setString(3, message);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Message> getMessagesFromTicket(int ticket) {
        List<Message> messages = new ArrayList<>();
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT * FROM ticketmessages WHERE ticket=?");
            ps.setInt(1, ticket);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                messages.add(new Message(ticket, rs.getString(4), (rs.getString(2))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public static boolean setTicketStatus(int ticket, Status status) {
        if(!exists(ticket)) return false;
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("UPDATE tickets SET status=? WHERE id=?");
            ps.setString(1, status.toString());
            ps.setInt(2, ticket);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private static boolean exists(int ticket) {
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT * FROM tickets WHERE id=?");
            ps.setInt(1, ticket);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static class Ticket {
        int id;
        String owner;
        Status status;
        String type;

        protected Ticket(int id, String owner, Status status, String type) {
            this.id = id;
            this.owner = owner;
            this.status = status;
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public String getOwner() {
            return owner;
        }

        public Status getStatus() {
            return status;
        }

        public String getType() {
            return type;
        }
    }

    public static class Message {
        int ticket;
        String message;
        String playeruuid;

        protected Message(int ticket, String message, String playeruuid) {
            this.ticket = ticket;
            this.message = message.replaceAll("  ", "");
            this.playeruuid = playeruuid.replaceAll(" ", "");
        }

        public int getTicket() {
            return ticket;
        }

        public String getMessage() {
            return message;
        }

        public String getPlayeruuid() {
            return playeruuid;
        }
    }
}
