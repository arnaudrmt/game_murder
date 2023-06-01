package fr.first92.murder.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawn implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {

        e.setCancelled(true);
    }
}
