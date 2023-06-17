package me.mio.punishments.commands;

import me.mio.punishments.Punishments;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.List;

public class PunishmentLogsCommand implements CommandExecutor {
    private final int PAGE_SIZE = 10;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("punishments.punishmentlogs")) {
            player.sendMessage("You don't have permission to use this command.");
            return true;
        }

        int page = 1;
        if (args.length > 0) {
            try {
                page = Integer.parseInt(args[0]);
            } catch (NumberFormatException ignored) {
            }
        }

        List<String> punishmentLogs = Punishments.getInstance().getMongoDBManager().getPunishmentLogs();
        if (punishmentLogs.isEmpty()) {
            player.sendMessage("No punishment logs found.");
            return true;
        }

        int totalPages = (int) Math.ceil((double) punishmentLogs.size() / PAGE_SIZE);
        if (page < 1 || page > totalPages) {
            player.sendMessage("Invalid page number.");
            return true;
        }

        player.sendMessage("§c§lᴘᴜɴɪꜱʜᴍᴇɴᴛꜱ §8• §cPunishment Logs - Page " + page + "/" + totalPages);

        int startIndex = (page - 1) * PAGE_SIZE;
        int endIndex = Math.min(startIndex + PAGE_SIZE, punishmentLogs.size());

        for (int i = startIndex; i < endIndex; i++) {
            player.sendMessage(punishmentLogs.get(i));
        }

        return true;
    }
}
