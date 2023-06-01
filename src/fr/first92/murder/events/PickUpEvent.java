package fr.first92.murder.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PickUpEvent implements Listener {

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {

        e.setCancelled(true);
    }
}
