package live.mufin.staffutils.commands;

import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.Database.PostgreSQLConnect;
import live.mufin.staffutils.StaffUtils;
import live.mufin.staffutils.utils.Mutes;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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
        OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
        if(p==null) {StaffUtils.core.sendFormattedMessage(sender, "&cInvalid player."); return true;}
        if(args.length == 2) {
            if(args[1].equalsIgnoreCase("warnings")) {
                List<String> warnings = getWarnings(p.getUniqueId());
                if(warnings.isEmpty()) StaffUtils.core.sendFormattedMessage(sender, "&cThat player does not have any warnings.");
                for (int i = 0; i < warnings.size(); i++) {
                    StaffUtils.core.sendFormattedMessage(sender, (i + 1) + " &d" + warnings.get(i));
                }
            }else if(args[1].equalsIgnoreCase("notes")) {
                List<String> notes = getNotes(p.getUniqueId());
                if(notes.isEmpty()) StaffUtils.core.sendFormattedMessage(sender, "&cThat player does not have any warnings.");
                for (int i = 0; i < notes.size(); i++) {
                    StaffUtils.core.sendFormattedMessage(sender, (i + 1) + " &d" + notes.get(i));
                }
            }
        } else if(args.length == 1) {
            StaffUtils.core.sendDividerMessage(sender);
            StaffUtils.core.sendFormattedMessage(sender, "Trust score: &d" + getTrustScore(p.getUniqueId()));
            StaffUtils.core.sendFormattedMessage(sender, "Warnings: &d" + getWarnings(p.getUniqueId()).size());
            StaffUtils.core.sendFormattedMessage(sender, "Notes: &d" + getNotes(p.getUniqueId()).size());
            StaffUtils.core.sendFormattedMessage(sender, "Muted: &d" + Mutes.isMuted(p.getUniqueId()));
            StaffUtils.core.sendDividerMessage(sender);
        }
        return true;
    }

    //TODO add all types
    private int getTrustScore(UUID uuid) {
        try {
            int score = 0;
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT trustscore FROM players WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException ignored) { }
        return 0;
    }

    private List<String> getWarnings(UUID uuid) {
        List<String> warnings = new ArrayList<>();
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT * FROM warnings WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                warnings.add(rs.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return warnings;
    }

    private List<String> getNotes(UUID uuid) {
        List<String> notes = new ArrayList<>();
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("SELECT * FROM notes WHERE uuid=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                notes.add(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }
}
