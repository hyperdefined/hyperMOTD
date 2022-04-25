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

package lol.hyper.motdbukkit.events;

import lol.hyper.motdbukkit.MOTDBukkit;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;

import java.util.concurrent.ThreadLocalRandom;

public class PingEvent implements Listener {

    private final MOTDBukkit motd;

    public PingEvent(MOTDBukkit motd) {
        this.motd = motd;
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        if (motd.config.getString("type").equalsIgnoreCase("fixed")) {
            Component formattedMOTD = motd.getMessage("fixed-motd");
            event.motd(formattedMOTD);
        }

        if (motd.config.getString("type").equalsIgnoreCase("random")) {
            int randomNum = ThreadLocalRandom.current()
                    .nextInt(0, motd.config.getStringList("random-motd").size());
            Component randomMOTD = motd.miniMessage.deserialize(motd.config.getStringList("random-motd").get(randomNum));
            event.motd(randomMOTD);
        }

        if (motd.config.getBoolean("use-custom-icon") && motd.bufferedImage != null) {
            CachedServerIcon icon;
            try {
                icon = Bukkit.loadServerIcon(motd.bufferedImage);
            } catch (Exception exception) {
                motd.logger.severe("Unable to load server icon!");
                exception.printStackTrace();
                return;
            }
            event.setServerIcon(icon);
        }
    }
}
