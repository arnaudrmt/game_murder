package fr.first92.murder.events;

import fr.first92.murder.Murder;
import fr.first92.murder.handlers.Variables;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Arrays;

public class MoveEvent implements Listener {

    Variables var = Murder.getInstance().getVariables();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        Player p = e.getPlayer();

        if(Arrays.stream(p.getInventory().getContents()).filter(rs -> rs != null && rs.hasItemMeta()).anyMatch(rs -> rs.getItemMeta().getDisplayName().contains("Previous"))) {

            Location closest = new Location(p.getWorld(), 0, 0, 0);

            for(Location loc : var.getRoomsLocations()) {

                if(loc.distanceSquared(p.getLocation()) < closest.distanceSquared(p.getLocation()))
                    closest = loc;
            }

            p.teleport(var.getRoomsLocations().get(var.getRoomsLocations().indexOf(closest)));
        }
    }
}
