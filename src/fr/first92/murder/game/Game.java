package fr.first92.murder.game;

import fr.first92.murder.Murder;
import fr.first92.murder.roles.RolesManager;
import fr.first92.octaapi.game.GameManager;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game extends GameManager {

    public Game() {
        super(Murder.getInstance(), "murder", 2, 2);
    }

    @Override
    public void launchGame() {

        new RolesManager().randomRoles(new ArrayList<>(Bukkit.getOnlinePlayers()));
    }

    @Override
    public List<String> description() {
        return Arrays.asList("Kill", "Two");
    }
}
