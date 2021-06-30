package live.mufin.staffutils.events;

import live.mufin.staffutils.StaffUtils;
import live.mufin.staffutils.utils.Mutes;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Collections;
import java.util.List;

public class ChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        boolean muted = Mutes.isMuted(e.getPlayer().getUniqueId());
        if(muted) {
            e.setCancelled(true);
            StaffUtils.core.sendFormattedMessage(e.getPlayer(), "&cYou cannot talk, because you are muted.");
        } else {
            String message = e.getMessage();
            List<String> configList = StaffUtils.config.getStringList("chatfilter.blacklist");
            Collections.reverse(configList);
            for (String s : configList) {
                if(message.contains(s)) {
                    message = message.replaceAll(s, "*".repeat(s.length()));
                }
            }
            e.setMessage(message);
        }
    }
}
