package fr.first92.murder.events;

import fr.first92.murder.Murder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        p.getInventory().clear();
        p.setHealth(20);
        p.setFoodLevel(20);
        p.getActivePotionEffects().forEach(rs -> p.removePotionEffect(rs.getType()));

        p.teleport(Murder.getInstance().getVariables().getLobby());
    }
}
