package fr.first92.murder.schedules;

import fr.first92.murder.Murder;
import fr.first92.murder.game.InitiateGame;
import fr.first92.octaapi.utils.Title;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EscapeCageScheduler {

    private Player p;
    int timer;

    public EscapeCageScheduler(Player p, int timer) {

        this.p = p;
        this.timer = timer;

        p.teleport(Murder.getInstance().getVariables().getRoomsLocations().get((int) ((Math.random() * (Murder.getInstance().getVariables().getRoomsLocations().size() - 1)))));

        task.runTaskTimer(Murder.getInstance(), 20, 20);
    }

    BukkitRunnable task = new BukkitRunnable() {

        @Override
        public void run() {

            new Title().sendActionBar(p, "§c" + timer + " seconds");

            if(!p.getInventory().contains(Material.ENDER_PEARL)) task.cancel();

            if(timer == 0) {

                if(p.getInventory().contains(Material.ENDER_PEARL)) {

                    new InitiateGame().spawn(p);

                    task.cancel();

                } else task.cancel();
            }

            timer --;
        }
    };
}
