package fr.first92.murder.events;

import fr.first92.murder.minigames.CompleteGUI;
import fr.first92.murder.minigames.CourantGUI;
import fr.first92.murder.roles.RolesManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();

        if(e.getMessage().contains("link")) new CourantGUI(p).open();
        if(e.getMessage().contains("complete")) new CompleteGUI(p).open();

        if(!new RolesManager().hisPlayersAlive(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
}
