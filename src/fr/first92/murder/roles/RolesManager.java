package fr.first92.murder.roles;

import fr.first92.murder.game.InitiateGame;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class RolesManager {

    public void randomRoles(List<Player> players) {

        Collections.shuffle(players);

        IntStream.range(0, (int) Math.ceil(players.size() * .10)).forEach(i -> RolesList.MURDERER.addAlivePlayer(players.get(i)));

        RolesList.DETECTIVE.addAlivePlayer(players.stream().filter(p -> !hasPlayerRole(p)).findAny().get());

        players.stream().filter(p -> !hasPlayerRole(p)).forEach(RolesList.INNOCENT::addAlivePlayer);

        new InitiateGame().initiate(players);
    }

    public RolesList getRoleByName(String name) {

        return RolesList.valueOf(name);
    }

    public RolesList getPlayerRole(Player p) {

        if(hasPlayerRole(p))
            return Arrays.stream(RolesList.values()).filter(r -> r.getPlayers().contains(p)).findFirst().get();

        return null;
    }

    public boolean hasPlayerRole(Player p) {

        return Arrays.stream(RolesList.values()).anyMatch(r -> r.getPlayers().contains(p));
    }

    public int getMaxPlayers() {

        List<Player> players = new ArrayList<>();

        Arrays.stream(RolesList.values()).forEach(r -> players.addAll(r.getPlayers()));

        return (players.size() - 1);
    }

    public boolean hisPlayersAlive(Player p) {

        return Arrays.stream(RolesList.values()).anyMatch(r -> r.getPlayersAlive().contains(p));
    }

    public int numberOfDeath() {

        List<Player> aPlayers = new ArrayList<>();
        List<Player> allPlayers = new ArrayList<>();

        Arrays.stream(RolesList.values()).forEach(r -> aPlayers.addAll(r.getPlayersAlive()));
        Arrays.stream(RolesList.values()).forEach(r -> allPlayers.addAll(r.getPlayers()));

        return allPlayers.size() - aPlayers.size();
    }
}
