package fr.first92.murder.commands;

import fr.first92.murder.Murder;
import fr.first92.murder.handlers.Variables;
import fr.first92.murder.roles.RolesManager;
import fr.first92.murder.schedules.TestScheduler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {

    Variables var = Murder.getInstance().getVariables();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(args.length != 1) return false;
        if(!(sender instanceof Player)) return false;
        if(Bukkit.getPlayer(args[0]) == null) return false;
        if(var.numberOfTested() > (Math.ceil(new RolesManager().getMaxPlayers() * .25) + var.getBonusTests())) return false;
        if(Bukkit.getPlayer(args[0]).getInventory().contains(Material.TRIPWIRE_HOOK)) return false;

        Player p = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        new TestScheduler(target, 5);

        return false;
    }
}
