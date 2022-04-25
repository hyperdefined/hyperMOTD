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

package lol.hyper.motdbungee.commands;

import lol.hyper.motdbungee.MOTDBungee;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CommandReload extends Command {

    private final MOTDBungee motd;

    public CommandReload(String name, MOTDBungee motd) {
        super(name);
        this.motd = motd;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("motdbungee.reload")) {
            motd.loadConfig(motd.configFile);
            motd.getAdventure().sender(sender).sendMessage(Component.text("Config reloaded!").color(NamedTextColor.GREEN));
        } else {
            motd.getAdventure().sender(sender).sendMessage(Component.text("You do not have permission for this command.").color(NamedTextColor.RED));
        }
    }
}
