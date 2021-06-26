package live.mufin.staffutils.commands.TabCompleters;

import live.mufin.staffutils.StaffUtils;
import live.mufin.staffutils.commands.RemovePunishmentCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class RemovePunishmentTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        RemovePunishmentCommand.PunishmentType[] types = RemovePunishmentCommand.PunishmentType.values();

        List<String> results = new ArrayList<>();
        if(strings.length == 1) {
            for(RemovePunishmentCommand.PunishmentType type : types) {
                String result = type.toString();
                if(result.toUpperCase().startsWith(strings[0].toUpperCase()))
                    results.add(result);
            }
            return results;
        }
        return null;
    }
}
