package live.mufin.staffutils.commands;

import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.StaffUtils;
import live.mufin.staffutils.utils.Reports;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class GetReportsCommand implements CommandExecutor, MCM {
    @Override
    public String commandName() {
        return "sugetreports";
    }

    @Override
    public String[] commandAliases() {
        return null;
    }

    @Override
    public String usage() {
        return "/sugetreports [player]";
    }

    @Override
    public String description() {
        return "Gives you all current open reports.";
    }

    @Override
    public String permission() {
        return "staffutils.reports.getall";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        List<Reports.Report> reports;
        if(args.length == 0) reports = Reports.getReports();
        else {
            UUID uuid = Bukkit.getOfflinePlayer(args[0]).getUniqueId();
            reports = Reports.getReportsOnPlayer(uuid);
        }
        StaffUtils.core.sendDividerMessage(commandSender);
        reports.forEach(report -> {
            StaffUtils.core.sendFormattedMessage(commandSender, report.getId() + ". &d" + report.getReason() + "&7 on &d" + Bukkit.getOfflinePlayer(report.getTarget()).getName() + "&7 at &d" + report.getTime());
        });
        if(reports.isEmpty()) StaffUtils.core.sendFormattedMessage(commandSender, "No more reports!");

        StaffUtils.core.sendDividerMessage(commandSender);
        return true;
    }
}
