package me.mio.punishments;

import me.mio.punishments.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Punishments extends JavaPlugin {
    private static Punishments instance;
    private MongoDBManager mongoDBManager;

    @Override
    public void onEnable() {
        instance = this;
        mongoDBManager = new MongoDBManager();

        getCommand("ban").setExecutor(new BanCommand());
        getCommand("tempban").setExecutor(new TempBanCommand());
        getCommand("mute").setExecutor(new MuteCommand());
        getCommand("tempmute").setExecutor(new TempMuteCommand());
        getCommand("kick").setExecutor(new KickCommand());
        getCommand("blacklist").setExecutor(new BlacklistCommand());
        getCommand("ipaddress").setExecutor(new IPAddressCommand());
        getCommand("punishmentlogs").setExecutor(new PunishmentLogsCommand());
    }

    @Override
    public void onDisable() {}

    public static Punishments getInstance() {
        return instance;
    }

    public MongoDBManager getMongoDBManager() {
        return mongoDBManager;
    }
}
