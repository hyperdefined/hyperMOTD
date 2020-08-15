package lol.hyper.motd.bungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class MOTD extends Plugin implements Listener {

    private static MOTD instance;
    public File configFile = new File("plugins" + File.separator + "DMC-MOTD", "config.yml");
    public Configuration configuration;

    public static MOTD getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
            getProxy().getLogger().severe("Unable to log configuration file!");
        }
        ProxyServer.getInstance().getPluginManager().registerListener(this, this);
        getProxy().getPluginManager().registerCommand(this, new CommandReload("motdreload"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPing(ProxyPingEvent e) {
        ServerPing response = e.getResponse();
        if (configuration.getString("type").equalsIgnoreCase("random")) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, configuration.getList("random-motd").size());
            response.setDescriptionComponent(new TextComponent(ChatColor.translateAlternateColorCodes('&', configuration.getList("random-motd").get(randomNum).toString())));
        } else if (configuration.getString("type").equalsIgnoreCase("fixed")) {
            response.setDescriptionComponent(new TextComponent(ChatColor.translateAlternateColorCodes('&', configuration.getString("fixed-motd"))));
        }
    }
}
