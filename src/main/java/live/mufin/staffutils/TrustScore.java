package live.mufin.staffutils;

import live.mufin.staffutils.Database.PostgreSQLConnect;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TrustScore {

    public static int getTrustScore(UUID uuid) {
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT * from players WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(3);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void addToTrustScore(UUID uuid, int amount) {
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("UPDATE players SET trustscore=? WHERE uuid=?");
            ps.setInt(1, getTrustScore(uuid) + amount);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
            checkTrustScore(uuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void checkTrustScore(UUID uuid) {
        if(getTrustScore(uuid) <= 0) {
            Player p = Bukkit.getPlayer(uuid);
            if(p == null) throw new IllegalArgumentException("Invalid player");
            p.kickPlayer(ChatColor.translateAlternateColorCodes('&', "You have been &dbanned&f!\n\nBanned by:" +
                    " &dConsole\n&fReason: &dTrust score too low. Apply for an unban.\n&fDuration: &dForever"));

            Bukkit.getBanList(BanList.Type.NAME).addBan(uuid.toString(), ChatColor.translateAlternateColorCodes('&', "You have been &dbanned&f!\n\nBanned by:" +
                    " &dConsole\n&fReason: &dTrust score too low. Apply for an unban.\n&fDuration: &dForever"), null, null);
            Logging.Log("BAN - Console has banned " + p.getName() + " for a trust score below 1.");
        }
    }
}
