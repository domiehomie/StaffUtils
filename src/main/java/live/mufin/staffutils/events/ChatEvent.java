package live.mufin.staffutils.events;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import live.mufin.staffutils.StaffUtils;
import live.mufin.staffutils.utils.Mutes;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;

public class ChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        boolean muted = Mutes.isMuted(e.getPlayer().getUniqueId());
        if(muted) {
            e.setCancelled(true);
            StaffUtils.core.sendFormattedMessage(e.getPlayer(), "&cYou cannot talk, because you are muted.");
        } else {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://community-purgomalum.p.rapidapi.com/json?text=" + e.getMessage() + "&fill_char=*")
                    .get()
                    .addHeader("x-rapidapi-key", "b48aee06b2msh79385c5252e1dd6p11a13djsn8ec7b7e133c9")
                    .addHeader("x-rapidapi-host", "community-purgomalum.p.rapidapi.com")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String json = response.body().string();
                Gson gson = new Gson();
                Message msg = gson.fromJson(json, new TypeToken<Message>(){}.getType());
                e.setMessage(msg.getResult());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    class Message {
        String result;

        public String getResult() {
            return result;
        }
    }

}
