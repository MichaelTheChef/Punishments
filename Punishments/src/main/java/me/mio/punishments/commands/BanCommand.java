package me.mio.punishments.commands;

import org.bukkit.BanList;
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Usage: /ban <player> <reason>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found.");
            return true;
        }

        String reason = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

        BanList banList = Bukkit.getBanList(BanList.Type.NAME);
        banList.addBan(target.getName(), reason, null, sender.getName());

        target.kickPlayer("§c§lᴘᴜɴɪꜱʜᴍᴇɴᴛꜱ §8• §cYou have been banned. Reason: " + reason);
        sender.sendMessage("§c§lᴘᴜɴɪꜱʜᴍᴇɴᴛꜱ §8• §cPlayer " + target.getName() + " has been banned.");

        return true;
    }
}
