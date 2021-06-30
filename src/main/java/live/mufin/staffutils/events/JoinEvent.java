package live.mufin.staffutils.events;

import live.mufin.staffutils.StaffUtils;
import live.mufin.staffutils.database.PostgreSQLConnect;
import live.mufin.staffutils.utils.Bans;
import live.mufin.staffutils.utils.TrustScore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(Bans.isBanned(e.getPlayer().getUniqueId())) {
            try {
                PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT * FROM bans WHERE uuid=?");
                ps.setString(1, e.getPlayer().getUniqueId().toString());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Timestamp now = new Timestamp(new java.util.Date().getTime());
                    Timestamp ts = rs.getTimestamp(5);
                    String reason = rs.getString(6).replaceAll("  ", "");
                    UUID banner = rs.getString(4).startsWith("console") ? null : UUID.fromString(rs.getString(4).replaceAll(" ", ""));

                    if(rs.getBoolean(8)) {
                        if (ts == null || now.before(ts)) {
                            String time = ts == null ? "forever" : ts.toString();
                            String msg = "You have been &dbanned&f from the server.\n\nReason: &d" + reason + "\n&fBanned by: &d" + (banner == null ? "console" : Bukkit.getOfflinePlayer(banner).getName()) + "\n&fBanned until: &d" + time;
                            e.getPlayer().kickPlayer(ChatColor.translateAlternateColorCodes('&', msg));
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        if(!exists(e.getPlayer().getUniqueId())) {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime now = LocalDateTime.now();
                String date = dtf.format(now);
                PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("INSERT INTO players(uuid, trustscore, lastloggedin) VALUES (?, ?, ?)");
                ps.setString(1, e.getPlayer().getUniqueId().toString());
                ps.setInt(2, 75);
                ps.setDate(3, Date.valueOf(date));
                ps.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT * FROM players WHERE UUID=?");
            ps.setString(1, e.getPlayer().getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            rs.next();
            Date date = rs.getDate(4);
            int daysclaimed = rs.getInt(5);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            String dt = dtf.format(now);
            if(date.before(Date.valueOf(dt)) && daysclaimed < 25) {
                if(rs.getInt(3) >= 100) return;
                TrustScore.addToTrustScore(e.getPlayer().getUniqueId(), 1);
                PreparedStatement ps1 = PostgreSQLConnect.getConnection().prepareStatement("UPDATE players SET lastloggedin=?, claimedscore=? WHERE uuid=?");
                ps1.setDate(1, Date.valueOf(dt));
                ps1.setInt(2, daysclaimed + 1);
                ps1.setString(3, e.getPlayer().getUniqueId().toString());
                ps1.executeUpdate();
                StaffUtils.core.sendFormattedMessage(e.getPlayer(), "Your current trust score is &d" + (rs.getInt(3) + 1) + "&7.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private boolean exists(UUID uuid) {
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT * FROM players WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
