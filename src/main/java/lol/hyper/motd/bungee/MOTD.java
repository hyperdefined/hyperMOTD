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
    List<String> motds = new ArrayList<>();
    String fixedMOTD;
    String type;

    public static MOTD getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        loadConfig();
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
        if (type.equalsIgnoreCase("random")) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, motds.size());
            response.setDescriptionComponent(new TextComponent(ChatColor.translateAlternateColorCodes('&', motds.get(randomNum))));
        } else if (type.equalsIgnoreCase("fixed")) {
            response.setDescriptionComponent(new TextComponent(ChatColor.translateAlternateColorCodes('&', fixedMOTD)));
        }
    }

    public void loadConfig() {
        try {
            File configFile = new File("plugins" + File.separator + "DMC-MOTD", "config.yml");
            Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            type = configuration.getString("type");
            motds = configuration.getStringList("random-motd");
            fixedMOTD = configuration.getString("fixed-motd");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
