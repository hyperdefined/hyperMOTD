/*
 * This file is part of DMC-MOTD.
 *
 * DMC-MOTD is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DMC-MOTD is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DMC-MOTD.  If not, see <https://www.gnu.org/licenses/>.
 */

package lol.hyper.motdbukkit;

import lol.hyper.motdbukkit.commands.CommandReload;
import lol.hyper.motdbukkit.events.PingEvent;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

public class MOTDBukkit extends JavaPlugin {

    public final File configFile = new File(getDataFolder(), "config.yml");
    public FileConfiguration config;
    public BufferedImage bufferedImage;
    public File iconFile;
    public final Logger logger = this.getLogger();
    public PingEvent pingEvent;
    public final MiniMessage miniMessage = MiniMessage.miniMessage();
    private BukkitAudiences adventure;

    @Override
    public void onEnable() {
        this.adventure = BukkitAudiences.create(this);
        pingEvent = new PingEvent(this);

        if (!configFile.exists()) {
            this.saveResource("config.yml", true);
            logger.info("Copying default config...");
            File serverIcon = new File("server-icon.png");
            if (serverIcon.exists()) {
                try {
                    Files.copy(
                            serverIcon.toPath(),
                            new File("plugins" + File.separator + "DMC-MOTD", "server-icon").toPath());
                } catch (IOException e) {
                    logger.severe("Unable to move current server icon!");
                    e.printStackTrace();
                }
                logger.info("Moving current server icon...");
            }
        }
        loadConfig(configFile);

        this.getCommand("motdreload").setExecutor(new CommandReload(this));
        Bukkit.getServer().getPluginManager().registerEvents(pingEvent, this);
    }

    public void loadConfig(File file) {
        try {
            config = YamlConfiguration.loadConfiguration(file);
            if (config.getBoolean("use-custom-icon")) {
                String iconName = config.getString("custom-icon-filename");
                if (iconName == null || iconName.isEmpty()) {
                    logger.warning("custom-icon-filename is not set properly!");
                } else {
                    iconFile = new File("plugins" + File.separator + "DMC-MOTD", iconName);
                    if (!iconFile.exists()) {
                        logger.warning(
                                "Unable to locate custom icon from configuration! Make sure you have the path correct!");
                        logger.warning("The path is current set to: " + iconFile.getAbsolutePath());
                        logger.warning("Make sure this path exists!");
                        bufferedImage = null;
                    } else if (!(FilenameUtils.getExtension(iconFile.getName()).equalsIgnoreCase("jpg")
                            || FilenameUtils.getExtension(iconFile.getName()).equalsIgnoreCase("png"))) {
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
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.severe("Unable to load configuration file!");
        }
    }

    public BukkitAudiences getAdventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    public Component getMessage(String path) {
        String message = config.getString(path);
        if (message == null) {
            logger.warning(path + " is not a valid message!");
            return miniMessage.deserialize("<red>Invalid path! " + path + "</red>");
        }
        return miniMessage.deserialize(message);
    }
}
