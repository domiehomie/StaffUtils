package live.mufin.staffutils;

import live.mufin.MufinCore.MufinCore;
import live.mufin.MufinCore.commands.MCM;
import live.mufin.staffutils.commands.*;
import live.mufin.staffutils.commands.TabCompleters.CreateTicketTabCompleter;
import live.mufin.staffutils.commands.TabCompleters.RemovePunishmentTabCompleter;
import live.mufin.staffutils.commands.TabCompleters.SetTicketStatusTabCompleter;
import live.mufin.staffutils.commands.TabCompleters.ViewTicketsTabCompleter;
import live.mufin.staffutils.commands.tabcompleters.ReportTabCompleter;
import live.mufin.staffutils.commands.tickets.*;
import live.mufin.staffutils.database.PostgreSQLConnect;
import live.mufin.staffutils.database.Tables;
import live.mufin.staffutils.events.ChatEvent;
import live.mufin.staffutils.events.JoinEvent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class StaffUtils extends JavaPlugin {

    public static StaffUtils instance;
    public static MufinCore core;

    public static FileConfiguration config;

    private PostgreSQLConnect db;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        config = this.getConfig();
        core = new MufinCore(this, "StaffUtils", ChatColor.LIGHT_PURPLE, "SU");
        db = new PostgreSQLConnect();
        this.registerDBAndTables();
        this.registerCommands();
        this.registerEvents();

    }

    @Override
    public void onDisable() {
        db.disconnect();
    }

    public void registerCommands() {
        core.registerCommands(new MCM[]{new NoteCommand(), new WarnCommand(), new PlayerCommand(), new KickCommand(),
                new MuteCommand(), new RemovePunishmentCommand(), new BanCommand(),
                new CreateTicketCommand(),new SetTicketStatusCommand(),new ViewTicketsCommand(),
                new OpenTicketCommand(), new RespondInTicketCommand(),
                new ReportCommand(), new GetReportsCommand(), new CloseReportCommand()});
        getCommand("sunote").setExecutor(new NoteCommand());
        getCommand("suwarn").setExecutor(new WarnCommand());
        getCommand("suplayer").setExecutor(new PlayerCommand());
        getCommand("sukick").setExecutor(new KickCommand());
        getCommand("sumute").setExecutor(new MuteCommand());
        getCommand("suban").setExecutor(new BanCommand());
        getCommand("removepunishment").setExecutor(new RemovePunishmentCommand());
        getCommand("sureport").setExecutor(new ReportCommand());
        getCommand("sugetreports").setExecutor(new GetReportsCommand());
        getCommand("suclosereport").setExecutor(new CloseReportCommand());

        getCommand("createticket").setExecutor(new CreateTicketCommand());
        getCommand("viewtickets").setExecutor(new ViewTicketsCommand());
        getCommand("setticketstatus").setExecutor(new SetTicketStatusCommand());
        getCommand("openticket").setExecutor(new OpenTicketCommand());
        getCommand("respondinticket").setExecutor(new RespondInTicketCommand());


        getCommand("createticket").setTabCompleter(new CreateTicketTabCompleter());
        getCommand("viewtickets").setTabCompleter(new ViewTicketsTabCompleter());
        getCommand("setticketstatus").setTabCompleter(new SetTicketStatusTabCompleter());
        getCommand("removepunishment").setTabCompleter(new RemovePunishmentTabCompleter());
        getCommand("sureport").setTabCompleter(new ReportTabCompleter());
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
    }

    public void registerDBAndTables() {
        db.connect();
        Tables tables = new Tables();
        tables.createNotesTable();
        tables.createWarningsTable();
        tables.createTicketsTable();
        tables.createTicketMessagesTable();
        tables.createMutesTable();
        tables.createPlayersTable();
        tables.createBansTable();
        tables.createReportsTable();
    }
}
