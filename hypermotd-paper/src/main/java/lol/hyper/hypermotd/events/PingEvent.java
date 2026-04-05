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

package lol.hyper.hypermotd.events;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import lol.hyper.hypermotd.MOTDPaper;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.CachedServerIcon;

import java.util.concurrent.ThreadLocalRandom;

public class PingEvent implements Listener {

    private final MOTDPaper motdPaper;

    public PingEvent(MOTDPaper motdPaper) {
        this.motdPaper = motdPaper;
    }

    @EventHandler
    public void onPing(PaperServerListPingEvent event) {
        if (motdPaper.config.getString("type").equalsIgnoreCase("fixed")) {
            Component formattedMOTD = motdPaper.textUtils.format(motdPaper.config.getString("fixed-motd"));
            event.motd(formattedMOTD);
        }

        if (motdPaper.config.getString("type").equalsIgnoreCase("random")) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, motdPaper.config.getStringList("random-motd").size());
            Component randomMOTD = motdPaper.textUtils.format(motdPaper.config.getStringList("random-motd").get(randomNum));
            event.motd(randomMOTD);
        }

        if (motdPaper.config.getBoolean("use-custom-icon") && motdPaper.bufferedImage != null) {
            CachedServerIcon icon;
            try {
                icon = Bukkit.loadServerIcon(motdPaper.bufferedImage);
            } catch (Exception exception) {
                motdPaper.logger.error("Unable to load server icon!", exception);
                return;
            }
            event.setServerIcon(icon);
        }
    }
}
