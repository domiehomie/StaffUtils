package live.mufin.staffutils.commands.tickets;

import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.utils.Logging;
import live.mufin.staffutils.StaffUtils;
import live.mufin.staffutils.utils.Tickets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetTicketStatusCommand implements CommandExecutor, MCM {
    @Override
    public String commandName() {
        return "setticketstatus";
    }

    @Override
    public String[] commandAliases() {
        return null;
    }

    @Override
    public String usage() {
        return "/setticketstatus <ticket> <status>";
    }

    @Override
    public String description() {
        return "Allows you to set the status of a ticket. Options are OPEN, IN_PROGRESS and CLOSED.";
    }

    @Override
    public String permission() {
        return "staffutils.tickets.setstatus";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(args.length < 2) { StaffUtils.core.sendFormattedMessage(sender, "&cPlease supply a ticket number and the status."); return true; }
        try {
            int ticket = Integer.parseInt(args[0]);
            Tickets.Status status = Tickets.Status.valueOf(args[1]);
            if(Tickets.setTicketStatus(ticket, status)) {
                StaffUtils.core.sendFormattedMessage(sender, "Successfully set the status of &d" + ticket +
                        "&7 to &d" + status + "&7.");
                Logging.Log("TICKET - " + sender.getName() + " updated the status of ticket #" + ticket + " to " + status + ".");
            }
            else StaffUtils.core.sendFormattedMessage(sender, "&cInvalid ticket.");
        } catch (IllegalArgumentException e) {
            StaffUtils.core.sendFormattedMessage(sender, "&cInvalid number and/or ticket.");
        }
        return true;
    }
}
