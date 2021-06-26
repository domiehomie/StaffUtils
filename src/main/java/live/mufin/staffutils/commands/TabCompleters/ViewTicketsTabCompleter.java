package live.mufin.staffutils.commands.TabCompleters;

import live.mufin.staffutils.utils.Tickets;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ViewTicketsTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> values = new ArrayList<>();
        for (Tickets.Status value : Tickets.Status.values()) {
            values.add(value.toString());
        }
        values.add("ALL");

        List<String> results = new ArrayList<>();
        if(strings.length == 1) {
            for(String result : values) {
                if(result.toUpperCase().startsWith(strings[0].toUpperCase()))
                    results.add(result);
            }
            return results;
        }
        return null;
    }
}
