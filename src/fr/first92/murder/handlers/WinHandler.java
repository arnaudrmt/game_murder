package fr.first92.murder.handlers;

import fr.first92.murder.game.InitiateGame;
import fr.first92.murder.roles.RolesList;
import fr.first92.murder.roles.RolesManager;
import fr.first92.murder.schedules.EndGameScheduler;
import fr.first92.octaapi.game.gamestate.GameState;
import fr.first92.octaapi.game.gamestate.GameStateManager;
import fr.first92.octaapi.utils.ItemStackUtils;
import fr.first92.octaapi.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WinHandler {

    public void checkWin() {

        if(Arrays.stream(RolesList.values()).filter(r -> r.getPlayersAlive().size() > 0).collect(Collectors.toList()).stream().allMatch(r1 -> r1.getSide().equals(RolesList.RoleSides.GOOD))) {

            win(RolesList.RoleSides.GOOD);

        } else if(Arrays.stream(RolesList.values()).filter(r -> r.getPlayersAlive().size() > 0).collect(Collectors.toList()).stream().allMatch(r1 -> r1.getSide().equals(RolesList.RoleSides.BAD))) {

            win(RolesList.RoleSides.BAD);
        }
    }

    public void win(RolesList.RoleSides side) {

        new GameStateManager().setState(GameState.END);

        Bukkit.getOnlinePlayers().stream().filter(p -> new RolesManager().hasPlayerRole(p)).forEach(p -> {

            if(new RolesManager().getPlayerRole(p).getSide().equals(side)) {

                new Title().sendFullTitle(p, 5, 120, 5, "§6You won!", "§7I hope you'll won again next time too...");
            } else {

                new Title().sendFullTitle(p, 5, 120, 5, "§cYou lost!", "§7Better luck next time...");
            }
        });

        new EndGameScheduler(10);
    }

    public void eliminate(Player p) {

        p.getInventory().clear();
        p.setGameMode(GameMode.SPECTATOR);

        RolesList pRole = new RolesManager().getPlayerRole(p);

        pRole.getPlayersAlive().remove(p);

        List<Player> l = new ArrayList<>();

        Arrays.stream(RolesList.values()).forEach(r -> l.addAll(r.getPlayersAlive()));

        if((Math.floor(new RolesManager().getMaxPlayers() * .75) > new RolesManager().getMaxPlayers() - l.size()) && RolesList.ALLY.getPlayers().isEmpty()) {

            RolesList.MURDERER.getPlayersAlive().forEach(p1 -> {

                int slot = IntStream.range(0, 8).filter(i2 -> i2 != p.getInventory().getHeldItemSlot() && !(p.getInventory().getItem(i2) != null && p.getInventory().getItem(i2).getType() != Material.AIR)).findFirst().getAsInt();

                p.getInventory().setItem(slot, new ItemStackUtils(Material.SKULL_ITEM, "§cHire a Ally", 1).build());
            });
        }

        if(pRole.equals(RolesList.DETECTIVE) && RolesList.INNOCENT.getPlayersAlive().size() > 0) {

            Player newDet = RolesList.INNOCENT.getPlayersAlive().stream().findAny().get();

            RolesList.INNOCENT.getPlayersAlive().remove(newDet);

            RolesList.DETECTIVE.addAlivePlayer(newDet);

            newDet.sendMessage("§a You're the new detective");

            new InitiateGame().spawn(newDet);
        }

        Bukkit.broadcastMessage("§cA §6" + pRole.getTag() + " §chas been eliminated!");

        checkWin();
    }
}
