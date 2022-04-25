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

package lol.hyper.motdvelocity;

import com.google.inject.Inject;
import com.moandjiezana.toml.Toml;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lol.hyper.motdvelocity.commands.CommandReload;
import lol.hyper.motdvelocity.events.PingEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;

@Plugin(
        id = "hypermotd",
        name = "hyperMOTD",
        version = "1.0"
)
public class MOTDVelocity {

    public final Logger logger;
    private final ProxyServer server;
    private final CommandManager commandManager;
    public MiniMessage miniMessage = MiniMessage.miniMessage();
    public Toml config;
    public BufferedImage bufferedImage;
    public PingEvent pingEvent;
    public CommandReload commandReload;
    public final File configFile = new File("plugins" + File.separator + "hyperMOTD", "config.toml");

    @Inject
    public MOTDVelocity(ProxyServer server, Logger logger, CommandManager commandManager) {
        this.server = server;
        this.logger = logger;
        this.commandManager = commandManager;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        commandReload = new CommandReload(this);
        pingEvent = new PingEvent(this);
        loadConfig(configFile);
        server.getEventManager().register(this, pingEvent);

        CommandMeta meta = commandManager.metaBuilder("motdreload").build();
        commandManager.register(meta, commandReload);
    }

    public void loadConfig(File file) {
        if (!file.exists()) {
            InputStream is = this.getClass().getResourceAsStream( "/config.toml");
            File path = new File("plugins" + File.separator + "hyperMOTD");
            if (is != null) {
                try {
                    if (path.mkdir()) {
                        Files.copy(is, file.toPath());
                        this.logger.info("Copying default config...");
                    } else {
                        this.logger.error("Unable to create config folder!");
                    }
                } catch (IOException e) {
                    this.logger.error("Unable to copy default config!", e);
                }
            }
        }
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            this.logger.error("Unable to find config!", e);
            return;
        }
        config = new Toml().read(inputStream);

        if (config.getBoolean("use-custom-icon")) {
            File iconFile = new File("plugins" + File.separator + "hyperMOTD", config.getString("custom-icon-filename"));
            if (!iconFile.exists()) {
                logger.warn(
                        "Unable to locate custom icon from configuration! Make sure you have the path correct!");
                logger.warn("The path is current set to: " + iconFile.getAbsolutePath());
                logger.warn("Make sure this path exists!");
                bufferedImage = null;
            } else if (!(FilenameUtils.getExtension(iconFile.getName()).equalsIgnoreCase("jpg")
                    || FilenameUtils.getExtension(iconFile.getName()).equalsIgnoreCase("png"))) {
                logger.warn("Unsupported file extension for server icon! You must use either JPG or PNG only.");
                bufferedImage = null;
            } else {
                try {
                    bufferedImage = ImageIO.read(iconFile);
                } catch (IOException exception) {
                    logger.error("Unable to load icon file!", exception);
                    bufferedImage = null;
                    return;
                }
                if ((bufferedImage.getWidth() != 64) && bufferedImage.getHeight() != 64) {
                    logger.warn("Server icon MUST be 64x64 pixels! Please resize the image before using!");
                    bufferedImage = null;
                }
            }
        }
    }

    public Component getMessage(String path) {
        String message = config.getString(path);
        if (message == null) {
            logger.warn(path + " is not a valid message!");
            return miniMessage.deserialize("<red>Invalid path! " + path + "</red>");
        }
        return miniMessage.deserialize(message);
    }
}
