package live.mufin.staffutils.utils;

import live.mufin.staffutils.database.PostgreSQLConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Reports {

    public static void createReport(UUID target, UUID reporter, String reason) {
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("INSERT INTO reports (uuid, report, reportedby) VALUES (?, ?, ?)");
            ps.setString(1, target.toString());
            ps.setString(2, reason);
            ps.setString(3, reporter.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Report> getReports() {
        try {
            List<Report> reports = new ArrayList<>();
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT * FROM reports WHERE is_open=?");
            ps.setBoolean(1, true);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reports.add(new Report(rs.getInt(1), UUID.fromString(rs.getString(2).replaceAll(" ", "")), UUID.fromString(rs.getString(4).replaceAll(" ", "")), rs.getString(3).replaceAll(" ", ""), rs.getTimestamp(5)));
            }
            return reports;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Report> getReportsOnPlayer(UUID uuid) {
        List<Report> reports = new ArrayList<>();
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT * FROM reports WHERE uuid=? AND is_open=?");
            ps.setString(1, uuid.toString());
            ps.setBoolean(2, true);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reports.add(new Report(rs.getInt(1), UUID.fromString(rs.getString(2).replaceAll(" ", "")), UUID.fromString(rs.getString(4).replaceAll(" ", "")), rs.getString(3).replaceAll(" ", ""), rs.getTimestamp(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reports;
    }

    public static void closeReport(int report) {
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("UPDATE reports SET is_open=? WHERE id=?");
            ps.setBoolean(1, false);
            ps.setInt(2, report);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean exists(int report) {
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT * FROM reports WHERE id=?");
            ps.setInt(1, report);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getBoolean(6);
            } else return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static class Report {
        int id;
        UUID target;
        UUID reporter;
        String reason;
        Timestamp time;

        protected Report(int id, UUID target, UUID reporter, String reason, Timestamp time) {
            this.id = id;
            this.target = target;
            this.reporter = reporter;
            this.reason = reason;
            this.time = time;
        }

        public int getId() {
            return id;
        }

        public UUID getTarget() {
            return target;
        }

        public UUID getReporter() {
            return reporter;
        }

        public String getReason() {
            return reason;
        }

        public Timestamp getTime() {
            return time;
        }
    }
}
