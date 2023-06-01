package fr.first92.murder.armorstand;

import fr.first92.murder.Murder;
import fr.first92.murder.handlers.Variables;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;

public class ArmorStandsHandler {

    Variables var = Murder.getInstance().getVariables();

    public void load(){

        Bukkit.getWorlds().forEach(w -> {

            w.getEntities().stream().filter(e -> e instanceof ArmorStand).forEach(as -> {

                if(as.getCustomName().contains("cage")) var.setCage(as.getLocation());
                else if(as.getCustomName().contains("lobby")) var.setLobby(as.getLocation());

                else if(as.getCustomName().contains("room")) var.addRoom(as.getCustomName().split(":")[1].replaceAll(" ", ""), as.getLocation());
                else if(as.getCustomName().contains("tunnel")) var.addTunnelPoint(as.getCustomName().split(":")[1].replaceAll(" ", ""), as.getLocation());

                else var.addSpawn(as.getLocation());

                as.remove();
            });
        });
    }
}