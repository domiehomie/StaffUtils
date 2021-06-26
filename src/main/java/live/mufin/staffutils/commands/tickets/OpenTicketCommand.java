package live.mufin.staffutils.commands.tickets;

import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.StaffUtils;
import live.mufin.staffutils.utils.Tickets;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.UUID;

public class OpenTicketCommand implements CommandExecutor, MCM {
    @Override
    public String commandName() {
        return "openticket";
    }

    @Override
    public String[] commandAliases() {
        return null;
    }

    @Override
    public String usage() {
        return "/openticket <ticket>";
    }

    @Override
    public String description() {
        return "Gives you all the messages that have been sent in a ticket.";
    }

    @Override
    public String permission() {
        return "staffutils.tickets.open";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(args.length < 1) { StaffUtils.core.sendFormattedMessage(sender, "&cYou need to supply a ticket number."); return true; }
        try {
            int ticket = Integer.parseInt(args[0]);
            List<Tickets.Message> messages = Tickets.getMessagesFromTicket(ticket);
            StaffUtils.core.sendDividerMessage(sender);
            messages.forEach(message -> {
                StaffUtils.core.sendFormattedMessage(sender, "&d" + Bukkit.getOfflinePlayer(UUID.fromString(message.getPlayeruuid())).getName() + " &8> &7" + message.getMessage());
            });
            StaffUtils.core.sendDividerMessage(sender);
        } catch(IllegalArgumentException e) {
            StaffUtils.core.sendFormattedMessage(sender, "&cInvalid ticket number.");
        }
        return true;
    }
}
