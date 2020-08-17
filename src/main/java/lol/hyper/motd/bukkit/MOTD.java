package lol.hyper.motd.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public class MOTD extends JavaPlugin implements Listener {

    private static MOTD instance;
    public FileConfiguration config = this.getConfig();
    public File configFile = new File(getDataFolder(), "config.yml");

    public static MOTD getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getLogger().info(this.getDataFolder().toString());
        if (!configFile.exists()) {
            this.saveResource("config.yml", true);
        }
        this.getCommand("motdreload").setExecutor(new CommandReload());
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }
    @EventHandler
    public void onPing (ServerListPingEvent event) {
        if (config.getString("type").equalsIgnoreCase("fixed")) {
            event.setMotd(ChatColor.translateAlternateColorCodes('&', config.getString("fixed-motd")));
        }
        if (config.getString("type").equalsIgnoreCase("random")) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, config.getStringList("random-motd").size());
            event.setMotd(ChatColor.translateAlternateColorCodes('&', config.getStringList("random-motd").get(randomNum)));
        }
    }
}
