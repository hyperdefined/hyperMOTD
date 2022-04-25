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

package lol.hyper.motdvelocity.events;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.server.ServerPing;
import com.velocitypowered.api.util.Favicon;
import lol.hyper.motdvelocity.MOTDVelocity;
import net.kyori.adventure.text.Component;

import java.util.concurrent.ThreadLocalRandom;

public class PingEvent {

    private final MOTDVelocity motd;

    public PingEvent(MOTDVelocity motd) {
        this.motd = motd;
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onPlayerLogin(ProxyPingEvent event) {
        ServerPing.Builder builder = event.getPing().asBuilder();
        // check for existing icon
        Favicon currentIcon = null;
        if (builder.getFavicon().isPresent()) {
            currentIcon = builder.getFavicon().get();
        }
        if (motd.config.getString("type").equalsIgnoreCase("fixed")) {
            Component finalMOTD = motd.getMessage("fixed-motd");
            builder.description(finalMOTD);
        }

        if (motd.config.getString("type").equalsIgnoreCase("random")) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, motd.config.getList("random-motd").size());
            Component finalMOTD = motd.miniMessage.deserialize(String.valueOf(motd.config.getList("random-motd").get(randomNum)));
            builder.description(finalMOTD);
        }

        if (motd.config.getBoolean("use-custom-icon") && motd.bufferedImage != null) {
            builder.favicon(Favicon.create(motd.bufferedImage));
        }
        if (currentIcon != null) {
            builder.favicon(currentIcon);
        }
        event.setPing(builder.build());
    }
}
