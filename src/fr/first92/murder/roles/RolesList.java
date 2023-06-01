package fr.first92.murder.roles;

import fr.first92.octaapi.utils.ItemStackUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public enum RolesList {


    INNOCENT("Innocent", "Try to survive!", "§7", "", new ItemStack[]{new ItemStackUtils(Material.TRIPWIRE_HOOK, "§aTester", 1).build()}, RoleSides.GOOD),

    DETECTIVE("Detective", "Try to kill the murderer(s)!", "§6", "", new ItemStack[]{new ItemStackUtils(Material.BOW, "", 1).build(), new ItemStackUtils(Material.ARROW, "§cThe arrows of truth", 1).build()}, RoleSides.GOOD),

    MURDERER("Murderer", "Try to kill every living!", "§c", "bouh\nbouh", new ItemStack[]{new ItemStackUtils(Material.TRIPWIRE_HOOK, "§aTester", 1).build(), new ItemStackUtils(Material.MAP, "§CMurder List", 1).build(),
        new ItemStackUtils(Material.BOW, "", 1).build(), new ItemStackUtils(Material.ARROW, "§cThe arrows of truth", 3).build(), new ItemStackUtils(Material.IRON_SWORD, "§cSlytherin", 1).build()}, RoleSides.BAD),

    ALLY("Ally", "Try to kill every living!", "§4", "", new ItemStack[]{new ItemStackUtils(Material.TRIPWIRE_HOOK, "§aTester", 1).build(), new ItemStackUtils(Material.MAP, "§CMurder List", 1).build(),
            new ItemStackUtils(Material.IRON_SWORD, "§cSlytherin", 1).build()}, RoleSides.BAD);

    private String name;
    private String lore;
    private String color;
    private String description;

    private ItemStack[] items;

    private RoleSides side;

    private List<Player> alive = new ArrayList<>();
    private List<Player> players = new ArrayList<>();

    RolesList(String name, String lore, String color, String description, ItemStack[] items, RoleSides side) {
        this.name = name;
        this.lore = lore;
        this.color = color;
        this.description = description;
        this.items = items;
        this.side = side;
    }

    public String getName() {
        return name;
    }

    public String getLore() {
        return lore;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public RoleSides getSide() {
        return side;
    }

    public List<Player> getPlayersAlive() {
        return alive;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void addAlivePlayer(Player p) {

        alive.add(p);
        players.add(p);
    }

    public void removeAlivePlayer(Player p) {

        alive.remove(p);
    }

    public String getTag() {
        return color + name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public enum RoleSides {

        GOOD(),
        BAD();
    }
}
