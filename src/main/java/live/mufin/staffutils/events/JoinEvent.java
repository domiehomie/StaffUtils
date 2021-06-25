package live.mufin.staffutils.events;

import live.mufin.staffutils.Database.PostgreSQLConnect;
import live.mufin.staffutils.StaffUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        System.out.println("test");
        if(!exists(e.getPlayer().getUniqueId())) {
            try {
                System.out.println(1);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime now = LocalDateTime.now();
                String date = dtf.format(now);
                PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("INSERT INTO players(uuid, trustscore, lastloggedin) VALUES (?, ?, ?)");
                ps.setString(1, e.getPlayer().getUniqueId().toString());
                ps.setInt(2, 75);
                ps.setDate(3, Date.valueOf(date));
                ps.executeUpdate();
                System.out.println(2);
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
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            String dt = dtf.format(now);
            if(date.before(Date.valueOf(dt))) {
                //TODO check if the thing goes up
                if(rs.getInt(3) >= 100) return;
                PreparedStatement ps1 = PostgreSQLConnect.getConnection().prepareStatement("UPDATE players SET trustscore=? WHERE uuid=?");
                ps1.setInt(1, rs.getInt(3 + 1));
                ps1.setString(2, e.getPlayer().getUniqueId().toString());
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
