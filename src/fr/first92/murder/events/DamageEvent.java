package fr.first92.murder.events;

import fr.first92.murder.Murder;
import fr.first92.murder.handlers.WinHandler;
import fr.first92.murder.roles.RolesList;
import fr.first92.murder.roles.RolesManager;
import fr.first92.octaapi.game.gamestate.GameState;
import fr.first92.octaapi.game.gamestate.GameStateManager;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class DamageEvent implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {

        if(!(e.getEntity() instanceof Player)) return;
        if(!(e.getDamager() instanceof Player) && !(e.getDamager() instanceof Arrow)) return;
        if(e.getDamager() instanceof Arrow && !(((Arrow) e.getDamager()).getShooter() instanceof Player)) return;
        if(!(new RolesManager().hasPlayerRole((Player) e.getEntity()))) return;
        if(e.getDamager() instanceof Player && !(new RolesManager().hasPlayerRole((Player) e.getDamager()))) return;
        if(!new GameStateManager().isState(GameState.GAME)) return;
        if(e.getDamager() instanceof Player && !((Player) e.getDamager()).getItemInHand().getType().equals(Material.IRON_SWORD)) return;

        Player target = ((Player) e.getEntity()).getPlayer();
        Player damager;

        if(e.getDamager() instanceof Player) damager = ((Player) e.getDamager()).getPlayer();
        else damager = (Player) ((Arrow) e.getDamager()).getShooter();

        RolesList targetRole = new RolesManager().getPlayerRole(target);
        RolesList damagerRole = new RolesManager().getPlayerRole(damager);

        if(damagerRole.equals(RolesList.DETECTIVE)) {

            if(targetRole.getSide().equals(damagerRole.getSide())) {

                new WinHandler().eliminate(damager);
                new WinHandler().eliminate(target);
            } else new WinHandler().eliminate(target);

        } else {

            if(!(e.getDamager() instanceof Arrow)) {

                if(!targetRole.getSide().equals(damagerRole.getSide())) new WinHandler().eliminate(target);

            } else {

                if(!targetRole.getSide().equals(damagerRole.getSide()))
                    if(target.getHealth() > 7) {
                        target.setHealth(target.getHealth() - 7);
                        return;
                    } else new WinHandler().eliminate(target);
            }
        }

        if(new RolesManager().getPlayerRole(damager).getSide().equals(RolesList.RoleSides.BAD)) {

            if(Murder.getInstance().getVariables().nextVictim() != target) {

                Murder.getInstance().getVariables().addBonusTest();
                RolesList.DETECTIVE.getPlayersAlive().forEach(det -> det.sendMessage("§aThe Murderer(s) didn't follow the list, you have one more test"));
            }

            Murder.getInstance().getVariables().removeVictim(target);
        }
    }

    @EventHandler
    public void onRegeneration(EntityRegainHealthEvent e) {

        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {

        e.setCancelled(true);
    }
}
