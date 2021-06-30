package live.mufin.staffutils.commands;

import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.StaffUtils;
import live.mufin.staffutils.utils.Logging;
import live.mufin.staffutils.utils.Reports;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CloseReportCommand implements CommandExecutor, MCM {
    @Override
    public String commandName() {
        return "suclosereport";
    }

    @Override
    public String[] commandAliases() {
        return null;
    }

    @Override
    public String usage() {
        return "/suclosereport <report>";
    }

    @Override
    public String description() {
        return "Closes a report, so it will not show up in the list anymore.";
    }

    @Override
    public String permission() {
        return "staffutils.reports.close";
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length < 1) {
            StaffUtils.core.sendFormattedMessage(commandSender, "&cPlease supply a report.");
            return true;
        }
        try  {
            int report = Integer.parseInt(args[0]);
            if(!Reports.exists(report)) {
                StaffUtils.core.sendFormattedMessage(commandSender, "&cThat report does not exist.");
                return true;
            }
            Reports.closeReport(report);
            StaffUtils.core.sendFormattedMessage(commandSender, "Successfully closed report #&d" + report + "&7.");
            Logging.Log("REPORT - " + commandSender.getName() + " closed report #" + report + ".");
        } catch (NumberFormatException e) {
            StaffUtils.core.sendFormattedMessage(commandSender, "&cInvalid number.");
        }
        return true;
    }
}
