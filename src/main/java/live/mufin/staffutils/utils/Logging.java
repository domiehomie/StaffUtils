package live.mufin.staffutils.utils;

import live.mufin.staffutils.StaffUtils;
import org.bukkit.Bukkit;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {



    public static void Log(String content) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        try {
            FileWriter myWriter = new FileWriter(StaffUtils.instance.getDataFolder() + "/logs.txt", true);
            myWriter.write("[" + dtf.format(now) + "] " + content + "\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bukkit.getOnlinePlayers().forEach(player -> {
            if(player.hasPermission("staffutils.log")) {
                StaffUtils.core.sendFormattedMessage(player, "&8[" + dtf.format(now) + "]&7 " + content + "\n");
            }
        });
    }
}
