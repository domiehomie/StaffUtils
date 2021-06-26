package live.mufin.staffutils.commands;

import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.Database.PostgreSQLConnect;
import live.mufin.staffutils.StaffUtils;
import live.mufin.staffutils.utils.Logging;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class RemovePunishmentCommand implements CommandExecutor, MCM {
    @Override
    public String commandName() {
        return "removepunishment";
    }

    @Override
    public String[] commandAliases() {
        return null;
    }

    @Override
    public String usage() {
        return "/removepunishment <BAN|MUTE> <player>";
    }

    @Override
    public String description() {
        return "Lets you manually remove the punishment on a player.";
    }

    @Override
    public String permission() {
        return "staffutils.removepunishment";
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length < 2) {
            StaffUtils.core.sendFormattedMessage(commandSender, "You must supply a punishment and player.");
            return true;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
        if(target == null) {
            StaffUtils.core.sendFormattedMessage(commandSender, "&cInvalid player.");
            return true;
        }
        try {
            PunishmentType punishment = PunishmentType.valueOf(args[0]);
            this.removePunishment(punishment, target.getUniqueId());
            Logging.Log("PUNISHMENTREMOVE - " + commandSender.getName() + " removed the " + punishment.toString().toLowerCase()
                    + " from " + target.getName() + ".");
            StaffUtils.core.sendFormattedMessage(commandSender, "Successfully removed the &d" + punishment + " &7punishment from &d" + target.getName() + "&7.");
        } catch (IllegalArgumentException e) {
            StaffUtils.core.sendFormattedMessage(commandSender, "&cInvalid punishment.");
        }
        return true;
    }

    public void removePunishment(PunishmentType type, UUID uuid){
        try {
            switch (type) {
                case BAN -> {
                    PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("UPDATE bans SET is_active=? WHERE uuid=?");
                    ps.setBoolean(1, false);
                    ps.setString(2, uuid.toString());
                    ps.executeUpdate();
                }
                case MUTE -> {
                    PreparedStatement ps = PostgreSQLConnect.getConnection().prepareStatement("UPDATE mutes SET is_active=? WHERE uuid=?");
                    ps.setBoolean(1, false);
                    ps.setString(2, uuid.toString());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public enum PunishmentType {
        BAN, MUTE
    }
}
