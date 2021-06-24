package live.mufin.staffutils;

import live.mufin.MufinCore.MufinCore;
import live.mufin.staffutils.Database.PostgreSQLConnect;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Staffutils extends JavaPlugin {

    public static Staffutils instance;
    public static MufinCore core;

    @Override
    public void onEnable() {
        instance = this;
        core = new MufinCore(this, "StaffUtils", ChatColor.LIGHT_PURPLE, "SU");
        PostgreSQLConnect connect = new PostgreSQLConnect();
        connect.connect();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
