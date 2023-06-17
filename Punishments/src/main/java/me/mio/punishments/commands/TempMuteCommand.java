package me.mio.punishments.commands;

import me.mio.punishments.Punishments;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.concurrent.TimeUnit;

public class TempMuteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("Usage: /tempmute <player> <duration> <reason>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found.");
            return true;
        }

        long duration = parseDuration(args[1]);
        if (duration <= 0) {
            sender.sendMessage("Invalid duration.");
            return true;
        }

        String reason = String.join(" ", args[2...args.length]);

        PunishmentPlugin.getInstance().getMongoDBManager().addTempMute(target.getUniqueId(), reason, duration);

        target.sendMessage("§c§lᴘᴜɴɪꜱʜᴍᴇɴᴛꜱ §8• §cYou have been muted temporarily. Reason: " + reason + " Duration: " + formatDuration(duration));
        sender.sendMessage("§c§lᴘᴜɴɪꜱʜᴍᴇɴᴛꜱ §8• §cPlayer " + target.getName() + " has been temporarily muted for " + formatDuration(duration));

        Bukkit.getScheduler().runTask(Punishments.getInstance(), () -> {
            Punishments.getInstance().getMongoDBManager().removeTempMute(target.getUniqueId());
        }, duration * 20L);

        return true;
    }

    private long parseDuration(String durationString) {
        try {
            if (durationString.matches("^\\d+$")) {
                long seconds = Long.parseLong(durationString);
                return TimeUnit.SECONDS.toSeconds(seconds);
            }

            if (durationString.matches("^\\d+[smhd]$")) {
                long value = Long.parseLong(durationString.substring(0, durationString.length() - 1));
                char unit = durationString.charAt(durationString.length() - 1);

                switch (unit) {
                    case 's':
                        return TimeUnit.SECONDS.toSeconds(value);
                    case 'm':
                        return TimeUnit.MINUTES.toSeconds(value);
                    case 'h':
                        return TimeUnit.HOURS.toSeconds(value);
                    case 'd':
                        return TimeUnit.DAYS.toSeconds(value);
                }
            }
        } catch (NumberFormatException ignored) {
        }

        return -1;
    }

    private String formatDuration(long duration) {
        StringBuilder formattedDuration = new StringBuilder();

        long days = TimeUnit.SECONDS.toDays(duration);
        if (days > 0) {
            formattedDuration.append(days).append("d ");
            duration -= TimeUnit.DAYS.toSeconds(days);
        }

        long hours = TimeUnit.SECONDS.toHours(duration);
        if (hours > 0) {
            formattedDuration.append(hours).append("h ");
            duration -= TimeUnit.HOURS.toSeconds(hours);
        }

        long minutes = TimeUnit.SECONDS.toMinutes(duration);
        if (minutes > 0) {
            formattedDuration.append(minutes).append("m ");
            duration -= TimeUnit.MINUTES.toSeconds(minutes);
        }

        long seconds = duration;
        if (seconds > 0) {
            formattedDuration.append(seconds).append("s");
        }

        return formattedDuration.toString();
    }
}
