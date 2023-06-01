package fr.first92.murder.handlers;

import fr.first92.murder.Murder;
import fr.first92.murder.commands.TestCommand;
import fr.first92.murder.events.*;
import org.bukkit.plugin.PluginManager;

public class Managers {

    private Murder murder = Murder.getInstance();

    public void registerEvents() {

        PluginManager pm = murder.getServer().getPluginManager();

        pm.registerEvents(new JoinEvent(), murder);
        pm.registerEvents(new DamageEvent(), murder);
        pm.registerEvents(new EntitySpawn(), murder);
        pm.registerEvents(new MapEvent(), murder);
        pm.registerEvents(new InteractEvent(), murder);
        pm.registerEvents(new MoveEvent(), murder);
        pm.registerEvents(new PickUpEvent(), murder);
        pm.registerEvents(new DropEvent(), murder);
        pm.registerEvents(new BreakEvent(), murder);
        pm.registerEvents(new AsyncChatEvent(), murder);
    }

    public void registerCommands() {

        murder.getCommand("test").setExecutor(new TestCommand());
    }
}
