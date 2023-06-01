package fr.first92.murder.events;

import fr.first92.murder.Murder;
import fr.first92.murder.game.InitiateGame;
import fr.first92.murder.handlers.Variables;
import fr.first92.murder.roles.RolesList;
import fr.first92.murder.roles.RolesManager;
import fr.first92.murder.schedules.TestRequestScheduler;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractEvent implements Listener {

    Variables var = Murder.getInstance().getVariables();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        if(e.getItem() == null) return;
        if(!e.getAction().toString().startsWith("R")) return;
        if(!(e.getItem().hasItemMeta())) return;

        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        Material mat = item.getType();

        e.setCancelled(true);

        if(mat.equals(Material.TRIPWIRE_HOOK) && var.numberOfTested() < (Math.ceil(new RolesManager().getMaxPlayers() * .25)) + var.getBonusTests()) {

            p.getInventory().remove(Material.TRIPWIRE_HOOK);

            new TestRequestScheduler(p, 30);

            RolesList.DETECTIVE.getPlayersAlive().forEach(det -> {

                det.sendMessage("§6" + p.getName() + "§a want to be tested!");

                IChatBaseComponent txt = IChatBaseComponent.ChatSerializer.a("{\"text\":\"Click here to accept the " +
                        "request\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" +
                        "/test " + p.getName() + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" +
                        "Click here to accept\"}}");

                PacketPlayOutChat packet = new PacketPlayOutChat(txt);
                ((CraftPlayer) det).getHandle().playerConnection.sendPacket(packet);
            });
        }

        if(mat.equals(Material.ARROW)) {

            Location closest = new Location(Bukkit.getWorld("world"), 0, 0, 0);

            for(Location loc : var.getRoomsLocations()) {

                 if(loc.distanceSquared(p.getLocation()) < closest.distanceSquared(p.getLocation()))
                    closest = loc;
            }

            int position = var.getRoomsLocations().indexOf(closest);

            if(item.getItemMeta().getDisplayName().contains("Previous")) p.teleport(position == 0 ? var.getRoomsLocations().get(var.getRoomsLocations().size() - 1) : var.getRoomsLocations().get(position - 1));
            else p.teleport(position == var.getRoomsLocations().size() - 1 ? var.getRoomsLocations().get(0) : var.getRoomsLocations().get(position + 1));
        }

        if(mat.equals(Material.ENDER_PEARL)) {

            p.getInventory().clear();

            PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn(((CraftPlayer) p).getHandle());

            Bukkit.getOnlinePlayers().stream().filter(player -> player != p)
                    .forEach(rs -> ((CraftPlayer) rs).getHandle().playerConnection.sendPacket(packet));

            new InitiateGame().spawn(p);
        }
    }
}
