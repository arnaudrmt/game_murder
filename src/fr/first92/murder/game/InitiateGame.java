package fr.first92.murder.game;

import fr.first92.murder.Murder;
import fr.first92.murder.roles.RolesList;
import fr.first92.murder.roles.RolesManager;
import fr.first92.octaapi.utils.ItemStackUtils;
import fr.first92.octaapi.utils.Title;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class InitiateGame {

   public void initiate(List<Player> players) {

       List<Location> spawns = Murder.getInstance().getVariables().getSpawns();

       players.forEach(p -> p.teleport(spawns.get(players.indexOf(p))));

       List<Player> collect = new ArrayList<>();

       Arrays.stream(RolesList.values()).filter(r -> r.getSide().equals(RolesList.RoleSides.GOOD) && !r.getPlayersAlive().isEmpty()).forEach(r -> collect.addAll(r.getPlayersAlive()));

       Collections.shuffle(collect);

       Murder.getInstance().getVariables().setVictimsList(collect);

       players.forEach(this::spawn);
   }

    public void spawn(Player p) {

        RolesList role = new RolesManager().getPlayerRole(p);

        p.getInventory().clear();
        p.getActivePotionEffects().forEach(e -> p.removePotionEffect(e.getType()));

        new Title().sendFullTitle(p, 5, 20, 5, role.getTag(), role.getLore());

        p.sendMessage(role.getDescription());

        Arrays.stream(role.getItems()).forEach(i -> {

            int slot = IntStream.range(0, 8).filter(i2 -> i2 != p.getInventory().getHeldItemSlot() && !(p.getInventory().getItem(i2) != null && p.getInventory().getItem(i2).getType() != Material.AIR)).findFirst().getAsInt();

            p.getInventory().setItem(slot, i);
        });

        if(Murder.getInstance().getVariables().hasBeenTested(p)) p.getInventory().remove(Material.TRIPWIRE_HOOK);

        if((Math.floor(new RolesManager().getMaxPlayers() * .75) > new RolesManager().numberOfDeath()) && role.equals(RolesList.MURDERER) && RolesList.ALLY.getPlayers().isEmpty()) {

            RolesList.MURDERER.getPlayersAlive().forEach(p1 -> {

                int slot = IntStream.range(0, 8).filter(i2 -> i2 != p.getInventory().getHeldItemSlot() && !(p.getInventory().getItem(i2) != null && p.getInventory().getItem(i2).getType() != Material.AIR)).findFirst().getAsInt();

                p.getInventory().setItem(slot, new ItemStackUtils(Material.SKULL_ITEM, "§cHire a Ally", 1).build());
            });
        }
    }
}
