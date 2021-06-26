package live.mufin.staffutils.utils;

import com.mongodb.lang.Nullable;
import live.mufin.staffutils.Database.PostgreSQLConnect;
import live.mufin.staffutils.StaffUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class Bans {

    public static void ban(UUID uuid, String reason, long duration, UUID banner) {
        try{
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("INSERT INTO bans (uuid, bannedby, banneduntil, bannedfor) VALUES (?, ?, ?, ?)");
            ps.setString(1, uuid.toString());
            ps.setString(2, banner.toString());
            long newtime = new Timestamp(new Date().getTime()).getTime() + (duration * 1000);
            Timestamp ts = new Timestamp(newtime);
            ps.setTimestamp(3, ts);
            ps.setString(4, reason);
            ps.executeUpdate();

            String time = ts == null ? "forever" : ts.toString();
            Bukkit.getPlayer(uuid).kickPlayer(ChatColor.translateAlternateColorCodes('&', "You have been &dbanned&f from the server.\n\nReason: &d" + reason + "\n&fBanned by: &d" + Bukkit.getPlayer(banner).getName() +"\n&fBanned until: &d" + time));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void ban(UUID uuid, String reason, @Nullable UUID banner) {
        try{
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("INSERT INTO bans (uuid, bannedby, banneduntil, bannedfor, permban) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, uuid.toString());

            ps.setString(2, banner == null ? "console" : banner.toString());
            ps.setTimestamp(3, null);
            ps.setString(4, reason);
            ps.setBoolean(5, true);
            ps.executeUpdate();

            Bukkit.getPlayer(uuid).kickPlayer(ChatColor.translateAlternateColorCodes('&', "You have been &dbanned&f from the server.\n\nReason: &d" + reason + "\n&fBanned by: &d" + (banner == null ? "console" : Bukkit.getOfflinePlayer(banner).getName()) +"\n&fBan duration: &dpermanent"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isBanned(UUID uuid) {
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT * FROM bans WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Timestamp now = new Timestamp(new Date().getTime());
                Timestamp ts = rs.getTimestamp(5);

                if(rs.getBoolean(8)) {
                    if (ts == null) return true;
                    if (now.before(ts)) return true;
                    return rs.getBoolean(8);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getBanReason(UUID uuid) {
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT * FROM bans WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                while (rs.next()) {

                    Timestamp now = new Timestamp(new Date().getTime());
                    Timestamp ts = rs.getTimestamp(5);

                    if(now.before(ts)) {
                        return rs.getString(6);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

