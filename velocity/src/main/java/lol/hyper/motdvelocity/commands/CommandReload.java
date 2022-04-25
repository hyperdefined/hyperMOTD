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

package lol.hyper.motdvelocity.commands;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import lol.hyper.motdvelocity.MOTDVelocity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class CommandReload implements SimpleCommand {

    private final MOTDVelocity motd;

    public CommandReload(MOTDVelocity motd) {
        this.motd = motd;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        if (source.hasPermission("motdvelocity.reload")) {
            motd.loadConfig(motd.configFile);
            source.sendMessage(Component.text("Config reloaded!").color(NamedTextColor.GREEN));
        } else {
            source.sendMessage(Component.text("You do not have permission for this command.").color(NamedTextColor.RED));
        }
    }
}
