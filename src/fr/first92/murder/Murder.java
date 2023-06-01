package fr.first92.murder;

import fr.first92.murder.armorstand.ArmorStandsHandler;
import fr.first92.murder.game.Game;
import fr.first92.murder.handlers.Managers;
import fr.first92.murder.handlers.Variables;
import fr.first92.octaapi.utils.Chunks;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class Murder extends JavaPlugin {

    private static Murder instance;

    private Variables variables = new Variables();

    @Override
    public void onEnable() {

        instance = this;

        new Chunks().around(new Location(
                Bukkit.getWorld("world"), 0, 65, 0).getChunk(), 15).forEach(Chunk::load);

        new ArmorStandsHandler().load();
        new Managers().registerEvents();
        new Managers().registerCommands();

        new Game();
    }

    public static Murder getInstance() {
        return instance;
    }

    public Variables getVariables() {
        return variables;
    }
}
