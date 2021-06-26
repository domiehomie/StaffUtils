package live.mufin.staffutils.commands;

import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.Database.PostgreSQLConnect;
import live.mufin.staffutils.utils.Logging;
import live.mufin.staffutils.StaffUtils;
import live.mufin.staffutils.utils.TrustScore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class WarnCommand implements CommandExecutor, MCM {
    @Override
    public String commandName() {
        return "suwarn";
    }

    @Override
    public String[] commandAliases() {
        return null;
    }

    @Override
    public String usage() {
        return "/suwarn <player> <message>";
    }

    @Override
    public String description() {
        return "Warn a player when they do something wrong.";
    }

    @Override
    public String permission() {
        return "staffutils.addwarning";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 2) {
            StaffUtils.core.sendFormattedMessage(sender, "&cYou have to supply a player and a reason.");
            return true;
        }
        Player p = Bukkit.getPlayer(args[0]);
        if(p == null) {
            StaffUtils.core.sendFormattedMessage(sender, "&cInvalid player.");
            return true;
        }
        UUID uuid = p.getUniqueId();
        List<String> reasoning = new LinkedList<>(Arrays.asList(args));
        reasoning.remove(0);
        String reason = String.join(" ", reasoning);
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("INSERT INTO warnings(uuid, reason) VALUES (?, ?)");
            ps.setString(1, uuid.toString());
            ps.setString(2, reason);
            ps.executeUpdate();

            StaffUtils.core.sendFormattedMessage(sender, "Warned &d" + p.getName() + "&7 for &d" + reason + "&7.");

            if(StaffUtils.config.getBoolean("warnings.globally-announce")) Bukkit.broadcastMessage("&d" + p.getName() + "&7 has been warned for &d" + reason + "&7.");
            if(StaffUtils.config.getBoolean("warnings.announce-to-player")) StaffUtils.core.sendFormattedMessage(p, "&7You have been warned for &d" + reason + "&7.");

            Logging.Log("WARNING - " + sender.getName() + " warned " + p.getName() + " for " + reason + ".");
            TrustScore.addToTrustScore(uuid, -3);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
}
