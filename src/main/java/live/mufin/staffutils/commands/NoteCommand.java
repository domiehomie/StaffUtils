package live.mufin.staffutils.commands;

import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.Staffutils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NoteCommand implements CommandExecutor, MCM {
    @Override
    public String commandName() {
        return "note";
    }

    @Override
    public String[] commandAliases() {
        return null;
    }

    @Override
    public String usage() {
        return "/note add|remove <player> <note>";
    }

    @Override
    public String description() {
        return "Lets you add a note to a specific player.";
    }

    @Override
    public String permission() {
        return "staffutils.addnote";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 3) Staffutils.core.sendFormattedMessage(sender, Staffutils.core.getMCMFromName("note").usage());
        Player target = Bukkit.getPlayer(args[1]);
        if(target == null) Staffutils.core.sendFormattedMessage(sender, "&cInvalid player.");
        switch(args[0]) {
            case "add": {
                if(Staffutils.players.getConfig().contains(target.getUniqueId() + ".notes")) {
                    Staffutils.players.getConfig().getStringList(target.getUniqueId() + ".notes");

                }
            }
        }
        return true;
    }
}
