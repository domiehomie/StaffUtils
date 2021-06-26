package live.mufin.staffutils.commands.tickets;

import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.utils.Logging;
import live.mufin.staffutils.StaffUtils;
import live.mufin.staffutils.utils.Tickets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RespondInTicketCommand implements CommandExecutor, MCM {
    @Override
    public String commandName() {
        return "respondinticket";
    }

    @Override
    public String[] commandAliases() {
        return null;
    }

    @Override
    public String usage() {
        return "/respondinticket <ticket> <message>";
    }

    @Override
    public String description() {
        return "Lets you send a message in a ticket.";
    }

    @Override
    public String permission() {
        return "staffutils.tickets.respond";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length < 2) {
            StaffUtils.core.sendFormattedMessage(commandSender, "&cYou need to supply a ticket and a message.");
            return true;
        }
        try {
            int ticket = Integer.parseInt(args[0]);
            List<String> msges = new LinkedList<>(Arrays.asList(args));
            msges.remove(0);
            String message = String.join(" ", msges);
            if(Tickets.getTicketStatus(ticket) == Tickets.Status.CLOSED) { StaffUtils.core.sendFormattedMessage(commandSender, "&cThat ticket is closed."); return true; }
            if(Tickets.addTicketMessage(ticket, (Player) commandSender, message)) { StaffUtils.core.sendFormattedMessage(commandSender,
                    "Successfully sent the message in ticket #&d" + ticket + "&7.");
                Logging.Log("TICKET - " + commandSender.getName() + " sent " + message + " in ticket #" + ticket + ".");
            }
            else StaffUtils.core.sendFormattedMessage(commandSender, "&cInvalid ticket.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            StaffUtils.core.sendFormattedMessage(commandSender, "&cInvalid number.");
        }
        return true;
    }
}
