package live.mufin.staffutils.commands.tickets;

import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.StaffUtils;
import live.mufin.staffutils.utils.Tickets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ViewTicketsCommand implements CommandExecutor, MCM {
    @Override
    public String commandName() {
        return "viewtickets";
    }

    @Override
    public String[] commandAliases() {
        return null;
    }

    @Override
    public String usage() {
        return "/viewtickets <status/ALL>";
    }

    @Override
    public String description() {
        return "Lets you see all current tickets! Supplying no type will result in only open tickets.";
    }

    @Override
    public String permission() {
        return "staffutils.tickets.viewall";
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            List<Tickets.Ticket> tickets = Tickets.getAllTickets(Tickets.Status.OPEN);
            StaffUtils.core.sendDividerMessage(sender);
            tickets.forEach(ticket -> {
                StaffUtils.core.sendFormattedMessage(sender, "#&d" + ticket.getId() + "&7 Status: &d" + ticket.getStatus() + " &7Type: &d" + ticket.getType());
            });
            StaffUtils.core.sendDividerMessage(sender);
        }else {
            if(args[0].equalsIgnoreCase("ALL")) {
                List<Tickets.Ticket> tickets = Tickets.getAllTickets();
                StaffUtils.core.sendDividerMessage(sender);
                tickets.forEach(ticket -> StaffUtils.core.sendFormattedMessage(sender, "#&d" + ticket.getId() + "&7 Status: &d" + ticket.getStatus() + " &7Type: &d" + ticket.getType()));
                StaffUtils.core.sendDividerMessage(sender);
            } else {
                try {
                    Tickets.Status status = Tickets.Status.valueOf(args[0]);
                    List<Tickets.Ticket> tickets = Tickets.getAllTickets(status);
                    StaffUtils.core.sendDividerMessage(sender);
                    tickets.forEach(ticket -> StaffUtils.core.sendFormattedMessage(sender, "#&d" + ticket.getId() + "&7 Status: &d" + ticket.getStatus() + " &7Type: &d" + ticket.getType()));
                    StaffUtils.core.sendDividerMessage(sender);
                } catch (IllegalArgumentException e) {
                    StaffUtils.core.sendFormattedMessage(sender, "&cInvalid status type.");
                }
            }
        }

        return true;
    }
}
