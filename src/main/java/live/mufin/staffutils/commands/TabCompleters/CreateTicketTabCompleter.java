package live.mufin.staffutils.commands.TabCompleters;

import live.mufin.staffutils.StaffUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CreateTicketTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> types = StaffUtils.config.getStringList("tickets.types");

        List<String> results = new ArrayList<>();
        if(strings.length == 1) {
            for(String result : types) {
                if(result.toUpperCase().startsWith(strings[0].toUpperCase()))
                    results.add(result);
            }
            return results;
        }
        return null;
    }
}
