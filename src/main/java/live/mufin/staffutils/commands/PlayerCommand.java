package live.mufin.staffutils.commands;

import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.Database.PostgreSQLConnect;
import live.mufin.staffutils.StaffUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerCommand implements CommandExecutor, MCM {
    @Override
    public String commandName() {
        return "suplayer";
    }

    @Override
    public String[] commandAliases() {
        return null;
    }

    @Override
    public String usage() {
        return "/suplayer <player>";
    }

    @Override
    public String description() {
        return "Gives you all the information about a specific player.";
    }

    @Override
    public String permission() {
        return "staffutils.getplayer";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<String> warnings = new ArrayList<>();
        List<String> notes = new ArrayList<>();
        if(!(sender instanceof Player)) return true;
        Player p = Bukkit.getPlayer(args[0]);
        if(p==null) return true;
        StaffUtils.core.sendFormattedMessage(sender, "Warnings: &d" + getWarningAmount(p.getUniqueId()));
        StaffUtils.core.sendFormattedMessage(sender, "Notes: &d" + getNoteAmount(p.getUniqueId()));
        return true;
    }

    private int getWarningAmount(UUID uuid) {
        try {
            int count = 0;
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT count(*) FROM warnings WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            rs.next();
            count = rs.getInt(1);
            return count;
        } catch (SQLException ignored) { }
        return 0;
    }

    private int getNoteAmount(UUID uuid) {
        try {
            int count = 0;
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT count(*) FROM notes WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            rs.next();
            count = rs.getInt(1);
            return count;
        } catch (SQLException ignored) { }
        return 0;
    }


    private List<String> getWarnings(UUID uuid) {
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT * FROM warnings WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            List<String> warnings = new ArrayList<>();
            while(rs.next()) {
                System.out.println(rs.getString(3));
                warnings.add(rs.getString(3));
            }
            return warnings;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
