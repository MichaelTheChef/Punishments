package me.mio.punishments.commands;

import me.mio.punishments.Punishments;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.UUID;

public class BlacklistCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lᴘᴜɴɪꜱʜᴍᴇɴᴛꜱ §8• §cThis command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("punishments.blacklist")) {
            player.sendMessage("§c§lᴘᴜɴɪꜱʜᴍᴇɴᴛꜱ §8• §cYou don't have permission to use this command.");
            return true;
        }

        if (args.length < 2) {
            player.sendMessage("§c§lᴘᴜɴɪꜱʜᴍᴇɴᴛꜱ §8• §cUsage: /blacklist <add|remove> <player>");
            return true;
        }

        String action = args[0];
        String targetName = args[1];
        Player target = Bukkit.getPlayer(targetName);

        if (target == null) {
            player.sendMessage("§c§lᴘᴜɴɪꜱʜᴍᴇɴᴛꜱ §8• §cPlayer not found.");
            return true;
        }

        UUID targetUUID = target.getUniqueId();

        switch (action.toLowerCase()) {
            case "add":
                if (Punishments.getInstance().getMongoDBManager().isBlacklisted(targetUUID)) {
                    player.sendMessage("§c§lᴘᴜɴɪꜱʜᴍᴇɴᴛꜱ §8• §cPlayer is already blacklisted.");
                } else {
                    Punishments.getInstance().getMongoDBManager().addToBlacklist(targetUUID);
                    player.sendMessage("§c§lᴘᴜɴɪꜱʜᴍᴇɴᴛꜱ §8• §cPlayer has been blacklisted.");
                }
                break;

            case "remove":
                if (!Punishments.getInstance().getMongoDBManager().isBlacklisted(targetUUID)) {
                    player.sendMessage("§c§lᴘᴜɴɪꜱʜᴍᴇɴᴛꜱ §8• §cPlayer is not blacklisted.");
                } else {
                    Punishments.getInstance().getMongoDBManager().removeFromBlacklist(targetUUID);
                    player.sendMessage("§c§lᴘᴜɴɪꜱʜᴍᴇɴᴛꜱ §8• §cPlayer has been removed from the blacklist.");
                }
                break;

            default:
                player.sendMessage("§c§lᴘᴜɴɪꜱʜᴍᴇɴᴛꜱ §8• §cInvalid action. Use 'add' or 'remove'.");
                break;
        }

        return true;
    }
}
