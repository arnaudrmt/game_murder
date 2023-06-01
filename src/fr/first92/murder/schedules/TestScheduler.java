package fr.first92.murder.schedules;

import fr.first92.murder.Murder;
import fr.first92.murder.roles.RolesList;
import fr.first92.murder.roles.RolesManager;
import fr.first92.octaapi.utils.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class TestScheduler {

    private Player p;
    int timer;

    public TestScheduler(Player p, int timer) {

        this.p = p;
        this.timer = timer;

        p.getInventory().clear();
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, timer, 1, false, false));

        Murder.getInstance().getVariables().setJudgment(p, !new RolesManager().getPlayerRole(p).getSide().equals(RolesList.RoleSides.GOOD));

        task.runTaskTimer(Murder.getInstance(), 20, 20);
    }

    BukkitRunnable task = new BukkitRunnable() {

        @Override
        public void run() {

            if(timer == 0) {

                Bukkit.broadcastMessage("§6" + p.getName() + " §ahas been identified as a " +
                        new RolesManager().getPlayerRole(p).getTag());

                p.getInventory().setItem(3, new ItemStackUtils(Material.ARROW, "§7Previous", 1).build());
                p.getInventory().setItem(4, new ItemStackUtils(Material.ENDER_PEARL, "§7Teleport", 1).build());
                p.getInventory().setItem(5, new ItemStackUtils(Material.ARROW, "§7Next", 1).build());

                p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10, 1, false, false));

                new EscapeCageScheduler(p, 15);

                task.cancel();
            }

            timer --;
        }
    };
}
