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

package lol.hyper.hypermotd.commands;

import lol.hyper.hypermotd.MOTDPaper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandReload implements CommandExecutor {

    private final MOTDPaper motdPaper;

    public CommandReload(MOTDPaper motdPaper) {
        this.motdPaper = motdPaper;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender.hasPermission("hypermotd.reload")) {
            motdPaper.loadConfig(motdPaper.configFile);
            commandSender.sendMessage(Component.text("Config reloaded!").color(NamedTextColor.GREEN));
        } else {
            commandSender.sendMessage(Component.text("You do not have permission for this command.").color(NamedTextColor.RED));
        }
        return true;
    }
}
