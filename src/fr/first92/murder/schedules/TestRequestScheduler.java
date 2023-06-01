package fr.first92.murder.schedules;

import fr.first92.murder.Murder;
import fr.first92.murder.roles.RolesList;
import fr.first92.octaapi.utils.ItemStackUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.stream.IntStream;

public class TestRequestScheduler {

    private Player p;
    int timer;

    public TestRequestScheduler(Player p, int timer) {

        this.p = p;
        this.timer = timer;

        task.runTaskTimer(Murder.getInstance(), 20, 20);
    }

    BukkitRunnable task = new BukkitRunnable() {

        @Override
        public void run() {

            if(p.getInventory().contains(Material.TRIPWIRE_HOOK)) p.getInventory().remove(Material.TRIPWIRE_HOOK);

            if(timer == 0) {

                if(!Murder.getInstance().getVariables().hasBeenTested(p)) {

                    RolesList.DETECTIVE.getPlayersAlive().forEach(det -> det.sendMessage("§6" + p.getName() + "'s §ctesting request expired!"));

                    p.sendMessage("§cYou're testing request has expired");

                    int slot = IntStream.range(0, 8).filter(i2 -> i2 != p.getInventory().getHeldItemSlot() && !(p.getInventory().getItem(i2) != null && p.getInventory().getItem(i2).getType() != Material.AIR)).findFirst().getAsInt();

                    p.getInventory().setItem(slot, new ItemStackUtils(Material.TRIPWIRE_HOOK, "§aTester", 1).build());
                }

                task.cancel();
            }

            timer --;
        }
    };
}
