package live.mufin.staffutils.commands;

import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.TrustScore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.nio.Buffer;

public class addtrustscorecmd implements CommandExecutor, MCM {
    //TODO remove this

    @Override
    public String commandName() {
        return "addtrustscore";
    }

    @Override
    public String[] commandAliases() {
        return null;
    }

    @Override
    public String usage() {
        return "/addtrustscore <player> <amount>";
    }

    @Override
    public String description() {
        return "adds or removes trustscore from a player";
    }

    @Override
    public String permission() {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = Bukkit.getPlayer(args[0]);
        TrustScore.addToTrustScore(p.getUniqueId(), Integer.parseInt(args[1]));
        return true;
    }
}
