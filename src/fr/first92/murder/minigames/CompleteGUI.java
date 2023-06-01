package fr.first92.murder.minigames;

import fr.first92.murder.Murder;
import fr.first92.octaapi.utils.AbstractGui;
import fr.first92.octaapi.utils.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.stream.IntStream;

public class CompleteGUI  extends AbstractGui {

    public CompleteGUI(Player p) {

        super("§7Color in green", 9*6, p);
    }

    @Override
    public void init() {

        ItemStackUtils greenpane = new ItemStackUtils(Material.STAINED_GLASS_PANE, "§aCOMPLETED", 1);
        greenpane.setMeta(5);

        ItemStackUtils greypane = new ItemStackUtils(Material.STAINED_GLASS_PANE, "§7------", 1);
        greypane.setMeta(8);

        ItemStackUtils redpane = new ItemStackUtils(Material.STAINED_GLASS_PANE, "§cTo Complete", 1);
        redpane.setMeta(14);

        IntStream.range(10, 44).forEach(i -> {

            addItem(i, redpane.build(), new ActionItem() {

                @Override
                protected void action(InventoryClickEvent e) {

                    if(e.getInventory().getItem(e.getSlot() - 1).getItemMeta().getDisplayName().contains("To Complete")) p.closeInventory();

                    if(e.getInventory().getItem(e.getSlot() - 1).getItemMeta().getDisplayName().contains("COMPLETED") || (e.getInventory().getItem(e.getSlot() - 1).getItemMeta().getDisplayName().contains("---") && e.getInventory().getItem(e.getSlot() - 3).getItemMeta().getDisplayName().contains("---")
                        || e.getInventory().getItem(e.getSlot() - 3).getItemMeta().getDisplayName().contains("COMPLETED"))) {

                        e.getInventory().setItem(e.getSlot(), greenpane.build());

                        if(e.getSlot() == 44) {

                            p.closeInventory();
                            Murder.getInstance().getVariables().completeQuest();
                            if(Murder.getInstance().getVariables().getQuestsCompleted() == 3) {

                                Bukkit.broadcastMessage("§aAll the challenges have been completed!");
                                p.getInventory().addItem(new ItemStackUtils(Material.MAP, "§cMurder List", 1).build());

                            } else {

                                Bukkit.broadcastMessage("§aSomeone completed the linking puzzle! §7(" + Murder.getInstance().getVariables().getQuestsCompleted() + "/3)");
                            }
                        }

                    } else p.closeInventory();
                }
            });
        });

        IntStream.range(0, 10).forEach(i -> addItem(i, greypane.build(), new ActionItem()  {@Override protected void action(InventoryClickEvent e) {}}));
        IntStream.range(17, 19).forEach(i -> addItem(i, greypane.build(), new ActionItem()  {@Override protected void action(InventoryClickEvent e) {}}));
        IntStream.range(26, 28).forEach(i -> addItem(i, greypane.build(), new ActionItem()  {@Override protected void action(InventoryClickEvent e) {}}));
        IntStream.range(35, 37).forEach(i -> addItem(i, greypane.build(), new ActionItem()  {@Override protected void action(InventoryClickEvent e) {}}));
        IntStream.range(44, 54).forEach(i -> addItem(i, greypane.build(), new ActionItem()  {@Override protected void action(InventoryClickEvent e) {}}));
    }
}
