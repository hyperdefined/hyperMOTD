package lol.hyper.motd.bukkit;

import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.config.ConfigurationProvider;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class MOTD extends JavaPlugin implements Listener {

    private static MOTD instance;
    public FileConfiguration config;
    public File configFile = new File(getDataFolder(), "config.yml");
    public boolean useCustomIcon = false;
    public BufferedImage bufferedImage;
    public File iconFile;

    public static MOTD getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        if (!configFile.exists()) {
            this.saveResource("config.yml", true);
        }
        loadConfig(configFile);
        this.getCommand("motdreload").setExecutor(new CommandReload());
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onPing (ServerListPingEvent event) throws Exception {
        if (config.getString("type").equalsIgnoreCase("fixed")) {
            event.setMotd(ChatColor.translateAlternateColorCodes('&', config.getString("fixed-motd")));
        }
        if (config.getString("type").equalsIgnoreCase("random")) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, config.getStringList("random-motd").size());
            event.setMotd(ChatColor.translateAlternateColorCodes('&', config.getStringList("random-motd").get(randomNum)));
        }
        if (config.getBoolean("use-custom-icon") && bufferedImage != null) {
            event.setServerIcon(Bukkit.loadServerIcon(bufferedImage));
        }
    }

    public void loadConfig(File file) {
        try {
            config = YamlConfiguration.loadConfiguration(file);
            if (config.getBoolean("use-custom-icon")) {
                iconFile = new File("plugins" + File.separator + "DMC-MOTD", config.getString("custom-icon-filename"));
                if (!iconFile.exists()) {
                    Bukkit.getLogger().warning("[DMC-MOTD] Unable to locate custom icon from configuration! Make sure you have the path correct!");
                    Bukkit.getLogger().warning("[DMC-MOTD] The path is current set to: " + iconFile.getAbsolutePath());
                    Bukkit.getLogger().warning("[DMC-MOTD] Make sure this path exists!");
                    bufferedImage = null;
                } else if (!(FilenameUtils.getExtension(iconFile.getName()).equalsIgnoreCase("jpg") || FilenameUtils.getExtension(iconFile.getName()).equalsIgnoreCase("png"))) {
                    Bukkit.getLogger().warning("[DMC-MOTD] Unsupported file extension for server icon! You must use either JPG or PNG only.");
                    bufferedImage = null;
                } else {
                    useCustomIcon = true;
                    bufferedImage = ImageIO.read(iconFile);
                    if ((bufferedImage.getWidth() != 64) && bufferedImage.getHeight() != 64) {
                        Bukkit.getLogger().warning("[DMC-MOTD] Server icon MUST be 64x64 pixels! Please resize the image before using!");
                        bufferedImage = null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.getLogger().severe("[DMC-MOTD] Unable to load configuration file!");
        }
    }
}
