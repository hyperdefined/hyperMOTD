package lol.hyper.motd.bungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.Favicon;
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
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public final class MOTD extends Plugin implements Listener {

    public final File configFile = new File("plugins" + File.separator + "DMC-MOTD", "config.yml");
    public File iconFile;
    public Configuration configuration;
    public BufferedImage bufferedImage;
    public Logger logger = this.getLogger();

    @Override
    public void onEnable() {
        if (!configFile.exists()) {
            InputStream is = getResourceAsStream("config.yml");
            try {
                File path = new File("plugins" + File.separator + "DMC-MOTD");
                if (path.mkdir()) {
                    Files.copy(is, configFile.toPath());
                    logger.info("Copying default config...");
                } else {
                    logger.warning("Unable to create config folder!");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        loadConfig(configFile);
        ProxyServer.getInstance().getPluginManager().registerListener(this, this);
        getProxy().getPluginManager().registerCommand(this, new CommandReload("motdreload", this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPing(ProxyPingEvent e) {
        ServerPing response = e.getResponse();
        if (configuration.getString("type").equalsIgnoreCase("random")) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, configuration.getStringList("random-motd").size());
            response.setDescriptionComponent(new TextComponent(ChatColor.translateAlternateColorCodes('&', configuration.getStringList("random-motd").get(randomNum))));
        } else if (configuration.getString("type").equalsIgnoreCase("fixed")) {
            response.setDescriptionComponent(new TextComponent(ChatColor.translateAlternateColorCodes('&', configuration.getString("fixed-motd"))));
        }
        if (configuration.getBoolean("use-custom-icon") && bufferedImage != null) {
            response.setFavicon(Favicon.create(bufferedImage));
        }
    }

    public void loadConfig(File file) {
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            if (configuration.getBoolean("use-custom-icon")) {
                iconFile = new File("plugins" + File.separator + "DMC-MOTD", configuration.getString("custom-icon-filename"));
                if (!iconFile.exists()) {
                    logger.warning("Unable to locate custom icon from configuration! Make sure you have the path correct!");
                    logger.warning("The path is current set to: " + iconFile.getAbsolutePath());
                    logger.warning("Make sure this path exists!");
                    bufferedImage = null;
                } else if (!(FilenameUtils.getExtension(iconFile.getName()).equalsIgnoreCase("jpg") || FilenameUtils.getExtension(iconFile.getName()).equalsIgnoreCase("png"))) {
                    logger.warning("Unsupported file extension for server icon! You must use either JPG or PNG only.");
                    bufferedImage = null;
                } else {
                    bufferedImage = ImageIO.read(iconFile);
                    if ((bufferedImage.getWidth() != 64) && bufferedImage.getHeight() != 64) {
                        logger.warning("Server icon MUST be 64x64 pixels! Please resize the image before using!");
                        bufferedImage = null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.severe("Unable to load configuration file!");
        }
    }
}
