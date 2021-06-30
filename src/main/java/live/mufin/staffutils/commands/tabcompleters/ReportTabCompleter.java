package live.mufin.staffutils.commands.tabcompleters;

import live.mufin.staffutils.StaffUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ReportTabCompleter implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> types = StaffUtils.config.getStringList("reports.reasons");

        List<String> results = new ArrayList<>();
        if(strings.length == 2) {
            for(String result : types) {
                if(result.toUpperCase().startsWith(strings[1].toUpperCase()))
                    results.add(result);
            }
            return results;
        }
        return null;
    }
}
