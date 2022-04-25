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

package lol.hyper.motdbukkit.commands;

import lol.hyper.motdbukkit.MOTDBukkit;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandReload implements CommandExecutor {

    private final MOTDBukkit motd;

    public CommandReload(MOTDBukkit motd) {
        this.motd = motd;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender.hasPermission("motdbukkit.reload")) {
            motd.loadConfig(motd.configFile);
            motd.getAdventure().sender(commandSender).sendMessage(Component.text("Config reloaded!").color(NamedTextColor.GREEN));
        } else {
            motd.getAdventure().sender(commandSender).sendMessage(Component.text("You do not have permission for this command.").color(NamedTextColor.RED));
        }
        return true;
    }
}
