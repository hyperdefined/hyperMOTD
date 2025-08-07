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

import lol.hyper.hyperlib.HyperLib;
import lol.hyper.hyperlib.bstats.HyperStats;
import lol.hyper.hyperlib.releases.HyperUpdater;
import lol.hyper.hyperlib.utils.TextUtils;
import lol.hyper.hypermotd.commands.CommandReload;
import lol.hyper.hypermotd.events.PingEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MOTDPaper extends JavaPlugin {

    public final File configFile = new File(getDataFolder(), "config.yml");
    public FileConfiguration config;
    public BufferedImage bufferedImage;
    public final ComponentLogger logger = this.getComponentLogger();
    public PingEvent pingEvent;
    public TextUtils textUtils;

    @Override
    public void onEnable() {
        HyperLib hyperLib = new HyperLib(this);
        hyperLib.setup();

        HyperStats stats = new HyperStats(hyperLib, 24158);
        stats.setup();

        textUtils = new TextUtils(hyperLib);

        pingEvent = new PingEvent(this);

        if (!configFile.exists()) {
            this.saveResource("config.yml", true);
            logger.info("Copying default config...");
        }
        loadConfig(configFile);

        this.getCommand("hypermotd").setExecutor(new CommandReload(this));
        Bukkit.getServer().getPluginManager().registerEvents(pingEvent, this);

        HyperUpdater updater = new HyperUpdater(hyperLib);
        updater.setGitHub("hyperdefined", "hyperMOTD");
        updater.setModrinth("DjGkTuWc");
        updater.setHangar("hyperMOTD", "paper");
        updater.check();
    }

    public void loadConfig(File file) {
        try {
            config = YamlConfiguration.loadConfiguration(file);
            if (config.getBoolean("use-custom-icon")) {
                String iconName = config.getString("custom-icon-filename");
                if (iconName == null || iconName.isEmpty()) {
                    logger.warn("custom-icon-filename is not set properly!");
                    bufferedImage = null;
                    return;
                }
                File iconFile = new File("plugins" + File.separator + "hyperMOTD", iconName);
                if (!iconFile.exists()) {
                    logger.warn(
                            "Unable to locate custom icon from configuration! Make sure you have the path correct!");
                    logger.warn("The path is current set to: {}", iconFile.getAbsolutePath());
                    logger.warn("Make sure this path exists!");
                    bufferedImage = null;
                    return;
                }
                if (!checkIcon(iconFile)) {
                    logger.warn("Unsupported file extension for server icon! You must use either JPG or PNG only.");
                    bufferedImage = null;
                    return;
                }
                bufferedImage = ImageIO.read(iconFile);
                if ((bufferedImage.getWidth() != 64) && bufferedImage.getHeight() != 64) {
                    logger.warn("Server icon MUST be 64x64 pixels! Please resize the image before using!");
                    bufferedImage = null;
                }
            }
        } catch (IOException exception) {
            logger.error("Unable to load configuration file!", exception);
        }
    }

    public Component getMessage(String path) {
        String message = config.getString(path);
        if (message == null) {
            logger.warn("{} is not a valid message!", path);
            return Component.text("Invalid path! " + path).color(NamedTextColor.RED);
        }
        return textUtils.format(message);
    }

    private boolean checkIcon(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        String extension = dotIndex > 0 ? fileName.substring(dotIndex + 1) : null;
        if (extension == null) return false;
        return extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg");
    }
}
