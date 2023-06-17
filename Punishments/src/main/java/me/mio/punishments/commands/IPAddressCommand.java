package me.mio.punishments.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.net.InetSocketAddress;

public class IPAddressCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("punishments.ipaddress")) {
            player.sendMessage("You don't have permission to use this command.");
            return true;
        }

        if (args.length < 1) {
            player.sendMessage("Usage: /ipaddress <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage("Player not found.");
            return true;
        }

        InetSocketAddress address = target.getAddress();
        if (address == null) {
            player.sendMessage("Unable to retrieve IP address for the player.");
            return true;
        }

        String ipAddress = address.getAddress().getHostAddress();
        player.sendMessage("§c§lᴘᴜɴɪꜱʜᴍᴇɴᴛꜱ §8• §cIP address of " + target.getName() + ": " + ipAddress);

        return true;
    }
}
