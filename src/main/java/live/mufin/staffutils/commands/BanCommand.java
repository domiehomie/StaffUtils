package live.mufin.staffutils.commands;

import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.StaffUtils;
import live.mufin.staffutils.utils.Bans;
import live.mufin.staffutils.utils.Logging;
import live.mufin.staffutils.utils.Mutes;
import live.mufin.staffutils.utils.TrustScore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BanCommand implements CommandExecutor, MCM {
    @Override
    public String commandName() {
        return "suban";
    }

    @Override
    public String[] commandAliases() {
        return null;
    }

    @Override
    public String usage() {
        return "/suban <player> <duration> <reason>";
    }

    @Override
    public String description() {
        return "Bans a player from the server.";
    }

    @Override
    public String permission() {
        return "staffutils.ban";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)) return true;
        Player sender = (Player) commandSender;
        if(args.length < 3) {
            StaffUtils.core.sendFormattedMessage(commandSender, "&cPlease supply a player, duration and reason.");
            return true;
        }
        Player p = Bukkit.getPlayer(args[0]);
        if(p == null) {StaffUtils.core.sendFormattedMessage(sender, "&cPlayer not found."); return true;}
        if(Bans.isBanned(p.getUniqueId())) {StaffUtils.core.sendFormattedMessage(sender, "&cThat player is already banned."); return true;}
        List<String> chars = new ArrayList<>(Arrays.asList(args[1].split("")));
        char unit = chars.get(chars.size() -1).charAt(0);
        chars.remove(chars.size() - 1);

        List<String> reasons = new LinkedList<>(Arrays.asList(args));
        reasons.remove(0); reasons.remove(0);
        String reason = String.join(" ", reasons);

        if(args[1].equalsIgnoreCase("perm")) {
            if(Bans.isBanned(p.getUniqueId())) {
                StaffUtils.core.sendFormattedMessage(commandSender, "&cThat player is already banned.");
                return true;
            }
            Bans.ban(p.getUniqueId(), reason, sender.getUniqueId());
            TrustScore.addToTrustScore(p.getUniqueId(), -15);
            StaffUtils.core.sendFormattedMessage(sender, "Banned &d" + p.getName() + "&7 for &d" + reason + "&7.");
            Logging.Log("BAN - " + sender.getName() + " has permanently banned" + p.getName() + ".");
            return true;
        }


        try {
            int value = Integer.parseInt(String.join("", chars));
            if(convertToSeconds(value, unit) == 0) { StaffUtils.core.sendFormattedMessage(sender, "&cEither format the time like this: <amount><m|h|d>, or put \"perm\"" +
                    " Example: \"2d\" is two days."); return true;}
            Bans.ban(p.getUniqueId(), reason, convertToSeconds(value, unit), sender.getUniqueId());
            TrustScore.addToTrustScore(p.getUniqueId(), -15);
            StaffUtils.core.sendFormattedMessage(sender, "Banned &d" + p.getName() + "&7 for &d" + reason + "&7.");
            Logging.Log("BAN - " + sender.getName() + " has banned " + p.getName() + " for " + convertToSeconds(value, unit) + " seconds.");
        } catch (NumberFormatException e) {
            StaffUtils.core.sendFormattedMessage(sender, "&cInvalid number.");
        }
        return true;
    }

    private static long convertToSeconds(int value, char unit) {
        long ret = value;
        switch (unit)
        {
            case 'd':
                ret = TimeUnit.DAYS.toSeconds(value);
                break;
            case 'h':
                ret = TimeUnit.HOURS.toSeconds(value);
                break;
            case 'm':
                ret = TimeUnit.MINUTES.toSeconds(value);
                break;
            default:
                return 0;
        }
        return ret;
    }
}
