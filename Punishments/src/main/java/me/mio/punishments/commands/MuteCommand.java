package me.mio.punishments.commands;

import me.mio.punishments.Punishments;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("Usage: /mute <player> <reason>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found.");
            return true;
        }

        String reason = String.join(" ", args[1...args.length]);

        Punishments.getInstance().getMongoDBManager().addMute(target.getUniqueId(), reason);

        target.sendMessage("§c§lᴘᴜɴɪꜱʜᴍᴇɴᴛꜱ §8• §cYou have been muted. Reason: " + reason);
        sender.sendMessage("§c§lᴘᴜɴɪꜱʜᴍᴇɴᴛꜱ §8• §cPlayer " + target.getName() + " has been muted.");

        return true;
    }
}
