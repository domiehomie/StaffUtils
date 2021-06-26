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

public class CreateTicketCommand implements CommandExecutor, MCM {
    @Override
    public String commandName() {
        return "createticket";
    }

    @Override
    public String[] commandAliases() {
        return null;
    }

    @Override
    public String usage() {
        return "/createticket <type> <message>";
    }

    @Override
    public String description() {
        return "Creates a support ticket.";
    }

    @Override
    public String permission() {
        return "staffutils.tickets.create";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!(commandSender instanceof Player)) return true;
        if(args.length < 2) {  StaffUtils.core.sendFormattedMessage(commandSender, "&cPlease supply a type and message."); return true; }
        List<String> types = StaffUtils.config.getStringList("tickets.types");
        if(!types.contains(args[0])) {StaffUtils.core.sendFormattedMessage(commandSender, "&cInvalid ticket type."); return true; }
        String type = args[0];
        List<String> msges = new LinkedList<>(Arrays.asList(args));
        msges.remove(0);
        String message = String.join(" ", msges);
        int id = Tickets.createTicket(type, message, (Player) commandSender);

        StaffUtils.core.sendFormattedMessage(commandSender, "Created new ticket with id #&d" + id);
        Logging.Log("TICKET - Ticket created with id " + id + " by " + commandSender.getName() + ".");
        return true;
    }
}
