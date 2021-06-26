package live.mufin.staffutils.utils;

import live.mufin.staffutils.Database.PostgreSQLConnect;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class Mutes {

    public static void mute(Player target, Player muter, long time, String reason) {
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("INSERT INTO mutes (uuid, mutedby, muteduntil, mutedfor) VALUES (?, ?, ?, ?)");
            ps.setString(1, target.getUniqueId().toString());
            ps.setString(2, muter.getUniqueId().toString());
            long newtime = new Timestamp(new Date().getTime()).getTime() + (time * 1000);
            Timestamp ts = new Timestamp(newtime);
            ps.setTimestamp(3, ts);
            ps.setString(4, reason);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isMuted(UUID p) {
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT * FROM mutes WHERE uuid=?");
            ps.setString(1, p.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Timestamp now = new Timestamp(new Date().getTime());
                Timestamp ts = rs.getTimestamp(5);

                if(rs.getBoolean(7)) {
                    if (now.before(ts)) return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
