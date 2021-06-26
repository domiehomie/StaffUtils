package live.mufin.staffutils.commands.TabCompleters;

import live.mufin.staffutils.utils.Tickets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class SetTicketStatusTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> results = new ArrayList<>();
        if(strings.length == 2) {
            for(Tickets.Status status : Tickets.Status.values()) {
                String result = status.toString();
                if(result.toUpperCase().startsWith(strings[1].toUpperCase()))
                    results.add(result);
            }
            return results;
        }
        return null;
    }
}
