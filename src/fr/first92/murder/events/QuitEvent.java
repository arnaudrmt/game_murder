package fr.first92.murder.events;

import fr.first92.murder.handlers.WinHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    public void onQuit(PlayerQuitEvent e) {

        new WinHandler().eliminate(e.getPlayer());
    }
}
