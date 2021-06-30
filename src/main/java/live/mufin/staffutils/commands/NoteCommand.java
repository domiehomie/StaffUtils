package live.mufin.staffutils.commands;

import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.StaffUtils;
import live.mufin.staffutils.database.PostgreSQLConnect;
import live.mufin.staffutils.utils.Logging;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class NoteCommand implements CommandExecutor, MCM {
    @Override
    public String commandName() {
        return "sunote";
    }

    @Override
    public String[] commandAliases() {
        return null;
    }

    @Override
    public String usage() {
        return "/sunote <player> <note>";
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
        if(args.length < 2) {
            StaffUtils.core.sendFormattedMessage(sender, "&cYou have to supply a player and a message.");
            return true;
        }
        Player p = Bukkit.getPlayer(args[0]);
        if(p == null) {
            StaffUtils.core.sendFormattedMessage(sender, "&cInvalid player.");
            return true;
        }
        UUID uuid = p.getUniqueId();
        List<String> notes = new LinkedList<>(Arrays.asList(args));
        notes.remove(0);
        String note = String.join(" ", notes);
        try {
            PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("INSERT INTO notes(uuid, note) VALUES (?, ?)");
            ps.setString(1, uuid.toString());
            ps.setString(2, note);
            ps.executeUpdate();
            StaffUtils.core.sendFormattedMessage(sender, "Successfully added the note &d" + note + "&7 to &d" + uuid + "&7.");
            Logging.Log("NOTE - " + sender.getName() + " added note " + note + " to " + p.getName() + ".");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
}
