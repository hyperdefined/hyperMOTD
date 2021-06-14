/*
 * This file is part of DMC-MOTD.
 *
 * DMC-MOTD is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DMC-MOTD is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DMC-MOTD.  If not, see <https://www.gnu.org/licenses/>.
 */

package lol.hyper.motd.bukkit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class CommandReload implements CommandExecutor {

    private final MOTD motd;

    public CommandReload(MOTD motd) {
        this.motd = motd;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.isOp() || commandSender instanceof ConsoleCommandSender) {
            motd.loadConfig(motd.configFile);
            commandSender.sendMessage(ChatColor.GREEN + "Config reloaded!");
        } else {
            commandSender.sendMessage(ChatColor.RED + "No permission.");
        }
        return true;
    }
}
