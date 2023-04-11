/*
 * This file is part of hyperMOTD.
 *
 * hyperMOTD is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * hyperMOTD is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with hyperMOTD.  If not, see <https://www.gnu.org/licenses/>.
 */

package lol.hyper.hypermotd;

import lol.hyper.hypermotd.commands.CommandReload;
import lol.hyper.hypermotd.events.PingEvent;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Logger;

public final class MOTDWaterfall extends Plugin {

    public final File configFile = new File("plugins" + File.separator + "hyperMOTD", "config.yml");
    public Configuration config;
    public BufferedImage bufferedImage;
    public final Logger logger = this.getLogger();
    public PingEvent pingEvent;
    public CommandReload commandReload;
    public final MiniMessage miniMessage = MiniMessage.miniMessage();
    private BungeeAudiences adventure;

    @Override
    public void onEnable() {
        adventure = BungeeAudiences.create(this);
        pingEvent = new PingEvent(this);
        commandReload = new CommandReload("hypermotd", this);
        if (!configFile.exists()) {
            InputStream is = getResourceAsStream("config.yml");
            File serverIcon = new File("server-icon.png");
            try {
                File path = new File("plugins" + File.separator + "hyperMOTD");
                if (path.mkdir()) {
                    Files.copy(is, configFile.toPath());
                    logger.info("Copying default config...");
                    if (serverIcon.exists()) {
                        Files.copy(
                                serverIcon.toPath(),
                                new File("plugins" + File.separator + "hyperMOTD", "server-icon").toPath());
                        logger.info("Moving current server icon...");
                    }
                } else {
                    logger.warning("Unable to create config folder!");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        loadConfig(configFile);
        ProxyServer.getInstance().getPluginManager().registerListener(this, pingEvent);
        getProxy().getPluginManager().registerCommand(this, commandReload);
    }

    public void loadConfig(File file) {
        try {
            config =
                    ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            if (config.getBoolean("use-custom-icon")) {
                String iconName = config.getString("custom-icon-filename");
                if (iconName == null || iconName.isEmpty()) {
                    logger.warning("custom-icon-filename is not set properly!");
                    bufferedImage = null;
                    return;
                }
                File iconFile = new File("plugins" + File.separator + "hyperMOTD", iconName);
                if (!iconFile.exists()) {
                    logger.warning(
                            "Unable to locate custom icon from configuration! Make sure you have the path correct!");
                    logger.warning("The path is current set to: " + iconFile.getAbsolutePath());
                    logger.warning("Make sure this path exists!");
                    bufferedImage = null;
                    return;
                }
                if (!(FilenameUtils.getExtension(iconFile.getName()).equalsIgnoreCase("jpg")
                        || FilenameUtils.getExtension(iconFile.getName()).equalsIgnoreCase("png"))) {
                    logger.warning("Unsupported file extension for server icon! You must use either JPG or PNG only.");
                    bufferedImage = null;
                    return;
                }
                bufferedImage = ImageIO.read(iconFile);
                if ((bufferedImage.getWidth() != 64) && bufferedImage.getHeight() != 64) {
                    logger.warning("Server icon MUST be 64x64 pixels! Please resize the image before using!");
                    bufferedImage = null;
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            logger.severe("Unable to load configuration file!");
            bufferedImage = null;
        }
    }

    public Component getMessage(String path) {
        String message = config.getString(path);
        if (message == null) {
            logger.warning(path + " is not a valid message!");
            return Component.text("Invalid path! " + path).color(NamedTextColor.RED);
        }
        return miniMessage.deserialize(message);
    }

    public BungeeAudiences getAdventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }
}
