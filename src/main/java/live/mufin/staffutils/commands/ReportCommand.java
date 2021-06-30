package live.mufin.staffutils.commands;

import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.StaffUtils;
import live.mufin.staffutils.utils.Logging;
import live.mufin.staffutils.utils.Reports;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ReportCommand implements CommandExecutor, MCM {
    @Override
    public String commandName() {
        return "sureport";
    }

    @Override
    public String[] commandAliases() {
        return null;
    }

    @Override
    public String usage() {
        return "/sureport <player> <reason>";
    }

    @Override
    public String description() {
        return "Lets you report a player if they are doing something wrong.";
    }

    @Override
    public String permission() {
        return "staffutils.reports.open";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length < 2) {
            StaffUtils.core.sendFormattedMessage(commandSender, "&cYou must supply a player and a reason.");
            return true;
        }
        if(!(commandSender instanceof Player)) return true;
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            StaffUtils.core.sendFormattedMessage(commandSender, "&cThat player is not online.");
            return true;
        }
        UUID reporter = ((Player) commandSender).getUniqueId();
        String reason = args[1];
        if(!StaffUtils.config.getStringList("reports.reasons").contains(reason)) {
            StaffUtils.core.sendFormattedMessage(commandSender, "&cYou must supply a valid reason.");
            return true;
        }
        Reports.createReport(target.getUniqueId(), reporter, reason);
        StaffUtils.core.sendFormattedMessage(commandSender, "Report on &d" + target.getName() + " &7for &d" + reason + " &7created successfully.");
        Logging.Log("REPORT - " + target.getName() + " has been reported for " + reason + " by " + commandSender.getName() + ".");
        return true;
    }
}
