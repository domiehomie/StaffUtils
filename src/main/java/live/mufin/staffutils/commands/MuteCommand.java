package live.mufin.staffutils.commands;

import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.StaffUtils;
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

public class MuteCommand implements CommandExecutor, MCM {
    @Override
    public String commandName() {
        return "sumute";
    }

    @Override
    public String[] commandAliases() {
        return null;
    }

    @Override
    public String usage() {
        return "/sumute <player> <duration> <reason>";
    }

    @Override
    public String description() {
        return "Allows you to mute a player.";
    }

    @Override
    public String permission() {
        return "staffutils.mute";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player sender)) return true;
        if(args.length < 3) {
            StaffUtils.core.sendFormattedMessage(commandSender, "&cPlease supply a player, duration and reason.");
            return true;
        }
        Player p = Bukkit.getPlayer(args[0]);
        if(p == null) {StaffUtils.core.sendFormattedMessage(sender, "&cPlayer not found."); return true;}
        if(Mutes.isMuted(p.getUniqueId())) {StaffUtils.core.sendFormattedMessage(sender, "&cThat player is already muted."); return true;}
        List<String> chars = new ArrayList<>(Arrays.asList(args[1].split("")));
        char unit = chars.get(chars.size() -1).charAt(0);
        chars.remove(chars.size() - 1);

        List<String> reasons = new LinkedList<>(Arrays.asList(args));
        reasons.remove(0); reasons.remove(0);
        String reason = String.join(" ", reasons);

        try {
            int value = Integer.parseInt(String.join("", chars));
            if(convertToSeconds(value, unit) == 0) { StaffUtils.core.sendFormattedMessage(sender, "&cFormat the time like this: <amount><m|h|d>." +
                    " Example: \"2d\" is two days."); return true;}
            Mutes.mute(p, sender, convertToSeconds(value, unit), reason);
            TrustScore.addToTrustScore(p.getUniqueId(), -7);
            StaffUtils.core.sendFormattedMessage(sender, "Muted &d" + p.getName() + "&7 for &d" + reason + "&7.");
            Logging.Log("MUTE - " + sender.getName() + " has muted " + p.getName() + " for " + convertToSeconds(value, unit) + " seconds.");
        } catch (NumberFormatException e) {
            StaffUtils.core.sendFormattedMessage(sender, "&cInvalid number.");
        }
        return true;
    }



    private static long convertToSeconds(int value, char unit) {
        long ret;
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
            case 's':
                return 0;
            default:
                return 0;
        }
        return ret;
    }

}
