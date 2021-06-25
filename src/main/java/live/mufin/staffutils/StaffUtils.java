package live.mufin.staffutils;

import live.mufin.MufinCore.MufinCore;
import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.Database.PostgreSQLConnect;
import live.mufin.staffutils.Database.Tables;
import live.mufin.staffutils.commands.*;
import live.mufin.staffutils.events.JoinEvent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class StaffUtils extends JavaPlugin {

    public static StaffUtils instance;
    public static MufinCore core;

    public static FileConfiguration config;

    private PostgreSQLConnect db = new PostgreSQLConnect();

    @Override
    public void onEnable() {
        instance = this;
        core = new MufinCore(this, "StaffUtils", ChatColor.LIGHT_PURPLE, "SU");
        this.saveDefaultConfig();
        config = this.getConfig();
        this.registerDBAndTables();
        this.registerCommands();
        this.registerEvents();
    }

    @Override
    public void onDisable() {
        db.disconnect();
    }

    public void registerCommands() {
        core.registerCommands(new MCM[]{new NoteCommand(), new WarnCommand(), new PlayerCommand(), new KickCommand(), new addtrustscorecmd()});
        getCommand("sunote").setExecutor(new NoteCommand());
        getCommand("suwarn").setExecutor(new WarnCommand());
        getCommand("suplayer").setExecutor(new PlayerCommand());
        getCommand("sukick").setExecutor(new KickCommand());
        getCommand("addtrustscore").setExecutor(new addtrustscorecmd());
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
    }

    public void registerDBAndTables() {
        db.connect();
        Tables tables = new Tables();
        tables.createNotesTable();
        tables.createWarningsTable();
    }
}
