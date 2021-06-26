package live.mufin.staffutils.commands;

import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.utils.Logging;
import live.mufin.staffutils.StaffUtils;
import live.mufin.staffutils.utils.TrustScore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class KickCommand implements CommandExecutor, MCM {
    @Override
    public String commandName() {
        return "sukick";
    }

    @Override
    public String[] commandAliases() {
        return null;
    }

    @Override
    public String usage() {
        return "/sukick <player> <reason>";
    }

    @Override
    public String description() {
        return "Kicks a player from the server.";
    }

    @Override
    public String permission() {
        return "staffutils.kick";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        if(args.length < 2) {StaffUtils.core.sendFormattedMessage(sender, "&cYou need to supply a player and a reason."); return true;}
        Player p = Bukkit.getPlayer(args[0]);
        if(p==null) { StaffUtils.core.sendFormattedMessage(sender, "Invalid player."); return true; }

        List<String> reasoning = new LinkedList<>(Arrays.asList(args));
        reasoning.remove(0);
        String reason = String.join(" ", reasoning);

        p.kickPlayer(ChatColor.translateAlternateColorCodes('&', "You were kicked.\n\nKicked by:" +
                " &d" +sender.getName() + "&f\nReason: &d" + reason));

        Logging.Log("KICK - " + sender.getName() + " just kicked " + p.getName() + " for " + reason + ".");
        TrustScore.addToTrustScore(p.getUniqueId(), -7);
        return true;
    }
}
