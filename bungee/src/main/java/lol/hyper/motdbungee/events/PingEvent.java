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

package lol.hyper.motdbungee.events;

import lol.hyper.motdbungee.MOTDBungee;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.ThreadLocalRandom;

public class PingEvent implements Listener {

    private final MOTDBungee motd;

    public PingEvent(MOTDBungee motd) {
        this.motd = motd;
    }

    @EventHandler
    public void onPing(ProxyPingEvent e) {
        ServerPing response = e.getResponse();

        if (motd.config.getString("type").equalsIgnoreCase("fixed")) {
            Component formattedMOTD = motd.getMessage("fixed-motd");
            BaseComponent finalMOTD = new TextComponent(BungeeComponentSerializer.get().serialize(formattedMOTD));
            response.setDescriptionComponent(finalMOTD);
        }

        if (motd.config.getString("type").equalsIgnoreCase("random")) {
            int randomNum = ThreadLocalRandom.current()
                    .nextInt(0, motd.config.getStringList("random-motd").size());
            Component randomMOTD = motd.miniMessage.deserialize(motd.config.getStringList("random-motd").get(randomNum));
            BaseComponent finalMOTD = new TextComponent(BungeeComponentSerializer.get().serialize(randomMOTD));
            response.setDescriptionComponent(finalMOTD);
        }

        if (motd.config.getBoolean("use-custom-icon") && motd.bufferedImage != null) {
            response.setFavicon(Favicon.create(motd.bufferedImage));
        }
    }
}
