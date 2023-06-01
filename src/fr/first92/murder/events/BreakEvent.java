package fr.first92.murder.events;

import fr.first92.murder.Murder;
import fr.first92.murder.handlers.Variables;
import fr.first92.murder.roles.RolesList;
import fr.first92.murder.roles.RolesManager;
import fr.first92.murder.utils.MapUtils;
import org.bukkit.Location;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

import java.util.Arrays;

public class BreakEvent implements Listener {

    Variables var = Murder.getInstance().getVariables();

    @EventHandler
    public void onPaintingBreak(HangingBreakByEntityEvent e) {

        if(!(e.getEntity() instanceof Painting)) return;
        if(!(e.getRemover() instanceof Player)) return;
        if(!new RolesManager().hasPlayerRole(((Player) e.getRemover()).getPlayer())) return;

        Player p = ((Player) e.getRemover()).getPlayer();

        String emplacement = "house";
        Location closest = new Location(p.getWorld(), 0, 0, 0);

        for(Location loc : var.getRoomsLocations()) {

            if(loc.distanceSquared(p.getLocation()) < closest.distanceSquared(p.getLocation()))
                closest = loc;
        }

        for(Location loc : var.getTunnelLocations()) {

            if(loc.distanceSquared(p.getLocation()) < closest.distanceSquared(p.getLocation())) {

                closest = loc;
                emplacement = "basement";
            }
        }

        String name = emplacement.equals("house") ? new MapUtils().getKey(var.getRoomsMap(), var.getRoomsLocations().get(var.getRoomsLocations().indexOf(closest))) :
                new MapUtils().getKey(var.getTunnelPointsMap(), var.getTunnelLocations().get(var.getTunnelLocations().indexOf(closest)));

        p.teleport(emplacement.equals("house") ? var.getTunnelPointsMap().get(name) : var.getRoomsMap().get(name));

        if(!new RolesManager().getPlayerRole(p).getSide().equals(RolesList.RoleSides.BAD)) {

            Arrays.stream(RolesList.values()).filter(r -> r.getSide().equals(RolesList.RoleSides.BAD)).forEach(r -> r.getPlayersAlive().forEach(all -> all.sendMessage("§cAn intruder entered the basement!")));
        }

        e.setCancelled(true);
    }
}
