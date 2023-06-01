package fr.first92.murder.schedules;

import fr.first92.murder.Murder;
import fr.first92.octaapi.network.data.redis.messages.RedisMessageSender;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class EndGameScheduler {

    int timer;
    int maxTimer;

    public EndGameScheduler(int timer) {

        this.maxTimer = timer;
        this.timer = timer;

        task.runTaskTimer(Murder.getInstance(), 20, 20);
    }

    public BukkitRunnable task = new BukkitRunnable() {

        @Override
        public void run() {

            Bukkit.getOnlinePlayers().forEach(all -> all.setLevel(timer));

            if(timer == 5) {

                Bukkit.getOnlinePlayers().forEach(all -> new RedisMessageSender().send(Arrays.asList("api", "server", "connect",
                        "Default", all.getName())));
            }

            if(timer == 0) {

                Murder.getInstance().getServer().shutdown();
                task.cancel();
            }

            timer --;
        }
    };
}
