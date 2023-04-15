package net.daechler.surrender;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Surrender extends JavaPlugin implements CommandExecutor {
    @Override
    public void onEnable() {
        // Register the "/surrender" command with this plugin
        getCommand("surrender").setExecutor(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Check if the command is "/surrender"
        if (cmd.getName().equalsIgnoreCase("surrender")) {
            // Check if the player has the "surrender.use" permission or is an admin with "surrender.other" permission
            if (sender.hasPermission("surrender.use") || (sender.hasPermission("surrender.other") && args.length == 1)) {
                if (args.length == 0) {
                    // Player uses "/surrender" command to kill themselves
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        player.setHealth(0);
                        player.sendMessage(ChatColor.RED + "You have surrendered.");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Only players can use this command to surrender.");
                    }
                } else if (args.length == 1) {
                    // Admin uses "/surrender <name>" command to kill another player
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        target.setHealth(0);
                        sender.sendMessage(ChatColor.RED + target.getName() + " has been surrendered.");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Could not find player " + args[0] + ".");
                    }
                } else {
                    // Invalid command usage
                    sender.sendMessage(ChatColor.RED + "Usage: /surrender <name>");
                }
            } else {
                // Player does not have the required permission to use the command
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            }
            return true;
        }
        return false;
    }
}
