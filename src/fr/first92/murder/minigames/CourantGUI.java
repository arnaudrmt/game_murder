package fr.first92.murder.minigames;

import fr.first92.murder.Murder;
import fr.first92.octaapi.utils.AbstractGui;
import fr.first92.octaapi.utils.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.stream.IntStream;

public class CourantGUI extends AbstractGui {

    public CourantGUI(Player p) {

        super("§7Link the dots", 9*4, p);
    }

    @Override
    public void init() {

        ItemStackUtils greenpane = new ItemStackUtils(Material.STAINED_GLASS_PANE, "§aCOMPLETED", 1);
        greenpane.setMeta(5);

        ItemStackUtils greypane = new ItemStackUtils(Material.STAINED_GLASS_PANE, "§cTO COMPLETE", 1);
        greypane.setMeta(8);

        ItemStackUtils redpane = new ItemStackUtils(Material.STAINED_GLASS_PANE, "§cFINISH", 1);
        redpane.setMeta(14);

        addItem(19, greenpane.build(), new ActionItem() {
            @Override
            protected void action(InventoryClickEvent e) {

            }
        });
        addItem(25, redpane.build(), new ActionItem() {

            @Override
            protected void action(InventoryClickEvent e) {


                if ((e.getInventory().getItem(e.getSlot() - 1) != null && e.getInventory().getItem(e.getSlot() - 1).getItemMeta().getDisplayName().contains("COMPLETED")) ||
                        (e.getInventory().getItem(e.getSlot() - 10) != null && e.getInventory().getItem(e.getSlot() - 10).getItemMeta().getDisplayName().contains("COMPLETED")) ||
                        (e.getInventory().getItem(e.getSlot() + 8) != null && e.getInventory().getItem(e.getSlot() + 8).getItemMeta().getDisplayName().contains("COMPLETED"))) {

                    p.closeInventory();
                    Murder.getInstance().getVariables().completeQuest();
                    if(Murder.getInstance().getVariables().getQuestsCompleted() == 3) {

                        Bukkit.broadcastMessage("§aAll the challenges have been completed!");
                        p.getInventory().addItem(new ItemStackUtils(Material.MAP, "§cMurder List", 1).build());

                    } else {

                        Bukkit.broadcastMessage("§aSomeone completed the linking puzzle! §7(" + Murder.getInstance().getVariables().getQuestsCompleted() + "/3)");
                    }

                } else p.closeInventory();
            }
        });

        IntStream.range(0, 5).forEach(i -> {

            int i1 = (int) ((Math.random() * (2)));
            int currentSlot = 19 + i;

            int slot = i1 == 1 ? (currentSlot - 8) : (currentSlot + 1);

            addItem(slot < 0 ? currentSlot + 1 : slot, greypane.build(), new ActionItem() {

                @Override
                protected void action(InventoryClickEvent e) {

                    if((e.getInventory().getItem(e.getSlot() - 1) != null && e.getInventory().getItem(e.getSlot() - 1).getItemMeta().getDisplayName().contains("COMPLETED")) ||
                            (e.getInventory().getItem(e.getSlot() - 10) != null && e.getInventory().getItem(e.getSlot() - 10).getItemMeta().getDisplayName().contains("COMPLETED")) ||
                            (e.getInventory().getItem(e.getSlot() + 8) != null && e.getInventory().getItem(e.getSlot() + 8).getItemMeta().getDisplayName().contains("COMPLETED")))
                        e.getInventory().setItem(e.getSlot(), greenpane.build());
                    else p.closeInventory();
                }
            });
        });
    }
}
